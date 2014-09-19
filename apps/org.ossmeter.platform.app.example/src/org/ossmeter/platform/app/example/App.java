package org.ossmeter.platform.app.example;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.app.example.util.ProjectCreationUtil;
import org.ossmeter.platform.osgi.OssmeterApplication;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectExecutionInformation;
import org.ossmeter.repository.model.ProjectRepository;

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
		ProjectRepository repo = platform.getProjectRepositoryManager().getProjectRepository();
		
//		ProjectCollection coll = repo.getProjects();
//		for (Project p : coll) {
//			coll.remove(p);
//		}
		
//		Project pongo = ProjectCreationUtil.createSvnProject("pongo", "http://pongo.googlecode.com/svn/trunk");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pongo);
		
//		Project pdb = ProjectCreationUtil.createProjectWithNewsGroup("xText", "news.eclipse.org", "eclipse.modeling.tmf", true, "exquisitus", "flinder1f7", 80, 10000);
		Project pdb = ProjectCreationUtil.createProjectWithBugTrackingSystem("epsilon", "https://bugs.eclipse.org/bugs/xmlrpc.cgi", "epsilon", null);
//		Project pdb = ProjectCreationUtil.createGitProject("rascal", "file:///Users/jurgenv/Workspaces/Rascal/rascal");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pdb);

		
		for (Project p : platform.getProjectRepositoryManager().getProjectRepository().getProjects()) {
			try {
				if (p.getExecutionInformation() == null) p.setExecutionInformation(new ProjectExecutionInformation());
			} catch (RuntimeException e) {
				p.setExecutionInformation(new ProjectExecutionInformation());
			}
			p.getExecutionInformation().setMonitor(true);
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();
			
		// Synchronise the changes and close the connection
		
//		EclipseProjectImporter importer = new EclipseProjectImporter();
//		System.err.println(importer.importProject("modeling.epsilon", platform));
//		
		repo.sync();
		mongo.close();
		
		// Start the application
		return new OssmeterApplication().start(context);
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
