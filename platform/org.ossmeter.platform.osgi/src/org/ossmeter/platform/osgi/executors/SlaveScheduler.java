/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.osgi.executors;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.osgi.old.IScheduler;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.SchedulingInformation;

import com.mongodb.Mongo;

public class SlaveScheduler implements IScheduler {

	protected boolean alive = true;
	protected Object master;
	
	protected SchedulerStatus status;
	protected List<String> queue;
	
	protected Thread worker;
	protected Thread heartbeat;
	final protected Mongo mongo;
	protected Platform platform;
	
	private volatile boolean running = true;
	
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
		synchronized (queue) {
			queue.addAll(projects);
			queue.notify();
		}
		return true;
	}

	@Override
	public void run() {
		
		String id = UUID.randomUUID().toString();
		try {
			id = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		final String identifier = id;
		
		heartbeat = new Thread() {
			@Override
			public void run() {
				while (alive) {
					SchedulingInformation job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier(identifier);
					if (job == null) {
						job = new SchedulingInformation();
						job.setWorkerIdentifier(identifier);
						platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().add(job);
					}
					// TODO: One issue with this is that the machines clock's may differ..
					job.setHeartbeat(System.currentTimeMillis());
					platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
					
					try {
						Thread.sleep(60000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		heartbeat.start();
		
		worker = new Thread() {
			@Override
			public void run() {
				
				while (alive) {
					SchedulingInformation job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier(identifier);
					
					if(job != null) {
						List<String> projects = job.getCurrentLoad();
						
						System.out.println("Slave '" + identifier + "' executing " + projects.size() + " projects.");
						
						for (String projectName : projects) {
							Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(projectName); //FIXME This should be just NAME
							
							if (project == null) {
								System.err.println("DB lookup for project named '" + projectName + "' failed. Skipping.");
								continue;
							}
							
							ProjectExecutor exe = new ProjectExecutor(platform, project);
							exe.run();
						}
						
						job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier(identifier);
						job.getCurrentLoad().clear();
						platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
					}
					try {
						// Give the master time to schedule some new projects
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		worker.start();
	}
	
	public void pause() {
		try {
			worker.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void resume() {
		worker.notify();
	}
	
	@Override
	public boolean finish() {
		alive = false;
		try {
			worker.join();
			heartbeat.join();
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
