package org.ossmeter.platform.osgi.services;

import java.util.List;

import org.ossmeter.platform.osgi.executors.SchedulerStatus;
import org.ossmeter.platform.osgi.executors.SlaveScheduler;

public class WorkerService implements IWorkerService {

	protected SlaveScheduler scheduler;
	
	public WorkerService() {
		scheduler = new SlaveScheduler();
	}
	
	/* (non-Javadoc)
	 * @see org.ossmeter.platform.osgi.services.IWorkerService#queueProjects(java.util.List)
	 */
	@Override
	public boolean queueProjects(List<Object> projects) {
		if (getStatus().equals(SchedulerStatus.AVAILABLE)) {
			scheduler.queueProjects(projects);
			scheduler.run();
			return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.ossmeter.platform.osgi.services.IWorkerService#getStatus()
	 */
	@Override
	public SchedulerStatus getStatus() {
		return scheduler.getStatus();
	}
	
}
