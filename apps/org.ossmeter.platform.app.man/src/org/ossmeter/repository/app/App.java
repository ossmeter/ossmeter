package org.ossmeter.repository.app;

import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.ExtensionPointMetricProviderManager;
import org.ossmeter.platform.IMetricProviderManager;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.bugtrackingsystem.ServiceBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.communicationchannel.ServiceCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.platform.delta.vcs.ServiceVcsManager;
import org.ossmeter.repository.model.Bugzilla;
import org.ossmeter.repository.model.GitRepository;
import org.ossmeter.repository.model.NntpNewsGroup;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.SvnRepository;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class App implements IApplication {
	
	public void run(IMetricProviderManager metricProviderManager, 
			PlatformVcsManager platformVcsManager, 
				PlatformCommunicationChannelManager platformCommunicationChannelManager, 
				PlatformBugTrackingSystemManager platformBugTrackingSystemManager) throws Exception {
		Mongo mongo = new Mongo();
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		Platform platform = new Platform(mongo);
		platform.setMetricProviderManager(metricProviderManager);
		platform.setPlatformVcsManager(platformVcsManager);
		platform.setPlatformCommunicationChannelManager(platformCommunicationChannelManager);
		platform.setPlatformBugTrackingSystemManager(platformBugTrackingSystemManager);
		
		//platform.getProjectRepositoryManager().reset();
//		addSampleSvnProject("pongo", "https://pongo.googlecode.com/svn", platform);
//		addSampleSvnProject("hamcrest", "http://hamcrest.googlecode.com/svn/", platform);
		
//		addSampleGitProject("saf", "https://code.google.com/p/super-awesome-fighter", platform);

		//addSampleSvnProject("jMonkeyEngine", "http://jmonkeyengine.googlecode.com/svn/", platform);

//		addSampleProjectWithNewsGroup("epsilon", "news.eclipse.org", "eclipse.epsilon", true, "exquisitus", "flinder1f7", 80, 10000, platform);
//		addSampleProjectWithNewsGroup("thunderbird", "news.mozilla.org", "mozilla.support.thunderbird", false, null, null, 80, 10000, platform);

		addSampleProjectWithBugTrackingSystem("fedora", "https://bugzilla.redhat.com/xmlrpc.cgi", "Fedora", "acpi", platform); // "acpi", platform);

		platform.run();
	}
	
	protected void addSampleGitProject(String name, String url, Platform platform) {
		Project project = new Project();
		project.setName(name);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		
		GitRepository repo = new GitRepository();
		repo.setUrl(url);
		
		project.getVcsRepositories().add(repo);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	protected void addSampleSvnProject(String name, String url, Platform platform ) {
		Project project = new Project();
		project.setName(name);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		SvnRepository svnRepository = new SvnRepository();
		svnRepository.setUrl(url);
		project.getVcsRepositories().add(svnRepository); 
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	protected void addSampleProjectWithNewsGroup(
			String name, String url, String newsGroupName, 
			Boolean authenticationRequired, String username, String password, 
			int port, int interval, Platform platform){
		Project project = new Project();
		project.setName(name);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		NntpNewsGroup newsGroup = new NntpNewsGroup();
		newsGroup.setUrl(url+'/' + newsGroupName);
		newsGroup.setAuthenticationRequired(authenticationRequired);
		newsGroup.setUsername(username);
		newsGroup.setPassword(password);
		newsGroup.setPort(port);
		newsGroup.setInterval(interval);
		newsGroup.setLastArticleChecked("-1");
		project.getCommunicationChannels().add(newsGroup);
		platform.getProjectRepositoryManager().getProjectRepository().sync();		
	}
	
	protected void addSampleProjectWithBugTrackingSystem(
			String name, String url, String product, String component, Platform platform){
		Project project = new Project();
		project.setName(name);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		Bugzilla bugzilla = new Bugzilla();
		bugzilla.setUrl(url);
		bugzilla.setProduct(product);
		if (component!=null)
			bugzilla.setComponent(component);
		project.getBugTrackingSystems().add(bugzilla);
		platform.getProjectRepositoryManager().getProjectRepository().sync();		
	}

	public static void main(String[] args) throws Exception {
		
		SimpleMetricProviderManager metricProviderManager =  new SimpleMetricProviderManager();
		metricProviderManager.getMetricProviders();
//		metricProviderManager.getMetricProviders().add(new SvnLocMetricProvider());
//		metricProviderManager.getMetricProviders().add(new NntpMessageCounterProvider());
		
//		metricProviderManager.getMetricProviders().add(new ProfanityCounterMetricProvider());
		
//		metricProviderManager.getMetricProviders().add(new org.ossmeter.metricprovider.loc.LocMetricProvider());
//		metricProviderManager.getMetricProviders().add(new GenericTotalLocMetricProvider());
		
		new App().run(metricProviderManager, 
				new ServiceVcsManager(), 
				new ServiceCommunicationChannelManager(),
				new ServiceBugTrackingSystemManager());
		
		IExtensionRegistry registry = org.eclipse.core.runtime.Platform.getExtensionRegistry();
//		registry.getExtensionPoint("").
		
	}
	
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		run(new ExtensionPointMetricProviderManager(), 
				new ExtensionPointVcsManager(), 
				new ExtensionPointCommunicationChannelManager(),
				new ExtensionPointBugTrackingSystemManager());
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		//nothing to do		
	}
}
