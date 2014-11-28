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
package org.ossmeter.platform.osgi.old;

import java.util.List;

public class RemoteWorker implements IWorker {

	protected IMaster master;
	protected List<String> projectQueue;
	protected List<String> runQueue;
	protected boolean isBusy;
	
	protected void executeRunQueue() {
		Thread runner = new Thread() {
			@Override
			public void run() {
				isBusy = true;
				runQueue.addAll(projectQueue);
				projectQueue.clear();
				
				for (String project : runQueue) {
					System.out.println("Executing project " + project);
					try {
						sleep(2000);
						report(project, new Report(project, ReportKind.ERROR, "Project analysis crashed."));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					report(project, new Report(project, ReportKind.COMPLETE, "Project analysis complete."));
				}
				isBusy = false;
			}
		};
		runner.start();
	}
	
	@Override
	public void setMaster(IMaster master) {
		this.master = master;
	}

	@Override
	public void queueProjects(List<String> projectList) {
		projectQueue.addAll(projectList);
		
		if (!isBusy) {
			executeRunQueue();
		}
	}
	
	public void report(String project, Report report) {
		master.receiveReport(this, report);
	}

}
