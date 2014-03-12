package org.ossmeter.platform.osgi.executors;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.osgi.old.IScheduler;
import org.ossmeter.repository.model.Project;

import com.mongodb.Mongo;

public class SlaveScheduler implements IScheduler {

	protected boolean alive = true;
	protected Object master;
	
	protected SchedulerStatus status;
	protected List<String> queue;
	
	protected Thread worker;
	final protected Mongo mongo;
	protected Platform platform;
	
	
	public SlaveScheduler(Mongo mongo) {
		status = SchedulerStatus.AVAILABLE;
		queue = new ArrayList<String>();
		this.mongo = mongo;
		
		// FIXME: This should be passed configuration information
		// specifying local storage location etc.
		this.platform = new Platform(mongo);
	}
	
	public boolean queueProjects(List<String> projects) {
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
					for (String projectName : queue) {
						Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(projectName);
						
						if (project == null) {
							System.err.println("DB lookup for project named '" + projectName + "' failed. Skipping.");
							continue;
						}
						
						ProjectExecutor exe = new ProjectExecutor(platform, project);
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
