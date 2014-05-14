package org.ossmeter.platform.app.york;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectCollection;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class Models2014Setup {
	public static void main(String[] args) throws Exception {
		List<ServerAddress> addrs = new ArrayList<>();
//		addrs.add(new ServerAddress("144.32.156.177", 27017));
//		addrs.add(new ServerAddress("144.32.156.148", 27017));
//		addrs.add(new ServerAddress("144.32.156.184", 27017));
		addrs.add(new ServerAddress("localhost", 27017));
		Mongo mongo = new Mongo(addrs);
		
		Platform platform = new Platform(mongo);
		
		ProjectCollection projects = platform.getProjectRepositoryManager().getProjectRepository().getProjects();
		
//		projects.findOneByShortName("modeling.emf");
//		projects.findOneByShortName("modeling.mmt.atl");
//		projects.findOneByShortName("modeling.epsilon");
//		projects.findOneByShortName("modeling.gmp.gmf-runtime");
//		projects.findOneByShortName("modeling.tmf.xtext");
//		projects.findOneByShortName("modeling.viatra2");
//		projects.findOneByShortName("modeling.gmt.amw");
//		projects.findOneByShortName("modeling.mdt.papyrus");
//		projects.findOneByShortName("modeling.mdt.modisco");
//		projects.findOneByShortName("modeling.emfcompare");
////		projects.findOneByShortName("tools.gef");
//		projects.findOneByShortName("modeling.gmp.graphiti");
	
		
//		updateInformation(projects, "modeling.emf", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "EMF", Arrays.asList("Core"), 
//				"eclipse.tools.emf", "news.eclipse.org/eclipse.tools.emf");
//		
//		updateInformation(projects, "modeling.mmt.atl", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "GMT", Arrays.asList("ATL"), 
//				"eclipse.atl", "news.eclipse.org/eclipse.atl");
//		
//		updateInformation(projects, "modeling.epsilon", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "Epsilon", Arrays.asList("Core"), 
//				"eclipse.epsilon", "news.eclipse.org/eclipse.epsilon");
//		
//		updateInformation(projects, "modeling.gmp.gmf-runtime", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "GMF-Runtime", Arrays.asList("General"), 
//				"eclipse.modeling.gmf", "news.eclipse.org/eclipse.modeling.gmf");
//		
//		updateInformation(projects, "modeling.tmf.xtext", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "TMF", Arrays.asList("Xtext"), 
//				"eclipse.modeling.tmf", "news.eclipse.org/eclipse.modeling.tmf");
//		
//		updateInformation(projects, "modeling.viatra2", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "GMT", Arrays.asList("VIATRA2"), 
//				"eclipse.viatra2", "news.eclipse.org/eclipse.viatra2");
//		
//		updateInformation(projects, "modeling.gmt.amw", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "GMT", Arrays.asList("AMW"), 
//				"eclipse.amw", "news.eclipse.org/eclipse.amw");
//		
//		updateInformation(projects, "modeling.mdt.papyrus", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "MDT.Papyrus", Arrays.asList("Core", "Diagram", "Others", "Table", "Views"), 
//				"eclipse.papyrus", "news.eclipse.org/eclipse.papyrus");
		
//		updateInformation(projects, "modeling.mdt.modisco", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "MDT.MoDisco", Arrays.asList("Main", "Contribution", "Infrastructure", "Technologies", "UseCases"), 
//				"eclipse.modisco", "news.eclipse.org/eclipse.modisco");
		
//		createProject(projects, "eclipse.platform", 
//				"", "", Arrays.asList(""), 
//				"eclipse.platform", "news.eclipse.org/eclipse.platform");

//		createProject(projects, "modeling.mdt.xsd", 
//				"", "", Arrays.asList(""), 
//				"modeling.mdt.xsd", "news.eclipse.org/eclipse.technology.xsd");
		
//		createProject(projects, "modeling.emf", 
//				"", "", Arrays.asList(""), 
//				"eclipse.tools.emf", "news.eclipse.org/eclipse.tools.emf");
		
//		createProject(projects, "eclipse.modeling.m2t", 
//				"", "", Arrays.asList(""), 
//				"eclipse.modeling.m2t", "news.eclipse.org/eclipse.modeling.m2t");
//		
//		createProject(projects, "eclipse.technology.emft", 
//				"", "", Arrays.asList(""), 
//				"eclipse.technology.emft", "news.eclipse.org/eclipse.technology.emft");
		
		createProject(projects, "modeling.mdt.uml2", 
				"", "", Arrays.asList(""), 
				"modeling.mdt.uml2", "news.eclipse.org/eclipse.modeling.mdt.uml2");
		
//		updateInformation(projects, "modeling.emfcompare", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "EMFCompare", Arrays.asList("Core","Diagram","Team","UI"), 
//				"eclipse.modeling.", "news.eclipse.org/eclipse.atl");
		
//		updateInformation(projects, "modeling.gmp.graphiti", 
//				"https://bugs.eclipse.org/bugs/xmlrpc.cgi", "GMP", Arrays.asList("Graphiti"), 
//				"eclipse.graphiti", "news.eclipse.org/eclipse.graphiti");
		
		projects.sync();
	}
	
	protected static void createProject(ProjectCollection projects, String projectName, 
			String bugzillaUrl, String bugzillaProduct, List<String> bugzillaComponents, 
			String nntpName, String nntpUrl) {
		Project project = new Project();
		project.setName(projectName.replace(".", ""));
		project.setShortName(projectName);
		project.setLastExecuted("");
		project.getMetricProviderData().clear();

		// NNTP
		project.getCommunicationChannels().clear();
		
		NntpNewsGroup nntp = new NntpNewsGroup();
		nntp.setName(nntpName);
		nntp.setUrl(nntpUrl);
		nntp.setAuthenticationRequired(true);
		nntp.setUsername("exquisitus");
		nntp.setPassword("flinder1f7");
		nntp.setPort(80);
		nntp.setLastArticleChecked("-1");
		project.getCommunicationChannels().add(nntp);
		
		projects.add(project);
		projects.sync();
	}

	protected static void updateInformation(ProjectCollection projects, String projectName, 
			String bugzillaUrl, String bugzillaProduct, List<String> bugzillaComponents, 
			String nntpName, String nntpUrl) {
		
		Project project = projects.findOneByShortName(projectName);
		
		if (project == null) {
			System.err.println("Project " + projectName + "not found in database.");
			return;
		}
		
		// Reset
		project.setName(projectName.replace(".", ""));
		project.setLastExecuted("");
		project.getMetricProviderData().clear();
		
		// BUGZILLA
		project.getBugTrackingSystems().clear();

		for (String comp : bugzillaComponents) {
			Bugzilla bugzilla = new Bugzilla();
			bugzilla.setUrl(bugzillaUrl);
			bugzilla.setProduct(bugzillaProduct);
			bugzilla.setComponent(comp);
			
			project.getBugTrackingSystems().add(bugzilla);
		}  // FIXME: Removed temporarily for performance reasons
		
		// NNTP
		project.getCommunicationChannels().clear();
		
		NntpNewsGroup nntp = new NntpNewsGroup();
		nntp.setName(nntpName);
		nntp.setUrl(nntpUrl);
		nntp.setAuthenticationRequired(true);
		nntp.setUsername("exquisitus");
		nntp.setPassword("flinder1f7");
		nntp.setPort(80);
		nntp.setLastArticleChecked("-1");
		project.getCommunicationChannels().add(nntp);
	}
	
}
