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
package org.ossmeter.platform.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.SchedulingInformation;

import com.mongodb.Mongo;

public class TestSchedulingInfo {

	@Test
	public void test() throws Exception {
		
		Mongo mongo = new Mongo();
		
		Platform platform = new Platform(mongo);
		
		SchedulingInformation job = new SchedulingInformation();
		job.setWorkerIdentifier("Test");
		job.setHeartbeat(System.currentTimeMillis());
		job.getCurrentLoad().add("hi");
		
		platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().add(job);
		platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
		
		job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier("Test");
		assertNotNull(job);
		assertEquals(1, job.getCurrentLoad().size());

		job.getCurrentLoad().add("Test2");
		platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
		
		job = platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().findOneByWorkerIdentifier("Test");
		assertNotNull(job);
		assertEquals(2, job.getCurrentLoad().size());
		
		platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().remove(job);
		platform.getProjectRepositoryManager().getProjectRepository().getSchedulingInformation().sync();
		
		mongo.close();
	}
}
