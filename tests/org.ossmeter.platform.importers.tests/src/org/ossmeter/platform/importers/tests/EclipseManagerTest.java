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
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.*;
import org.ossmeter.platform.delta.vcs.AbstractVcsManager;
import org.ossmeter.platform.vcs.git.GitManager;
import org.ossmeter.platform.vcs.svn.SvnManager;
import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.importer.EclipseProjectImporter;
import org.ossmeter.repository.model.vcs.git.GitRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;

import com.mongodb.Mongo;

public class EclipseManagerTest {
	static Mongo mongo;
	static Platform platform;
	static EclipseProjectImporter im;
	AbstractVcsManager manager;
	
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
		im = new EclipseProjectImporter();
		//repository.setUrl("https://code.google.com/p/super-awesome-fighter");
	}
	
	@AfterClass
	public static void shutdown() throws Exception {
		mongo.close();
	}
	
	@Test
	public void testEclipseBTS()
	{
		fail("Not yet implement");
	}
	
	
	@Test
	public void testEclipseVCSRepository() throws Exception {
		ProjectCollection pc = platform.getProjectRepositoryManager().getProjectRepository().getProjects();
		boolean test = true;
		int countSVN = 0;
		int countGit = 0;
		for (Project project : pc) 
		{
			
			if (project instanceof EclipseProject)
			{
				for (VcsRepository vcs : project.getVcsRepositories()) {
					
					if (vcs instanceof GitRepository)
					{
						try
						{
							countGit ++;
							manager = new GitManager();
							GitRepository bb = (GitRepository)vcs;
							manager.getFirstRevision(bb);
						} catch (Exception e) {
							System.out.println("Manager exception when call GIT getFirstRevision for Project: " + project.getShortName());
							test = false;
						}
					}
					if (vcs instanceof SvnRepository)
					{
						try
						{
							countSVN ++;
							manager = new SvnManager();
							SvnRepository bb = (SvnRepository)vcs;
							manager.getFirstRevision(bb);
						} catch (Exception e) {
							System.out.println("Manager exception when call SVN getFirstRevision for Project: " + project.getShortName());
							test = false;
						}
					}
				}
			}
		}
		System.out.println("Total Git VCS = " + countGit );
		System.out.println("Total SVN VCS = " + countSVN );
		assertTrue(test);
	}
}
