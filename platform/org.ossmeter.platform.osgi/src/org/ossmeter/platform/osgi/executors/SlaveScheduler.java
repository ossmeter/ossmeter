package org.ossmeter.platform.osgi.executors;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.osgi.old.IScheduler;

public class SlaveScheduler implements IScheduler {

	protected boolean alive = true;
	protected Object master;
	
	protected SchedulerStatus status;
	protected List<Object> queue;
	
	protected Thread worker;
	
	public SlaveScheduler() {
		status = SchedulerStatus.AVAILABLE;
		queue = new ArrayList<Object>();
	}
	
	public boolean queueProjects(List<Object> projects) {
		if (status.equals(SchedulerStatus.BUSY)) {
			return false;
		}
		queue.addAll(projects);
		return true;
	}

	@Override
	public void run() {
		if (status.equals(SchedulerStatus.AVAILABLE) && queue.size() > 0) {
			status = SchedulerStatus.BUSY;
			
			worker = new Thread() {
				@Override
				public void run() {
					// Focus on a single project at a time. Sequential.
					for (Object project : queue) {
						ProjectExecutor exe = new ProjectExecutor();
						exe.setProject(project);
						exe.run();
					}

					// Make it known that you're free
					status = SchedulerStatus.AVAILABLE;
				}
			};
			worker.start();
		} else {
			System.err.println("Tried to start slave when slave already started.");
		}
	}
	
	@Override
	public boolean finish() {
		alive = false;
		try {
			worker.join();
			return true;
		} catch (InterruptedException e) {
			return false;
		}
	}

	@Override 
	public SchedulerStatus getStatus() {
		return status;
	}
}
