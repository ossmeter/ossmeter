package org.ossmeter.platform.osgi;

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
import org.ossmeter.platform.osgi.services.IWorkerService;
import org.ossmeter.platform.osgi.services.WorkerService;

public class WorkerServiceApplication implements IApplication, ServiceTrackerCustomizer<IWorkerService, IWorkerService> {

	protected boolean done = false;
	protected Object appLock = new Object();
	
	protected ServiceTracker<IWorkerService, IWorkerService> workerServiceTracker;
	protected ServiceRegistration<IWorkerService> workerRegistration;
	
	@SuppressWarnings({ "unchecked", "rawtypes", "restriction" })// FIXME !!! (I just hate yellow squiggles...)
	@Override
	public Object start(IApplicationContext context) throws Exception {
		// Advertise as being a worker
		Dictionary props = new Properties();
		props.put(IDistributionConstants.SERVICE_EXPORTED_INTERFACES, IDistributionConstants.SERVICE_EXPORTED_INTERFACES_WILDCARD);
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONFIGS, "ecf.generic.server");
		props.put(IDistributionConstants.SERVICE_EXPORTED_CONTAINER_FACTORY_ARGUMENTS, "ecftcp://localhost:3788/worker");
		// FIXME: Understand the above: commenting out the props has no effect (at least locally - maybe a clue to network issue).
		
		workerRegistration = Activator.getContext().registerService(IWorkerService.class, new WorkerService(), props);		
		
		// Detect other workers
		workerServiceTracker = new ServiceTracker<IWorkerService, IWorkerService>(Activator.getContext(), IWorkerService.class, this);	
		workerServiceTracker.open();

		// Now, rest.
		waitForDone();
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		synchronized (appLock) {
			done = true;
			appLock.notifyAll();
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
		
		List<Object> projects = new ArrayList<Object>(); 
		projects.add("Project1");
		projects.add("Project2");
		projects.add("Project3");
		projects.add("Project4");
		projects.add("Project5");
		
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
