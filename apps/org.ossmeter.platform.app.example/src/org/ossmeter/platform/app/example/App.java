package org.ossmeter.platform.app.example;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.app.example.util.ProjectCreationUtil;
import org.ossmeter.platform.osgi.OssmeterApplication;
import org.ossmeter.repository.model.LocalStorage;
import org.ossmeter.repository.model.Project;
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
		
		Project pongo = ProjectCreationUtil.createSvnProject("pongo", "http://pongo.googlecode.com/svn/trunk");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pongo);
		
		// Synchronise the changes and close the connection
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
