package org.ossmeter.platform.osgi.services;

import java.util.List;

import org.ossmeter.platform.osgi.executors.SchedulerStatus;
import org.ossmeter.platform.osgi.executors.SlaveScheduler;

import com.mongodb.Mongo;

public class WorkerService implements IWorkerService {

	protected SlaveScheduler scheduler;
	
	public WorkerService(Mongo mongo) {
		scheduler = new SlaveScheduler(mongo);
	}
	
	/* (non-Javadoc)
	 * @see org.ossmeter.platform.osgi.services.IWorkerService#queueProjects(java.util.List)
	 */
	@Override
	public boolean queueProjects(List<String> projects) {
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
