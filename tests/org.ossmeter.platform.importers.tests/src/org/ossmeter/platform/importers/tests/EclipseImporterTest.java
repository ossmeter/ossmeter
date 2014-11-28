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

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.Matcher;
import org.hamcrest.core.AnyOf;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.ossmeter.metricprovider.trans.importer.eclipse.EclipseImporterProvider;
import org.ossmeter.metricprovider.trans.importer.github.GitHubImporterProvider;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.repository.model.eclipse.importer.EclipseProjectImporter;
import org.ossmeter.repository.model.github.importer.GitHubImporter;
import org.ossmeter.repository.model.importer.exception.ProjectUnknownException;
import org.ossmeter.repository.model.importer.exception.WrongUrlException;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.Mongo;

public class EclipseImporterTest {
	
	static Mongo mongo;
	static Platform platform;
	static EclipseProjectImporter im;
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
		im = new EclipseProjectImporter();
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	@Rule public ExpectedException expected = ExpectedException.none();
	@Test
	public void eclipseInvalidInput() throws WrongUrlException, ProjectUnknownException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		expected.expect(WrongUrlException.class);
			assertNull( im.importProjectByUrl("", platform));
			assertNull( im.importProjectByUrl(null, platform)); // This will fail
			//assertNotNull( im.importProject("birt", platform));
			//assertNull( im.importRepository(null, platform)); // This will fail
		
	}
	@Test
	public void eclipseValidInput() throws WrongUrlException, ProjectUnknownException {
		// Prints " API rate limit exceeded." message.
		// TODO: should we throw a InvalidUrlException instead of returning null? 
		//assertNull( im.importProjectByUrl(null, platform)); // This will fail
		assertNotNull( im.importProject("birt", platform));
		// Now update
		Project project = im.importProjectByUrl("http://projects.eclipse.org/projects/birt", platform);
		EclipseImporterProvider mp = new EclipseImporterProvider();
		PongoDB db = mp.adapt(platform.getProjectRepositoryManager().getDb());
		mp.measure(project, null, db);
		Project p = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(project.getName());
		assertNotEquals(p.getDbObject().toString(), project.getDbObject().toString());			
		
		
		//assertNull( im.importRepository(null, platform)); // This will fail
		
	}
	@Test
	public void eclipseEmptyNNTPCommunicationChannel()
	{
		
		Project p = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName("birt");
		boolean b = false;
		for (CommunicationChannel i : p.getCommunicationChannels()) {
			if (i instanceof NntpNewsGroup)
				b = true;
		}
		assertTrue(b);
	}
	

}
