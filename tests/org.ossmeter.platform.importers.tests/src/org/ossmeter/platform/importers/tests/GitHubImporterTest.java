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
package org.ossmeter.platform.importers.tests;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ossmeter.metricprovider.trans.importer.github.GitHubImporterProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.github.GitHubRepository;
import org.ossmeter.repository.model.github.importer.GitHubImporter;
import org.ossmeter.repository.model.importer.exception.WrongUrlException;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.Mongo;

public class GitHubImporterTest {
	
	static Mongo mongo;
	static Platform platform;
	static GitHubImporter im;
	
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
		im = new GitHubImporter();
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	
	@Test
	public void testValidInput() throws WrongUrlException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		assertNotNull( im.importProjectByUrl("https://github.com/facebook/react", platform));
//		assertNull( im.importProjectByUrl(null, platform)); // This will fail
		assertNotNull( im.importRepository("facebook/react", platform));
//		assertNull( im.importRepository(null, platform)); // This will fail
	}

	@Test	
	public void testImportByUrlAndUpdate() throws WrongUrlException {
		GitHubRepository project = im.importProjectByUrl("https://github.com/facebook/react", platform);
		
		String pr = project.getDbObject().toString();
		
		// Currently not working
//		assertEquals(1, project.getVcsRepositories().size());
		
		// Now update
		GitHubImporterProvider mp = new GitHubImporterProvider();
		PongoDB db = mp.adapt(platform.getMetricsRepository(project).getDb());
		mp.measure(project, null, db);
		
		Project p = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(project.getName());
		
		// Check the update had no effect
		assertEquals(pr, p.getDbObject().toString());
		
		// Clean up
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().remove(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
	}
	
	@Test(expected = WrongUrlException.class)
	public void testInvalidInput() throws WrongUrlException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		
			assertNull( im.importProjectByUrl("", platform));
			//assertNull( im.importProjectByUrl(null, platform)); // This will fail
			assertNull( im.importRepository("", platform));
			//assertNull( im.importRepository(null, platform)); // This will fail
		
	}
}
