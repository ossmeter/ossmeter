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
package org.ossmeter.platform.app.example;

import java.text.ParseException;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.app.example.util.ProjectCreationUtil;
import org.ossmeter.platform.delta.NoManagerFoundException;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.ossmeter.repository.model.VcsRepository;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class App implements IApplication { 

	@Override
	public Object start(IApplicationContext context) throws Exception {
		// Add projects to the DB here 
		Mongo mongo = new Mongo(); 
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(mongo);
		
//		Project pongo = ProjectCreationUtil.createSvnProject("pongo", "http://pongo.googlecode.com/svn/trunk");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pongo);
		
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("xText", "news.eclipse.org", "eclipse.modeling.tmf", true, "exquisitus", "flinder1f7", 80, 10000);
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("epsilon", "news.eclipse.org", "eclipse.epsilon", true, "exquisitus", "flinder1f7", 80, 10000);
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("thunderbird", "news.mozilla.org", "mozilla.support.thunderbird", false, null, null, 80, 10000);
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("eclipsePlatform", "news.eclipse.org", "eclipse.platform", true, "exquisitus", "flinder1f7", 80, 10000);
  	//	Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("BIRT", "news.eclipse.org", "eclipse.birt", true, "exquisitus", "flinder1f7", 80, 10000);
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("SiriusForum", "news.eclipse.org", "eclipse.sirius", true, "exquisitus", "flinder1f7", 80, 10000);
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("toolsEmf", "news.eclipse.org", "eclipse.tools.emf", true, "exquisitus", "flinder1f7", 80, 10000);
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("Log4J", "news.gmane.org", "gmane.comp.jakarta.log4j.user", false, null, null, 119, 10000);
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("tomcat-user", "news.gmane.org", "gmane.comp.jakarta.tomcat.user", false, null, null, 119, 10000);

		
//		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("epsilon", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "epsilon", null);
//		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("eclipse", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "platform", null);
//		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("modelinggmtamw", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "GMT", "AMW");
	//	Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("modelinggmt", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "GMT", null);
//		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("xText", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "TMF", "Xtext");
//		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("fedora", "https://bugzilla.redhat.com/xmlrpc.cgi", "Fedora", "acpi");
//		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("bugzilla", "https://bugzilla.redhat.com/xmlrpc.cgi", "Bugzilla", null);

		//		Url: https://bugs.eclipse.org/bugs/xmlrpc.cgi 
//			Product: MDT.Papyrus 
//			Components: Core, Diagram, Others, Table, Views


//		addSampleProjectWithBugTrackingSystem("MDT-Papyrus", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "MDT.Papyrus", "Core", platform);
//		addSampleProjectWithBugTrackingSystem("TMF-Xtext", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "TMF", "Xtext", platform);

		
//		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("epsilon", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "epsilon", null);
//		Project pdb = ProjectCreationUtil.createGitProject("rascal", "file:///Users/jurgenv/Workspaces/Rascal/rascal");

	//	pdb.getExecutionInformation().setMonitor(true);
		
	//	platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pdb);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		System.err.println(platform.getBugTrackingSystemManager().getBugTrackingSystemManagers());
		
		mongo.close();
		
		// Start the application
		return  null;
//		return  new OssmeterApplication().start(context);
	}

	protected Date getLastExecutedDate(Platform platform, Project project) {
		Date lastExec;
		String lastExecuted = project.getExecutionInformation().getLastExecuted();
		
		if(lastExecuted.equals("null") || lastExecuted.equals("")) {
			lastExec = new Date();
			
			for (VcsRepository repo : project.getVcsRepositories()) {
				// This needs to be the day BEFORE the first day! (Hence the addDays(-1)) 
				try {
					Date d = platform.getVcsManager().getDateForRevision(repo, platform.getVcsManager().getFirstRevision(repo)).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					} 
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
				try {
					Date d = platform.getCommunicationChannelManager().getFirstDate(platform.getMetricsRepository(project).getDb(), communicationChannel);
					if (d == null) continue;
					d = d.addDays(-1);
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (BugTrackingSystem bugTrackingSystem : project.getBugTrackingSystems()) {
				try {
					Date d = platform.getBugTrackingSystemManager().getFirstDate(platform.getMetricsRepository(project).getDb(), bugTrackingSystem).addDays(-1);
					if (d == null) continue;
					if (lastExec.compareTo(d) > 0) {
						lastExec = d;
					}
				} catch (NoManagerFoundException e) {
					System.err.println(e.getMessage());					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			try {
				lastExec = new Date(lastExecuted);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return lastExec;
	}
	
	
	protected boolean nonDuplicatingProjectInsertion(Project project, ProjectRepository repo) {
		if (project.getShortName() == null || "".equals(project.getShortName())) {
			throw new RuntimeException("Invalid project short name.");
		}
		if (repo.getProjects().findOneByShortName(project.getShortName()) != null) return false;
		
		repo.getProjects().add(project);
		
		return true;
	}
	
	@Override
	public void stop() {
		//nothing to do	
	}
}
