package org.ossmeter.repository.model.eclipse.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.Role;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.cc.forum.Forum;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.repository.model.eclipse.Documentation;
import org.ossmeter.repository.model.eclipse.EclipsePlatform;
import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.MailingList;
import org.ossmeter.repository.model.eclipse.Wiki;
import org.ossmeter.repository.model.eclipse.importer.util.XML;
import org.ossmeter.repository.model.eclipse.importer.util.NNTP.NTTPManager;
import org.ossmeter.repository.model.vcs.cvs.CvsRepository;
import org.ossmeter.repository.model.vcs.git.GitRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.w3c.dom.Document;
//import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EclipseProjectImporter {
	
	private Map<EclipseProject,String> pendingParentReferences = new HashMap<EclipseProject,String>();
	private Collection<EclipseProject> importedProjects = new ArrayList<EclipseProject>();
	private ArrayList<String> NNTPUrllist;
	
	private Map<String, Role> rolePending = new HashMap<String, Role>();
	private Map<String, License> licensePending = new HashMap<String, License>();
	private Map<String, Person> userPending = new HashMap<String, Person>();
	
	private EclipseProject getProjectByName(String projectName) {
		for (EclipseProject p : importedProjects) {
	         if (p.getShortName().equals(projectName))
	             return p;
	    }
		return null;	
	}
	
	private Collection<EclipseProject> fixPendingParentReferences(){

		EclipseProject child = null;
	    EclipseProject parent = null;
	    
		Iterator it = pendingParentReferences.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pr = (Map.Entry)it.next();
	        child = (EclipseProject) pr.getKey();
	        parent = getProjectByName((String)pr.getValue());
	        if (parent != null)
	        	child.setParent(parent);
	    }
		
		return importedProjects;
	}
	

	private Collection<EclipseProject> cleanParent()
	{

		EclipseProject child = null;
	    EclipseProject parent = null;
	    
		Iterator it = pendingParentReferences.entrySet().iterator();
		while (it.hasNext()) {
	        Map.Entry pr = (Map.Entry)it.next();
	        
	        child = (EclipseProject) pr.getKey();
	        parent = getProjectByName((String)pr.getValue());
	        
	        if (parent != null)
	        	{
	        		ArrayList<VcsRepository> toBeRemovedFromParentVCS = new ArrayList<VcsRepository>();
	        		for (VcsRepository i : child.getVcsRepositories()) 
	        		{
	        			for (VcsRepository j : parent.getVcsRepositories()) 
		        		{
		        			if (i.getUrl().equals(j.getUrl()))
		        				toBeRemovedFromParentVCS.add(j);
						}
					}
	        		for (VcsRepository vcsRepository : toBeRemovedFromParentVCS) {
						parent.getVcsRepositories().remove(vcsRepository);
					}
	        		ArrayList<CommunicationChannel> toBeRemovedFromParentCC = new ArrayList<CommunicationChannel>();
	        		for (CommunicationChannel i : child.getCommunicationChannels()) 
	        		{
	        			for (CommunicationChannel j : parent.getCommunicationChannels()) 
		        		{
		        			if (i.getUrl().equals(j.getUrl()))
		        				toBeRemovedFromParentCC.add(j);
						}
					}
	        		for (CommunicationChannel vcsRepository : toBeRemovedFromParentCC) {
						parent.getCommunicationChannels().remove(vcsRepository);
					}
	        		ArrayList<BugTrackingSystem> toBeRemovedFromParentBTS = new ArrayList<BugTrackingSystem>();
	        		
	        		for (BugTrackingSystem i : child.getBugTrackingSystems()) 
	        		{
	        			for (BugTrackingSystem j : parent.getBugTrackingSystems()) 
		        		{
		        			if (i.getUrl().equals(j.getUrl()))
		        				//parent.getBugTrackingSystems().remove(j);
		        				toBeRemovedFromParentBTS.add(j);
						}
					}
	        		for (BugTrackingSystem vcsRepository : toBeRemovedFromParentBTS) {
						parent.getBugTrackingSystems().remove(vcsRepository);
					}
	        	}
		}
		
		return importedProjects;
	}
	
	private boolean isNotNull(JSONObject currentProg, String attribute ) 
	{
		if (((JSONArray)currentProg.get(attribute)).isEmpty())
			return false;
		else 
			return true;		
	}
	
	private boolean isNotNullObj(JSONObject currentProg, String attribute ) 
	{
		if (currentProg.get(attribute)!=null)
			return true;
		else 
			return false;		
	}
	
	private static String readAll(Reader rd) throws IOException 
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public void importAll(Platform platform) 
	{		
		try {
			NTTPManager man = new NTTPManager();
			NNTPUrllist = man.GetListNNTPGroups();
			
			System.out.println("Retrieving the list of Eclipse projects...");

			//InputStream is = new FileInputStream(new File("C:\\eclipse.json"));
			InputStream is = new URL("http://projects.eclipse.org/json/projects/all").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);		
	
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			Iterator iter = obj.entrySet().iterator();
			Map.Entry jsonAr =  null;
			if (iter.hasNext())
				jsonAr = (Map.Entry) iter.next(); 
			
			Object o =jsonAr.getValue();
			Iterator iter2 = ((JSONObject)jsonAr.getValue()).entrySet().iterator();
			
			while (iter2.hasNext()) {
				Map.Entry entry = (Map.Entry) iter2.next();
				EclipseProject pi = (EclipseProject)platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName((String) entry.getKey());
				EclipseProject project = importProject((String) entry.getKey(), platform);
				importedProjects.add(project);
				
			}			
			fixPendingParentReferences();
			cleanParent();
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}
	

	public EclipseProject importProject(String projectId, Platform platform) {
			
		String URL_PROJECT = "http://projects.eclipse.org/projects/"+ projectId;
		String html = null;
		XML xml = null;
		
		Project p = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(projectId);
		EclipseProject project = new EclipseProject();
		Boolean projectToBeUpdated = false;
		
		if ((p != null) & (p instanceof EclipseProject)) {
			project = (EclipseProject)p;
			System.out.println("Project " + p.getShortName() + " already in the repository. Its metadata will be updated.");
			projectToBeUpdated = true;
		}
		
		//Retrieving data by parsing the Web page of the project
		//This is necessary to retrieve metadata not available in JSON
		try {
			html = getRawHtml(URL_PROJECT);
			xml = new XML(html);
			Document xmlDoc = xml.getDOM();
			String platformName;
			
			List<String> eclipsePlatformNames = getPlatforms(xmlDoc);
			Iterator it  = (Iterator) eclipsePlatformNames.iterator();
			
			if (projectToBeUpdated) {
				project.getPlatforms().clear();
				platform.getProjectRepositoryManager().getProjectRepository().sync();	
			}
			
			while (it.hasNext()) {
				platformName = (String) it.next();
				EclipsePlatform eclipsePlatform = new EclipsePlatform();
				eclipsePlatform.setName(platformName);
				project.getPlatforms().add(eclipsePlatform);
				platform.getProjectRepositoryManager().getProjectRepository().sync();	
			}
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Retrieving data from the JSON file
		try {
			InputStream is = new URL("http://projects.eclipse.org/json/project/" + projectId).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
						
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONObject currentProg = (JSONObject)((JSONObject)obj.get("projects")).get(projectId);
			
			project.setShortName(projectId);
			if ((isNotNullObj(currentProg,"title")))
				project.setName(currentProg.get("title").toString());
			System.out.println("---> Retrieving metadata of " + project.getShortName());
						
			project.setParagraphUrl(getParagraphUrl(projectId));

			if ((isNotNull(currentProg,"description")))
				project.setDescription(((JSONObject)((JSONArray)currentProg.get("description")).get(0)).get("value").toString());
		
			if ((isNotNull(currentProg,"parent_project"))){
				String parentProjectName = ((JSONObject)((JSONArray)currentProg.get("parent_project")).get(0)).get("id").toString();
				EclipseProject parentProject = (EclipseProject)platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName(parentProjectName);				
				if (parentProject != null) {
					project.setParent(parentProject);
				    System.out.println("The project " + parentProject.getShortName() + " is parent of " + project.getShortName());
				} else {
					pendingParentReferences.put(project,parentProjectName);
				}				    
			}		
			
			if ((isNotNull(currentProg,"download_url")))
					project.setDownloadsUrl(((JSONObject)((JSONArray)currentProg.get("download_url")).get(0)).get("url").toString());

			if ((isNotNull(currentProg,"website_url")))
				project.setHomePage(((JSONObject)((JSONArray)currentProg.get("website_url")).get(0)).get("url").toString());
	
			if ((isNotNull(currentProg,"plan_url")))
				project.setProjectplanUrl(((JSONObject)((JSONArray)currentProg.get("plan_url")).get(0)).get("url").toString());
			
			if ((isNotNull(currentProg,"update_sites")))
				project.setUpdatesiteUrl(((JSONObject)((JSONArray)currentProg.get("update_sites")).get(0)).get("url").toString());

			if ((isNotNull(currentProg,"state")))
				project.setState(((JSONObject)((JSONArray)currentProg.get("state")).get(0)).get("value").toString());		

			
		// BEGIN Management of Communication Channels 		
			if (projectToBeUpdated) {
				project.getCommunicationChannels().clear();
				platform.getProjectRepositoryManager().getProjectRepository().sync();	
			}
				
			if ((isNotNull(currentProg,"documentation_url")))
			{
				JSONArray bugzillaJsonArray = (JSONArray)currentProg.get("documentation_url");
				for (Object object : bugzillaJsonArray) {
					Documentation documentation_url = new Documentation();
					documentation_url.setUrl((String)((JSONObject)object).get("url"));
					documentation_url.setNonProcessable(true);
					project.getCommunicationChannels().add(documentation_url);
				}
			}
								
			if ((isNotNull(currentProg,"wiki_url"))) {			
				JSONArray bugzillaJsonArray = (JSONArray)currentProg.get("wiki_url");
				for (Object object : bugzillaJsonArray) {
					Wiki wiki = new Wiki();
					String sApp = (String)((JSONObject)object).get("url");
					wiki.setUrl(sApp);
					wiki.setNonProcessable(true);
					project.getCommunicationChannels().add(wiki);
				}				
			}

			if ((isNotNull(currentProg,"mailing_lists"))){			
				JSONArray mailingLists = (JSONArray)currentProg.get("mailing_lists");
				Iterator<JSONObject> iter  = mailingLists.iterator();
			    MailingList mailingList = null;
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					mailingList = new MailingList();
					mailingList.setName((String)entry.get("name"));
					mailingList.setUrl((String)entry.get("url"));
					if(mailingList.getUrl().startsWith("news://")
							|| mailingList.getUrl().startsWith("git://")
							|| mailingList.getUrl().startsWith("svn://"))
						mailingList.setNonProcessable(false);
					else mailingList.setNonProcessable(true);
					project.getCommunicationChannels().add(mailingList);
				}
			}

			if ((isNotNull(currentProg,"forums"))){
				JSONArray forums = (JSONArray)currentProg.get("forums");
				Iterator<JSONObject> iter  = forums.iterator();
			    Forum forum = null;
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					forum = new Forum();
					forum.setName((String)entry.get("name"));
					forum.setUrl((String)entry.get("url"));
					if(forum.getUrl().startsWith("news://")
							|| forum.getUrl().startsWith("git://")
							|| forum.getUrl().startsWith("svn://"))
						forum.setNonProcessable(false);
					else forum.setNonProcessable(true);
					forum.setDescription((String)entry.get("description"));
					project.getCommunicationChannels().add(forum);
				}
			}
			
			for (NntpNewsGroup cc : getNntpNewsGroup(projectId)) {
				project.getCommunicationChannels().add(cc);
			}
		// END Management of Communication Channels
						
			
		// BEGIN Management of Bug Tracking Systems
			if (projectToBeUpdated) {
				project.getBugTrackingSystems().clear();
				platform.getProjectRepositoryManager().getProjectRepository().sync();	
			}
			
			if ((isNotNull(currentProg,"bugzilla"))) {
				JSONArray bugzillaJsonArray = (JSONArray)currentProg.get("bugzilla");
				for (Object object : bugzillaJsonArray) {
					Bugzilla bugzilla = new Bugzilla();
					bugzilla.setComponent((String)((JSONObject)object).get("component"));
					bugzilla.setCgiQueryProgram((String)((JSONObject)object).get("query_url"));
					bugzilla.setUrl((String)((JSONObject)object).get("create_url"));
					bugzilla.setComponent((String)((JSONObject)object).get("component"));
					bugzilla.setProduct((String)((JSONObject)object).get("product"));
					project.getBugTrackingSystems().add(bugzilla);
				}				
			}
		// END Management of Bug Tracking Systems
	
		// BEGIN Management of Licenses
			if (projectToBeUpdated) {
				project.getLicenses().clear();
				platform.getProjectRepositoryManager().getProjectRepository().sync();	
			}
	
			if ((isNotNull(currentProg,"licenses"))){			
				JSONArray licenses = (JSONArray)currentProg.get("licenses");
				Iterator<JSONObject> iter  = licenses.iterator();
			    License license = null;
				while(iter.hasNext()){
					JSONObject entry = (JSONObject)iter.next();
					license = platform.getProjectRepositoryManager().getProjectRepository().getLicenses().findOneByName((String)entry.get("name"));
					if (license == null) {
						license = new License();
						license.setName((String)entry.get("name"));
						license.setUrl((String)entry.get("url"));
						platform.getProjectRepositoryManager().getProjectRepository().getLicenses().add(license);
						project.getLicenses().add(license);
					} else {
						license.setUrl((String)entry.get("url"));						
					}
					platform.getProjectRepositoryManager().getProjectRepository().sync();	
				}
			}
		// END Management of Licenses

			
		// BEGIN Management of VcsRepositories
			if (projectToBeUpdated) {
				project.getVcsRepositories().clear();
				platform.getProjectRepositoryManager().getProjectRepository().sync();	
			}
			
			if ((isNotNull(currentProg,"source_repo"))){
				JSONArray source_repo = (JSONArray)currentProg.get("source_repo");
				Iterator<JSONObject> iter  = source_repo.iterator();
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					VcsRepository repository = null;
					if (((String)entry.get("type")).equals("git")) {
						repository = new GitRepository();
					} else if (((String)entry.get("type")).equals("svn")) {
						repository = new SvnRepository();
					} else if (((String)entry.get("type")).equals("cvs")) {
						repository = new CvsRepository();
					}
				if (repository != null) {
					repository.setName((String)entry.get("name"));
					repository.setUrl("http://git.eclipse.org" + (String)entry.get("path"));
				}
				project.getVcsRepositories().add(repository);
				}
			}
		// END Management of VcsRepositories

			
		List<Person> ps = getProjectPersons(platform, projectId);
		for (Person person : ps) {
			project.getPersons().add(person);
		}

			
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		if (!projectToBeUpdated) {
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		}
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();	
		return project;
		
	}

	
	private ArrayList<NntpNewsGroup> getNntpNewsGroup(String projectShortName)
	{
		ArrayList<NntpNewsGroup> result = new ArrayList<NntpNewsGroup>(); 
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		Integer numPagesToBeScanned;
		String url = null;	
		List<String> toRetry = new ArrayList<String>();
		String URL_PROJECT = "https://projects.eclipse.org/projects/"+ projectShortName +"/contact";
		url = URL_PROJECT;
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Elements e = doc.getElementsByClass("forum-nntp");
			for (int i = 0; i < e.size(); i++){
				NntpNewsGroup NNTPuRL = null;
				NNTPuRL = new NntpNewsGroup();
				NNTPuRL.setName(projectShortName);
				NNTPuRL.setUrl(e.get(i).attr("href"));
				if (NNTPuRL.getUrl().startsWith("news://")
						|| NNTPuRL.getUrl().startsWith("news://")
						|| NNTPuRL.getUrl().startsWith("news://"))
					NNTPuRL.setNonProcessable(false);
				else NNTPuRL.setNonProcessable(true);
					
				result.add(NNTPuRL);				
			}
			return result;
		} catch (IOException e1) {
			e1.printStackTrace();
			return new ArrayList<NntpNewsGroup>();
		}	
	}
	
	private List<Person> getProjectPersons(Platform platform,
			String projectId) 
	{
		List<Person> result = new ArrayList<Person>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = "http://projects.eclipse.org/projects/" + projectId + "/who";	
		
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			org.jsoup.nodes.Element e = doc.getElementsByClass("field-name-field-projects-members-members").first().
											getElementsByClass("field-items").first();
			
			Elements divUsers = e.getElementsByClass("field-item");
			for (Element iterable_element : divUsers) 
			{
				String roleName = iterable_element.getElementsByTag("h3").text();
				Role gr = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(roleName);
				if (gr == null)
				{				
					gr = rolePending.get(roleName);
					if(gr==null)
					{
						gr = new Role();
						gr.setName("Eclipse " + roleName);
						rolePending.put(roleName, gr);
						platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(gr);
					}
				}
				roleName = iterable_element.getElementsByTag("h3").first().text();
				
				Elements usersInRole = iterable_element.getElementsByTag("li");
				for (Element element : usersInRole) {
					System.out.println(element.text());
					String username = element.text();
					String url = "http://projects.eclipse.org/" + element.getElementsByAttribute("href").attr("href");
					Person gu = userPending.get(username);
					if (gu==null)
					{
						gu = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(username);			
						if(gu==null)
						{
							gu = new Person();
							gu.setName(username);
							gu.setHomePage(url);
							
							platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(gu);
							
						}
						userPending.put(username, gu);
						
					}
					gu.getRoles().add(gr);
					result.add(gu);
				}
			}
		} catch (NullPointerException e){
			System.err.println("Problems occurred during the collection of the persons in volved in the project " + projectId);			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	private List<String> getPlatforms(Node xml) {
		List<String> result = new ArrayList<>();
		
		String xPathPattern = "//*[@id=\"block-summary-block-summary-block\"]/div/div/div[1]/a";
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();
		try {
			XPathExpression expr = xpath.compile(xPathPattern);
			Object evaluated = expr.evaluate(xml, XPathConstants.NODESET);
			NodeList nodes = (NodeList) evaluated;
			ArrayList<Element> elements = new ArrayList<Element>();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() != Node.ELEMENT_NODE)
					continue;
				result.add(node.getFirstChild().getNodeValue());
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String getCompleteName(Node xml) {
		String result = "";
		
		String xPathPattern = "[@id=\"page-title\"]";
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();
		try {
			XPathExpression expr = xpath.compile(xPathPattern);
			Object evaluated = expr.evaluate(xml, XPathConstants.NODESET);
			result = evaluated.toString();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	private List<String> getPlatforms(JSONObject xml) {
		List<String> result = new ArrayList<>();
		
		String xPathPattern = "//*[@id=\"block-summary-block-summary-block\"]/div/div/div[1]/a";
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();
		try {
			XPathExpression expr = xpath.compile(xPathPattern);
			Object evaluated = expr.evaluate(xml, XPathConstants.NODESET);
			NodeList nodes = (NodeList) evaluated;
			ArrayList<Element> elements = new ArrayList<Element>();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() != Node.ELEMENT_NODE)
					continue;
				result.add(node.getFirstChild().getNodeValue());
			}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


	private String getParagraphUrl(String name) {
		return "http://www.eclipse.org/"+name+"/project-info/project-page-paragraph.html";
	}

	private String getFirstColumnSecondDivHrefInfo(Node xml, String attribute) {
		String result = "";
		String xPathPattern = "//*[@id=\"block-summary-block-summary-block\"]/div/div/div[2]/div/ul/li/a";
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();
		try {
			XPathExpression expr = xpath.compile(xPathPattern);
			Object evaluated = expr.evaluate(xml, XPathConstants.NODESET);
			NodeList nodes = (NodeList) evaluated;
			ArrayList<Element> elements = new ArrayList<Element>();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if (node.getNodeType() != Node.ELEMENT_NODE)
					continue;
				// Home page is the first link
				if (node.getTextContent().equals(attribute)) {
					result = node.getAttributes().getNamedItem("href")
							.getNodeValue();
					break; // No need of finding more
				}
			}

		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Get the html part relative to projects (because the list-of-projects url
	 * is not xhtml->SAX error)
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getRawHtml(String projectURL) throws Exception {
		URL url = new URL(projectURL);
		URLConnection con = url.openConnection();
		BufferedReader rd = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String line;
		StringBuilder sb = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			sb.append(line + "\n");
		}
		rd.close();
		String text = sb.toString();
		return text;
	}

	
}
