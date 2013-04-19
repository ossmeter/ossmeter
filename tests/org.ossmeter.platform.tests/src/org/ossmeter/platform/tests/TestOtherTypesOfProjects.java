package org.ossmeter.platform.tests;

import org.junit.Test;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.GitRepository;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.github.GitHubProject;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class TestOtherTypesOfProjects {
	
	@Test
	public void testCreateGitProject() throws Exception {
		
		GitHubProject project = new GitHubProject();
		
		Platform platform = new Platform(new Mongo());
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
	}
	
	@Test
	public void testGetGitProject() throws Exception {
		
		PongoFactory.getInstance().clear();
		
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		Platform platform = new Platform(new Mongo());
		for (Project project : platform.getProjectRepositoryManager().getProjectRepository().getProjects()) {
			System.err.println(project);
		}
		
	}
	
}
