package org.ossmeter.platform.app.example;

import java.text.ParseException;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.NoManagerFoundException;
import org.ossmeter.platform.osgi.OssmeterApplication;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectExecutionInformation;
import org.ossmeter.repository.model.ProjectRepository;
import org.ossmeter.repository.model.VcsRepository;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class App implements IApplication { 

	@Override
	public Object start(IApplicationContext context) throws Exception {
		// Add projects to the DB here 
		Mongo mongo = new Mongo(); // FIXME: this needs to connect to the replica set
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(mongo);
		
		// THIS NEEDS TO RUN DUE TO UPDATES IN THE PROJECT MODEL	
		for (Project p : platform.getProjectRepositoryManager().getProjectRepository().getProjects()) {
			try {
				if (p.getExecutionInformation() == null) p.setExecutionInformation(new ProjectExecutionInformation());
			} catch (RuntimeException e) {
				p.setExecutionInformation(new ProjectExecutionInformation());
			}
			
			p.getExecutionInformation().setMonitor(false);
		}
		
		// Start the application
		return  new OssmeterApplication().start(context);
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
					Date d = platform.getBugTrackingSystemManager().getFirstDate(bugTrackingSystem).addDays(-1);
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
