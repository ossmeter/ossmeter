package org.ossmeter.rascal.test;

import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.vcs.git.GitRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;

/**
 * This class is purely for illustration purposes and is not intended for release.
 * @author jimmy
 *
 */
public class ProjectCreationUtil {
	
	public static Project createGitProject(String name, String url) {
		Project project = new Project();
		project.setName(name);
		project.setShortName(name);
		project.setDescription(name);
		
		GitRepository repo = new GitRepository();
		repo.setUrl(url);
		
		project.getVcsRepositories().add(repo);
		return project;
	}
	
	
	public static Project createSvnProject(String name, String url) {
		Project project = new Project();
		project.setName(name);
		project.setShortName(name);
		SvnRepository svnRepository = new SvnRepository();
		svnRepository.setUrl(url);
		project.getVcsRepositories().add(svnRepository); 
		return project;
	}
	
//	
//	public static Project createGitHubProject(String login, String repository, String url) {
//		GitHubProject project = new GitHubProject();
//		project.setName(login + "-" + repository);
//		
//		GitHubRepository gitHubRepository = new GitHubRepository();
//		gitHubRepository.setName(repository);
//		gitHubRepository.setUrl(url);
//		
//		GitHubUser owner = new GitHubUser();
//		owner.setLogin(login);
//		gitHubRepository.setOwner(owner);
//		
//		project.getVcsRepositories().add(gitHubRepository);
//		return project;
//	}
	
	
//	public static Project createSourceForgeProject(String name) {
//		SourceForgeProject project = new SourceForgeProject();
//		project.setName(name);
//		/* TODO: Why was this code commented out?
//		SvnRepository svnRepository = new SvnRepository();
//		svnRepository.setUrl("http://" + name + ".svn.sourceforge.net/svnroot/" + name);
//		project.getVcsRepositories().add(svnRepository); */
//		return project;
//	}
}