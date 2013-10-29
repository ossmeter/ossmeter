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
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;

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

		Project openOffice = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"OpenOffice2", "openoffice", "https://svn.apache.org/repos/asf/openoffice/trunk/main",
				"https://issues.apache.org/ooo/xmlrpc.cgi", "General", "null",
				"subversion.user", "news.gmane.org/gmane.comp.version-control.subversion.user", false, "", "");	
		
		Project ant = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Ant", "ant", "http://svn.apache.org/repos/asf/ant/core/trunk/",
				"https://issues.apache.org/bugzilla/xmlrpc.cgi", "Ant", "Core",
				"ant-user", "news.gmane.org/gmane.comp.jakarta.ant.user", false, "", "");
		
		Project log4j = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Log4J", "log4j", "http://svn.apache.org/repos/asf/logging/log4j/trunk",
				"https://issues.apache.org/bugzilla/xmlrpc.cgi", "Log4j", "null", 
				"ant-user", "news.gmane.org/gmane.comp.jakarta.log4j.user", false, "", "");
		
		Project tomcat = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Tomcat", "tomcat", "http://svn.apache.org/repos/asf/tomcat/trunk/",
				"https://issues.apache.org/bugzilla/xmlrpc.cgi", "Tomcat 8", "Manager",
				"ant-user", "news.gmane.org/gmane.comp.jakarta.tomcat.user", false, "", "");
		
		// FIXME: subversion  currently cannot connect to bugzilla
		Project subversion = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Subversion", "subversion", "http://svn.apache.org/repos/asf/subversion/trunk",
				"http://subversion.tigris.org/issues/xmlrpc.cgi", "subversion", "null",
				"openoffice.user", "news.gmane.org/gmane.comp.apache.openoffice.user", false, "", "");
		
		Project subversive = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Subversive", "subversive", "http://dev.eclipse.org/svnroot/technology/org.eclipse.subversive/trunk",
				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "Subversive", "null",
				"openoffice.user", "news.eclipse.org/eclipse.technology.subversive", true, "exquisitus", "flinder1f7");

		Project epsilon = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Epsilon", "epsilon", "http://dev.eclipse.org/svnroot/modeling/org.eclipse.epsilon/trunk",
				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "Epsilon", "null",
				"epsilon", "news.eclipse.org/eclipse.epsilon", true, "exquisitus", "flinder1f7");

		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(openOffice);
		
		openOffice.getBugTrackingSystems().clear();
		openOffice.getVcsRepositories().clear();
		
/*
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(subversive);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(tomcat);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(log4j);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(epsilon);
 		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(openOffice);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(ant);
*/
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// FEDORA
		// GIT: https://github.com/fcrepo/fcrepo.git
		// Bugzilla: https://bugzilla.redhat.com/xmlrpc.cgi (product = Fedora)
		// NNTP: ???
		
		// Eclipse platform
		// GIT: git.eclipse.org/gitroot/platform/eclipse.platform.git
		// Bugzilla: https://bugs.eclipse.org/bugs/describecomponents.cgi?product=Platform
		
		platform.run();
		
//		while (true) {
//			if (1 > 2) break;
//		}
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
