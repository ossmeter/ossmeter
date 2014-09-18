package org.ossmeter.platform.osgi;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ossmeter.platform.admin.AdminApplication;
import org.ossmeter.platform.admin.ProjectListAnalysis;
import org.ossmeter.platform.client.api.ProjectResource;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.platform.osgi.services.IWorkerService;
import org.ossmeter.platform.osgi.services.MasterService;
import org.ossmeter.platform.osgi.services.WorkerService;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class OssmeterApplication implements IApplication, ServiceTrackerCustomizer<IWorkerService, IWorkerService> {
	
	protected boolean master = true;
	protected OssmeterLogger logger;
	protected boolean done = false;
	protected Object appLock = new Object();
	protected Properties configuration;
	protected List<ServerAddress> mongoHostAddresses;
	
	protected Mongo mongo;
	
	protected ServiceTracker<IWorkerService, IWorkerService> workerServiceTracker;
	protected ServiceRegistration<IWorkerService> workerRegistration;
	
	protected List<IWorkerService> workers;
	private MasterService masterService;
	
	public OssmeterApplication() {
		workers = new ArrayList<IWorkerService>();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "restriction" })// FIXME !!! (I just hate yellow squiggles...)
	@Override
	public Object start(IApplicationContext context) throws Exception {
		// Setup platform
		processArguments(context);
		loadConfiguration();

		logger = (OssmeterLogger)OssmeterLogger.getLogger("OssmeterApplication");
		logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
		logger.info("Application initialising.");
		
		// Connect to Mongo - single instance per node
		mongo = new Mongo(mongoHostAddresses);
		
		// Ensure OSGi contributors are active
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		// Advertise as being a worker
		Dictionary props = new Properties();
//		props.put(IDistributionConstants.SERVICE_EXPORTED_INTERFACES, IDistributionConstants.SERVICE_EXPORTED_INTERFACES_WILDCARD);
//		props.put(IDistributionConstants.SERVICE_EXPORTED_CONFIGS, "ecf.generic.server");
//		props.put(IDistributionConstants.SERVICE_EXPORTED_CONTAINER_FACTORY_ARGUMENTS, "ecftcp://localhost:3788/worker");
		// FIXME: Understand the above: commenting out the props has no effect (at least locally - maybe a clue to network issue).
		
		// TODO: Pass the service any configuration details it needs
		WorkerService worker = new WorkerService(mongo);
		workerRegistration = Activator.getContext().registerService(IWorkerService.class, worker, props);		
		
		// Detect other workers
		workerServiceTracker = new ServiceTracker<IWorkerService, IWorkerService>(Activator.getContext(), IWorkerService.class, this);	
		workerServiceTracker.open();
		
		
		System.setProperty("MAVEN_EXECUTABLE", "/Applications/apache-maven-3.2.3/bin/mvn");
		
		// If master, start
		if (master) {
			masterService = new MasterService(workers);
			masterService.start();
		}

		// Start web server
		new ProjectResource();
		new ProjectListAnalysis();
		
		// Now, rest.
  		waitForDone();
		return IApplication.EXIT_OK;
	}

	protected void processArguments(IApplicationContext context) throws Exception {
		String[] args = (String[])context.getArguments().get("application.args");
		if (args == null) return;
		
		for (int i = 0; i < args.length; i++) {
			if ("-ossmeterConfig".equals(args[i])) {
				configuration = new Properties();
				configuration.load(new FileReader(args[i+1]));
				
				// Maven
				String maven = configuration.getProperty("maven_executable", "");
				if (!maven.equals("")) {
					System.setProperty("MAVEN_EXECUTABLE", maven);
				}
				
				i++;
			} else if ("-master".equals(args[i])) { 
				master = true;
			}
		}
	}

	protected void loadConfiguration() throws Exception {
		if (configuration == null) {
			// TODO Create default configuration. Maybe a config class?
			configuration = new Properties();
		}

		// Mongo
		String[] hosts = configuration.getProperty("mongohosts", "localhost:27017").split(",");
		mongoHostAddresses = new ArrayList<>();
		for (String host : hosts) {
			mongoHostAddresses.add(new ServerAddress(host));
		}
		
		// Storage
		// TODO
	}

	@Override
	public void stop() {
		synchronized (appLock) {
			done = true;
			appLock.notifyAll();
			
			// Clean up
			if (master && masterService != null) masterService.shutdown();
			mongo.close();
			workerRegistration.unregister();
			workerServiceTracker.close();
		}	
	}
	
	private void waitForDone() {
		// then just wait here
		synchronized (appLock) {
			while (!done) {
				try {
					appLock.wait();
				} catch (InterruptedException e) {
					// do nothing
				}
			}
		}
	}

	@Override
	public IWorkerService addingService(ServiceReference<IWorkerService> reference) {
		IWorkerService worker = Activator.getContext().getService(reference);
		workers.add(worker);
		return worker;
	}

	@Override
	public void modifiedService(ServiceReference<IWorkerService> reference, IWorkerService service) {
		
		
	}

	@Override
	public void removedService(ServiceReference<IWorkerService> reference, IWorkerService service) {
		workers.remove(service);
	}
}
