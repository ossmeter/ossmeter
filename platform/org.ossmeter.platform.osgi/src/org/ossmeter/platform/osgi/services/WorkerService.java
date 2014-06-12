package org.ossmeter.platform.osgi.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

	@Override
	public void pause() {
		scheduler.pause();
	}

	@Override
	public void resume() {
		scheduler.resume();
	}

	@Override
	public void shutdown() {
		boolean clean = scheduler.finish(); // Blocking. Waits for worker to complete.
		if (!clean) {
			throw new RuntimeException("Slave scheduler did not shutdown cleanly.");
		}
	}

	@Override
	public String getIdentifier() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "Unknown IP"; //FIXME
	}
}
