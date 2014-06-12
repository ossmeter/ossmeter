package org.ossmeter.platform.osgi.services;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.platform.logging.OssmeterLoggerFactory;
import org.ossmeter.platform.osgi.executors.SchedulerStatus;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.SchedulingInformation;
import org.ossmeter.repository.model.WorkerNode;

import com.mongodb.Mongo;


public class MasterService implements IMasterService {

	protected List<IWorkerService> workers;
	protected MasterRunner runner;
	protected Mongo mongo;
	protected Platform platform; 
	
	protected Thread master;
	protected OssmeterLogger logger;
	
	public MasterService(List<IWorkerService> workers) {
		this.workers = workers;
		this.logger = (OssmeterLogger) OssmeterLogger.getLogger("Master");
		this.logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
	}
	
	@Override
	public void start() throws Exception {
		logger.info("Master service started.");
		
		mongo = new Mongo(); //FIXME: should use replica set / conf
		platform = new Platform(mongo);
	
		SchedulingInformation schedulingInformation = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().first();
		if (schedulingInformation == null) {
			schedulingInformation = new SchedulingInformation();
			platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().add(schedulingInformation);
		}
		schedulingInformation.setMasterNodeIdentifier(InetAddress.getLocalHost().getHostAddress());
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// Now start scheduling
		master = new Thread() {
			@Override
			public void run() {
				// FIXME: Oh God, so many while loops.
				while (true) { // TODO: while alive
					Iterator<Project> it = platform.getProjectRepositoryManager().getProjectRepository().getProjects().iterator();
					
					while (it.hasNext()) {
						List<String> projects = new ArrayList<String>();
						
						while (it.hasNext()) {
							projects.add(it.next().getShortName());
							if (projects.size() >= 3) break;
						}
						
						IWorkerService worker = null;
						while (worker == null) {
							worker = nextFreeWorker();
							if (worker == null) {
								try {
									logger.info("No workers available. Sleeping.");
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							} else {
								//worker.queueProjects(projects);
								logger.info("Queuing " + projects.size() + " on worker ");
								
								worker.queueProjects(projects);
								
								// Update DB with load
								WorkerNode wn = null;
								for (WorkerNode n : platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().first().getNodes()) {
									if (n.getIdentifier().equals(worker.getIdentifier())) { 
										wn = n;
										break;
									}
								}
								if (wn == null) {
									wn = new WorkerNode();
									wn.setIdentifier(worker.getIdentifier());
									 platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().first().getNodes().add(wn);
								}
								wn.getCurrentLoad().clear();
								wn.getCurrentLoad().addAll(projects);
								platform.getProjectRepositoryManager().getProjectRepository().sync();
							}
						}
					}
					logger.info("All projects scheduled. Repeating.");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		master.start();
	}

	protected IWorkerService nextFreeWorker() {
		for (IWorkerService worker : workers) {
			if (worker.getStatus().equals(SchedulerStatus.AVAILABLE)){
				return worker;
			}
		}
		return null;
	}
	
	@Override
	public void pause() {
//		master.wait(); // TODO
		
		for (IWorkerService worker : workers) {
			worker.pause();
		}
	}
	
	@Override
	public void resume() {
//		master.notify(); // TODO
		
		for (IWorkerService worker : workers) {
			worker.resume();
		}
	}

	@Override
	public void shutdown() {

		for (IWorkerService worker : workers) {
			worker.shutdown();
		}
		
		mongo.close();
		
	}

	class MasterRunner implements Runnable {
		public void run() {
			while (true) {
				
			}
		}
	}
}
