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

import org.ossmeter.platform.osgi.executors.MasterScheduler;
import org.ossmeter.platform.osgi.executors.SlaveScheduler;

public class NodeRunner {
	
	protected List<Object> workers;
	
	protected IScheduler scheduler;
	
	public void run() {
		if (isMaster()) {
			scheduler = new MasterScheduler();
		} else {
//			scheduler = new SlaveScheduler();
		}
		
		// FIXME: This above is probably not the way to go. This class needs
		// to be the service class essentially. All nodes advertise their Slave abilities. Voting
		// occurs. Master starts the distribution of tasks. Master can also be slave (but with a reduced
		// load/threadpool size.
	}
	
	public boolean isMaster() {
		return false;
	}
}
