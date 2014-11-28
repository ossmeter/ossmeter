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
package org.ossmeter.platform.osgi;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.ossmeter.platform.osgi.executors.SlaveScheduler;

import com.mongodb.Mongo;

public class SlaveSchedulerTest {

	@Test
	public void test() throws Exception {
		
		Mongo mongo = new Mongo();
		
		SlaveScheduler slave = new SlaveScheduler(mongo);
		
		List<String> projects = new ArrayList<String>(); 
		
		projects.add("Project1");
		projects.add("Project2");
		projects.add("Project3");
		projects.add("Project4");
		projects.add("Project5");
		
		slave.queueProjects(projects);

		slave.run();
		slave.run(); // Should print error
		slave.finish();
	}
}
