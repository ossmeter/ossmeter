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
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pongo);
//		Project saf = ProjectCreationUtil.createGitProject("saf", "https://code.google.com/p/super-awesome-fighter");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(saf);
//		Project fedora = ProjectCreationUtil.createProjectWithBugTrackingSystem("fedora", "https://bugzilla.redhat.com/xmlrpc.cgi", "Fedora", "acpi"); // "acpi", platform);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(fedora);
//		Project mojambo = ProjectCreationUtil.createGitHubProject("mojambo", "grit", "https://github.com/mojombo/grit.git");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(mojambo);
//		Project skim = ProjectCreationUtil.createSourceForgeProject("skim-app");
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(skim);
//		Project epsilon = ProjectCreationUtil.createProjectWithNewsGroup("epsilon", "news.eclipse.org", "eclipse.epsilon", true, "exquisitus", "flinder1f7", 80, 10000);
//		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(epsilon);

//		platform.getProjectRepositoryManager().getProjectRepository().sync();

//		Project openOffice = ProjectCreationUtil.createProjectSvnNntpBugzilla(
//				"OpenOffice2", "openoffice", "Description", 
//				"https://svn.apache.org/repos/asf/openoffice/trunk/main",
//				"https://issues.apache.org/ooo/xmlrpc.cgi", "General", "null",
//				"openoffice.user", "news.gmane.org/gmane.comp.apache.openoffice.user", false, "", "");	
//		
//		Project ant = ProjectCreationUtil.createProjectSvnNntpBugzilla(
//				"Ant", "ant", "Description", 
//				"http://svn.apache.org/repos/asf/ant/core/trunk/",
//				"https://issues.apache.org/bugzilla/xmlrpc.cgi", "Ant", "Core",
//				"ant-user", "news.gmane.org/gmane.comp.jakarta.ant.user", false, "", "");
//		
//		Project log4j = ProjectCreationUtil.createProjectSvnNntpBugzilla(
//				"Log4J", "log4j", "Description", 
//				"http://svn.apache.org/repos/asf/logging/log4j/trunk",
//				"https://issues.apache.org/bugzilla/xmlrpc.cgi", "Log4j", "null", 
//				"ant-user", "news.gmane.org/gmane.comp.jakarta.log4j.user", false, "", "");
//		
		Project tomcat = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Tomcat", "tomcat", "Description", 
				"http://svn.apache.org/repos/asf/tomcat/trunk/",
				"https://issues.apache.org/bugzilla/xmlrpc.cgi", "Tomcat 8", "Manager",
				"tomcat-user", "news.gmane.org/gmane.comp.jakarta.tomcat.user", false, "", "");
//		
//		// FIXME: subversion  currently cannot connect to bugzilla
//		Project subversion = ProjectCreationUtil.createProjectSvnNntpBugzilla(
//				"Subversion", "subversion", "Description", 
//				"http://svn.apache.org/repos/asf/subversion/trunk",
//				"http://subversion.tigris.org/issues/xmlrpc.cgi", "subversion", "null",
//				"subversion.user", "news.gmane.org/gmane.comp.apache.openoffice.user", false, "", "");
//		
//		Project subversive = ProjectCreationUtil.createProjectSvnNntpBugzilla(
//				"Subversive", "subversive", "Description", 
//				"http://dev.eclipse.org/svnroot/technology/org.eclipse.subversive/trunk",
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "Subversive", "null",
//				"subversion.user", "news.eclipse.org/eclipse.technology.subversive", true, "exquisitus", "flinder1f7");
////
//		Project epsilon = ProjectCreationUtil.createProjectSvnNntpBugzilla(
//				"Epsilon", "epsilon", "Description", 
//				"http://dev.eclipse.org/svnroot/modeling/org.eclipse.epsilon/trunk",
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "Epsilon", "null",
//				"epsilon", "news.eclipse.org/eclipse.epsilon", true, "exquisitus", "flinder1f7");

		// This is just for the MSR paper
		tomcat.getVcsRepositories().clear();
		tomcat.getBugTrackingSystems().clear();
		
		
		addProjectNonDuplicating(platform, tomcat);
//		addProjectNonDuplicating(platform, epsilon);
//		addProjectNonDuplicating(platform, subversive);
//		addProjectNonDuplicating(platform, log4j);
//		addProjectNonDuplicating(platform, ant);
		platform.getProjectRepositoryManager().getProjectRepository().sync();

//		System.err.println("The platform is not running. No projects are being scheduled. In Client API debug mode. Uncomment platform.run() if you want to analyse projects.");
		platform.run();
		
//		while (true) {
//			Thread.sleep(10000);
//		}
	}
	
	private void addProjectNonDuplicating(Platform platform, Project project) {
		if (platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(project.getName()) == null) {
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		}
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
