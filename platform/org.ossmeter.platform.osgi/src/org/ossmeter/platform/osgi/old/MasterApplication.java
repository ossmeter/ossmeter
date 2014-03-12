package org.ossmeter.platform.osgi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.ossmeter.platform.osgi.Activator;

public class MasterApplication implements IApplication, ServiceTrackerCustomizer {
	
	protected boolean done = false;
	protected Object appLock = new Object();
	protected ServiceTracker workerServiceTracker;
	
	protected IMaster master;
	protected List<IWorker> workers;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		master = new IMaster() {
			@Override
			public void receiveReport(IWorker worker, Report report) {
				System.err.println("");
			}
		};
		workers = new ArrayList<IWorker>();
		
		workerServiceTracker = new ServiceTracker(Activator.getContext(), IWorker.class, this);	
		workerServiceTracker.open();
		
		
		waitForDone();
		return IApplication.EXIT_OK;	
	}

	@Override
	public Object addingService(ServiceReference reference) {
		IWorker worker = (IWorker)Activator.getContext().getService(reference);
		workers.add(worker);
		
		worker.setMaster(master);
		worker.queueProjects(Arrays.asList("Project1","Project2","Project3"));
		
		return worker;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub
		
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

}
