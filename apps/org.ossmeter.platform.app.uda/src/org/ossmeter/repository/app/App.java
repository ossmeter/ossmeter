package org.ossmeter.repository.app;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.ExtensionPointMetricProviderManager;
import org.ossmeter.platform.IMetricProviderManager;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.repository.model.github.GitHubProject;
import org.ossmeter.repository.model.github.GitHubRepository;
import org.ossmeter.repository.model.github.GitHubUser;
import org.ossmeter.repository.model.sourceforge.SourceForgeProject;

import com.mongodb.Mongo;

public class App implements IApplication {
	
	public void run(IMetricProviderManager metricProviderManager, PlatformVcsManager platformVcsManager) throws Exception {
		Mongo mongo = new Mongo();
		Platform platform = new Platform(mongo);
		platform.setMetricProviderManager(metricProviderManager);
		platform.setPlatformVcsManager(platformVcsManager);
		
		addSampleGitHubProject("mojombo", "grit", platform);
		addSourceForgeProject("skim-app", platform);
		
		//platform.getProjectRepositoryManager().reset();
		//addSampleSvnProject("pongo", "https://pongo.googlecode.com/svn", platform);
		//addSampleSvnProject("hamcrest", "http://hamcrest.googlecode.com/svn/", platform);
		
//		addSampleGitProject("saf", "https://code.google.com/p/super-awesome-fighter", platform);

		//addSampleSvnProject("jMonkeyEngine", "http://jmonkeyengine.googlecode.com/svn/", platform);
		//addSampleProjectWithNewsGroup("epsilon", "news.eclipse.org","eclipse.epsilon", "exquisitus", "flinder1f7", platform);
		
		platform.run();
	}
	
	
	protected void addSampleGitHubProject(String login, String repository, Platform platform) {
		GitHubProject project = new GitHubProject();
		project.setName(login + "-" + repository);
		
		GitHubRepository gitHubRepository = new GitHubRepository();
		gitHubRepository.setName(repository);
		
		GitHubUser owner = new GitHubUser();
		owner.setLogin(login);
		gitHubRepository.setOwner(owner);
		
		project.getVcsRepositories().add(gitHubRepository);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	
	protected void addSourceForgeProject(String name, Platform platform) {
		SourceForgeProject project = new SourceForgeProject();
		project.setName(name);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		/*
		SvnRepository svnRepository = new SvnRepository();
		svnRepository.setUrl("http://" + name + ".svn.sourceforge.net/svnroot/" + name);
		project.getVcsRepositories().add(svnRepository); */
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	/*
	protected void addSampleProjectWithNewsGroup(String name, String host, String url, String usr, String pass, Platform platform){
		Project project = new Project();
		project.setName(name);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		NntpNewsGroup newsGroup = new NntpNewsGroup();
		newsGroup.setUrl(url);
		newsGroup.setUsr(usr);
		newsGroup.setPass(pass);
		project.getNewsgroups().add(newsGroup);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
	}
	
	public static void main(String[] args) throws Exception {
		
		SimpleMetricProviderManager metricProviderManager =  new SimpleMetricProviderManager();
		metricProviderManager.getMetricProviders();
//		metricProviderManager.getMetricProviders().add(new SvnLocMetricProvider());
//		metricProviderManager.getMetricProviders().add(new NntpMessageCounterProvider());
		
//		metricProviderManager.getMetricProviders().add(new ProfanityCounterMetricProvider());
		
//		metricProviderManager.getMetricProviders().add(new org.ossmeter.metricprovider.loc.LocMetricProvider());
		metricProviderManager.getMetricProviders().add(new GenericTotalLocMetricProvider());
		
		new App().run(metricProviderManager, new ServiceVcsManager());
		
		IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
//		registry.getExtensionPoint("").
		
	}
	*/
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		run(new ExtensionPointMetricProviderManager(), new ExtensionPointVcsManager());
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		//nothing to do		
	}
}
