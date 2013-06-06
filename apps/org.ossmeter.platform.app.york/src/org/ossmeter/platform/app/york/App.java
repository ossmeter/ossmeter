package org.ossmeter.platform.app.york;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.ExtensionPointMetricProviderManager;
import org.ossmeter.platform.IMetricProviderManager;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.app.york.util.ProjectCreationUtil;
import org.ossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class App implements IApplication {
	
	public void run(IMetricProviderManager metricProviderManager, PlatformVcsManager platformVcsManager,
			PlatformCommunicationChannelManager communicationChannelManager, PlatformBugTrackingSystemManager bugTrackingSystemManager) throws Exception {
		Mongo mongo = new Mongo();
		
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		Platform platform = new Platform(mongo);
		platform.setMetricProviderManager(metricProviderManager);
		platform.setPlatformVcsManager(platformVcsManager);
		platform.setPlatformCommunicationChannelManager(communicationChannelManager);
		platform.setPlatformBugTrackingSystemManager(bugTrackingSystemManager);
		
		// FIXME: Needs to check Mongo for projects, not keep registering the same ones!
		
//		Project pongo = ProjectCreationUtil.createSvnProject("pongo", "https://pongo.googlecode.com/svn");
//		Project saf = ProjectCreationUtil.createGitProject("saf", "https://code.google.com/p/super-awesome-fighter");
//		Project fedora = ProjectCreationUtil.createProjectWithBugTrackingSystem("fedora", "https://bugzilla.redhat.com/xmlrpc.cgi", "Fedora", "acpi"); // "acpi", platform);
		//Project mojambo = ProjectCreationUtil.createGitHubProject("mojambo", "grit");
//		Project skim = ProjectCreationUtil.createSourceForgeProject("skim-app");
		Project epsilon = ProjectCreationUtil.createProjectWithNewsGroup("epsilon", "news.eclipse.org", "eclipse.epsilon", true, "exquisitus", "flinder1f7", 80, 10000);
		
		
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pongo);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(saf);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(fedora);
//		//platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(mojambo);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(skim);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(epsilon);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		platform.run();
	}
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		run(new ExtensionPointMetricProviderManager(), new ExtensionPointVcsManager(), new ExtensionPointCommunicationChannelManager(), new ExtensionPointBugTrackingSystemManager());
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		//nothing to do	
	}
}
