package org.ossmeter.platform.osgi;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Properties;

import org.eclipse.ecf.osgi.services.distribution.IDistributionConstants;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.platform.osgi.services.IWorkerService;
import org.ossmeter.platform.osgi.services.WorkerService;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class WorkerServiceApplication implements IApplication, ServiceTrackerCustomizer<IWorkerService, IWorkerService> {
	
	protected OssmeterLogger logger;
	protected boolean done = false;
	protected Object appLock = new Object();
	protected Properties configuration;
	protected List<ServerAddress> mongoHostAddresses;
	
	protected Mongo mongo;
	
	protected ServiceTracker<IWorkerService, IWorkerService> workerServiceTracker;
	protected ServiceRegistration<IWorkerService> workerRegistration;
	
	@SuppressWarnings({ "unchecked", "rawtypes", "restriction" })// FIXME !!! (I just hate yellow squiggles...)
	@Override
	public Object start(IApplicationContext context) throws Exception {
		// Setup platform
		processArguments(context);
		loadConfiguration();

		logger = (OssmeterLogger)OssmeterLogger.getLogger("WorkerServiceApplication");
		logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
		logger.info("Application initialising.");
		
		// Connect to Mongo - single instance per node
		mongo = new Mongo(mongoHostAddresses);
		
		// Ensure OSGi contributors are active
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		// Advertise as being a worker
		Dictionary props = new Properties();
		props.put(IDistributionConstants.SERVICE_EXPORTED_INTERFACES, IDistributionConstants.SERVICE_EXPORTED_INTERFACES_WILDCARD);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONFIGS, "ecf.generic.server");
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONTAINER_FACTORY_ARGUMENTS, "ecftcp://localhost:3788/worker");
		// FIXME: Understand the above: commenting out the props has no effect (at least locally - maybe a clue to network issue).
		
		// TODO: Pass the service any configuration details it needs
		WorkerService worker = new WorkerService(mongo);
		workerRegistration = Activator.getContext().registerService(IWorkerService.class, worker, props);		
		
		// Detect other workers
		workerServiceTracker = new ServiceTracker<IWorkerService, IWorkerService>(Activator.getContext(), IWorkerService.class, this);	
		workerServiceTracker.open();

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
				i++;
			}
		}
	}

	protected void loadConfiguration() throws Exception {
		if (configuration == null) {
			// TODO Create default configuration
			configuration = new Properties();
		}

		// Mongo
		String[] hosts = configuration.getProperty("mongohosts", "localhost:27017").split(",");
		mongoHostAddresses = new ArrayList<>();
		for (String host : hosts) {
			mongoHostAddresses.add(new ServerAddress(host));
		}
		
		// Storage
		// TODO: Perhaps pass a configuration file as the constructor to the Platform?
		
	}

	@Override
	public void stop() {
		synchronized (appLock) {
			done = true;
			appLock.notifyAll();
			
			// Clean up
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
		// TODO: This essentially acts as the master at the moment
		/* Something like:
		if (iAmMaster) {
			worker.setMaster(me);
			addToWOrkers(worker);
		} else {
			keepTrack(worker);
		}
		Need a voting thing too
		*/
		
		System.err.println("Worker registered!");
		IWorkerService worker = Activator.getContext().getService(reference);
		
		List<String> projects = new ArrayList<String>(); 

		// MODELS:
//		projects.add("modeling.mmt.atl");  // jimbook
//		projects.add("modeling.epsilon"); // jimbook
//		projects.add("modeling.gmp.gmf-runtime"); // esgroup
//		projects.add("modeling.tmf.xtext"); // esgroup
//		projects.add("modeling.viatra2"); // jimbook
//		projects.add("modeling.gmt.amw"); // paige
//		projects.add("modeling.mdt.papyrus"); // esgroup
//		projects.add("modeling.mdt.modisco"); // paige
//		projects.add("modeling.gmp.graphiti"); // paige

		// Deliverable:
//		projects.add("modeling.mmt.atl");  // jimbook
//		projects.add("modeling.viatra2"); // jimbook
//		projects.add("modeling.epsilon"); // esgroup
		projects.add("modeling.mdt.papyrus"); // esgroup
//		projects.add("modeling.gmp.graphiti"); // paige
		
		worker.queueProjects(projects);
		
		return worker;
	}


	@Override
	public void modifiedService(ServiceReference<IWorkerService> reference, IWorkerService service) {
		
		
	}


	@Override
	public void removedService(ServiceReference<IWorkerService> reference, IWorkerService service) {
		
	}

}
