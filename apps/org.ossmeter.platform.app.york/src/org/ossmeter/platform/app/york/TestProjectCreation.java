package org.ossmeter.platform.app.york;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class TestProjectCreation {
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		
		Platform platform = new Platform(mongo);
		
		ProjectRepository repo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Project p1 = new Project();
		p1.setName("p1");
		
		// The parent project MUST be added into the collection in order to
		// create a reference to it
		repo.getProjects().add(p1);
		
		Project p2 = new Project();
		p2.setName("p2");
		p2.setParent(p1);
		
		repo.getProjects().add(p2);
		
		repo.sync();
			
		Project p2x = repo.getProjects().findOneByName("p2");
		Project p1x = p2x.getParent();
		
		System.out.println(p1x.getName() + " is parent of " + p2x.getName());
	}
}
