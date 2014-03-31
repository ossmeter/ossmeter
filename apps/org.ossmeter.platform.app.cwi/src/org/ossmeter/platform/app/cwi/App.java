package org.ossmeter.platform.app.cwi;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.ossmeter.platform.ExtensionPointMetricProviderManager;
import org.ossmeter.platform.IMetricProviderManager;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.app.cwi.util.ProjectCreationUtil;
import org.ossmeter.platform.delta.bugtrackingsystem.ExtensionPointBugTrackingSystemManager;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.ExtensionPointCommunicationChannelManager;
import org.ossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.ossmeter.platform.delta.vcs.ExtensionPointVcsManager;
import org.ossmeter.platform.delta.vcs.PlatformVcsManager;
import org.ossmeter.repository.model.LocalStorage;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class App implements IApplication {
	
	public void run(IMetricProviderManager metricProviderManager, PlatformVcsManager platformVcsManager,
						PlatformCommunicationChannelManager communicationChannelManager, PlatformBugTrackingSystemManager bugTrackingSystemManager) throws Exception {

		Mongo mongo = new Mongo();
		mongo.dropDatabase("ossmeter");
		mongo.dropDatabase("pongo");
		mongo.dropDatabase("epsilon");
		mongo.dropDatabase("hamcrest");
		mongo.dropDatabase("jMonkeyEngine");
		mongo.dropDatabase("thunderbird");
		mongo.dropDatabase("fedora");
		mongo.dropDatabase("saf");
		mongo.dropDatabase("mojambo-grit");
		
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		Platform platform = new Platform(mongo);
		platform.setMetricProviderManager(metricProviderManager);
		platform.setPlatformVcsManager(platformVcsManager);
//		platform.setPlatformCommunicationChannelManager(communicationChannelManager);
//		platform.setPlatformBugTrackingSystemManager(bugTrackingSystemManager);
		
		// FIXME: Needs to check Mongo for projects, not keep registering the same ones!
		
//		Project pongo = ProjectCreationUtil.createSvnProject("pongo", "http://pongo.googlecode.com/svn/trunk/");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pongo);
//		Project tomcat = ProjectCreationUtil.createSvnProject("tomcat", "http://svn.apache.org/repos/asf/tomcat/trunk/");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(tomcat);
		Project saf = ProjectCreationUtil.createGitProject("saf", "https://code.google.com/p/super-awesome-fighter");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(saf);
//		Project fedora = ProjectCreationUtil.createProjectWithBugTrackingSystem("fedora", "https://bugzilla.redhat.com/xmlrpc.cgi", "Fedora", "acpi"); // "acpi", platform);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(fedora);
//		Project mojambo = ProjectCreationUtil.createGitHubProject("mojambo", "grit", "https://github.com/mojombo/grit.git");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(mojambo);
//		Project skim = ProjectCreationUtil.createSourceForgeProject("skim-app");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(skim);
//		Project epsilon = ProjectCreationUtil.createProjectWithNewsGroup("epsilon", "news.eclipse.org", "eclipse.epsilon", true, "exquisitus", "flinder1f7", 80, 10000);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(epsilon);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
//		while (true) {
//			if (1 > 2) break;
//		}
		
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
