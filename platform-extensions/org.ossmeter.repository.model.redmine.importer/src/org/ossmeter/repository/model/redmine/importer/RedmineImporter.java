package org.ossmeter.repository.model.redmine.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectCollection;
import org.ossmeter.repository.model.Role;
import org.ossmeter.repository.model.redmine.RedmineBugIssueTracker;
import org.ossmeter.repository.model.redmine.RedmineCategory;
import org.ossmeter.repository.model.redmine.RedmineIssue;
import org.ossmeter.repository.model.redmine.RedmineIssuePriority;
import org.ossmeter.repository.model.redmine.RedmineIssueStatus;
import org.ossmeter.repository.model.redmine.RedmineProject;
import org.ossmeter.repository.model.redmine.RedmineProjectVersion;
import org.ossmeter.repository.model.redmine.RedmineProjectVersionStatus;
import org.ossmeter.repository.model.redmine.RedmineUser;
import org.ossmeter.repository.model.redmine.RedmineWiki;

public class RedmineImporter {
	private String key;
	private String baseRepo;
	private String user;
	private String password;
	private String essentialRepo;
	protected OssmeterLogger logger;
	
	public RedmineImporter(String baseRepo, String key, String user, String password)
	{
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.redmine");
		logger.addConsoleAppender(OssmeterLogger.DEFAULT_PATTERN);
		this.baseRepo = baseRepo;
		this.essentialRepo = baseRepo.substring(7);
		this.key = key;
		this.user = user;
		this.password = password;
	}

	public RedmineProject importProjectFromHTML(String projectUrl, Platform platform) 
	{
		
		
		org.jsoup.nodes.Document doc;
		String URL_PROJECT = projectUrl;
		
		RedmineProject project = null;
		
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			//SET NAME
			Element e = doc.getElementById("header");
			Element name =  e.getElementsByTag("h1").first();
			Boolean projectToBeUpdated = false;
			Project projectTemp;// = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName(name.text());
			/////////
			Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(e.text());
			Iterator<Project> iprojects = pl.iterator();
			//RedmineProject p = null;
			
			while (iprojects.hasNext()) {
				projectTemp = iprojects.next();
				if (projectTemp instanceof RedmineProject) {
					project = (RedmineProject)projectTemp;
					projectToBeUpdated = true;
					logger.info("-----> project " + e.text() + " already in the repository. Its metadata will be updated.");
					break;
						
				}
			}
			
			
			
			////////
			if (project != null)
			{
				if (project instanceof RedmineProject) 
				{
					project = (RedmineProject)project;
					projectToBeUpdated = true;
					logger.info("-----> project " + projectUrl + " already in the repository. Its metadata will be updated.");	
				}
			}
			if (!projectToBeUpdated)  {
				project = new RedmineProject();
				// Clear containments to be updated	
			}
			else	
			{
				project.getCommunicationChannels().clear();
				project.getVcsRepositories().clear();	
				project.getBugTrackingSystems().clear();
				project.getPersons().clear();
				project.getLicenses().clear();
				project.getVersions();
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}
			project.setName(name.text());
			
			
			
			
			//SET WIKI
			if(exisistWiki(projectUrl + "/wiki"))
			{
				RedmineWiki wiki = new RedmineWiki();
				wiki.setUrl(projectUrl + "/wiki");
				project.setWiki(wiki);
			}
			//SET PERSON
			List<RedmineUser> gul = getPersonProject(platform, doc);
			for (RedmineUser redmineUser : gul) {
				platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(redmineUser);
				project.getPersons().add(redmineUser);
			}
			project.getPersons().addAll(gul);
			//SET RedmineBugIssueTracker
			Element mainManu = e.getElementById("main-menu");
			Elements issues = mainManu.getElementsByTag("li");
			Element issue = null;
			for (Element element : issues) 
			{
				if(element.text().toString().contains("Issues"))
					issue = element;
			}
			
			if (issue != null)
			{
				RedmineBugIssueTracker git = new RedmineBugIssueTracker();
				
				String s = projectUrl.substring(0,projectUrl.lastIndexOf("project")-1) + issue.getElementsByTag("a").first().attr("href") + "?set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=*&page=1";
				git.setUrl(s);
				//This line work fine but there is a google access limit
				List<RedmineIssue> gi = getRedmineIssueList(platform, git.getUrl());
				git.getIssues().addAll(gi);
				project.getBugTrackingSystems().add(git);
			}
			
			project.getVersions().addAll(getRedmineProjectVersion(platform, projectUrl +"/roadmap"));
			//List<RedmineProjectVersion> versions = getRedmineProjectVersion(platform, projectId +"/roadmap");
			

		} catch (IOException e1) {
			logger.error("Error import redmine project from HTML by url "+ projectUrl + " " + e1.getMessage());
		}
		return project;
	}
	
	private List<RedmineProjectVersion> getRedmineProjectVersion(
			Platform platform, String url) {
		
		List<RedmineProjectVersion> result = new ArrayList<RedmineProjectVersion>();
		org.jsoup.nodes.Document doc;	
		String URL_PROJECT = url + "?tracker_ids%5B%5D=1&tracker_ids%5B%5D=2&tracker_ids%5B%5D=3&completed=1";	
		url = URL_PROJECT;
			try {
				doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
				Element e = doc.getElementById("roadmap");
				if (e!=null)
				{
					Elements versioni = e.getElementsByTag("h3");
					for (Element element : versioni) {
						RedmineProjectVersion pv = new RedmineProjectVersion();
						pv.setName(element.text());
						//Description version miss
						result.add(pv);
					}
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error import redmine version from the url" + url + " " + e.getMessage());
			}
		
		
		return result;
	}

	private boolean exisistWiki(String url) {
		// TODO Auto-generated method stub
		org.jsoup.nodes.Document doc;
		
		try {
			doc = Jsoup.connect(url).timeout(10000).get();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	private List<RedmineIssue> getRedmineIssueList(Platform platform, String url) {
		List<RedmineIssue> result = new ArrayList<RedmineIssue>();
		org.jsoup.nodes.Document doc;	
		String URL_PROJECT = url;	
		url = URL_PROJECT;
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			//Pagination
			Element pagination = doc.getElementsByClass("pagination").first().getElementsByClass("items").first();
			
			String totalIssue = pagination.text().split("/")[1];
			totalIssue =totalIssue.substring(0, totalIssue.length()-1);
			String start = pagination.text().split("-")[0].substring(1);
			
			String endString = pagination.text().split("-")[1].substring(0);
			endString = endString.substring(0,endString.indexOf("/"));
			Integer end = Integer.parseInt(endString);
			Integer currentPage = Integer.parseInt(endString)/25;
			Integer totPagination = Integer.parseInt(totalIssue);
			Integer numPag = (25) == 0 ? totPagination/25:totPagination/25+1;
			//end pagination
			
			Element e = doc.getElementsByClass("autoscroll").first().getElementsByClass("issues").first().getElementsByTag("tbody").first();
			Elements tableRows = e.getElementsByTag("tr");
			for (Element iterable_element : tableRows) 
			{
				String urlIssue = url.substring(0,url.lastIndexOf("/projects/")) +
						iterable_element.getElementsByTag("a").first().attr("href");
				RedmineIssue gi = getRedmineIssue(platform, urlIssue);
				result.add(gi);
				break;
			}
			
			//Pagination
			if(end<totPagination)
			{
				
				if (url.contains("page="));
				{
					String s = url.substring(0,url.lastIndexOf("="));
					int k=currentPage+1;
					getRedmineIssueList(platform, s+"="+(k));
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Error import redmine issue list from the url" + url + " " + e1.getMessage());
		}
		return result;
	}
	
	
	private List<RedmineUser> getPersonProject(Platform platform,
			org.jsoup.nodes.Document doc) 
	{
		List<RedmineUser> result = new ArrayList<RedmineUser>();
		Element e = doc.getElementsByClass("memebers").first();
		if (doc.getElementsByClass("splitcontentright").first().getElementsByClass("members").size() != 0)
		{
			String members = doc.getElementsByClass("splitcontentright").first().getElementsByClass("members").first().text().substring(8);
		
			String[] membersArray = members.split(":");
			for (int i = 1; i < membersArray.length; i=i+2  ) 
			{
				String role = "";
				if (i==1)
					role = membersArray[i-1];
				else
					role = membersArray[i-1].substring(membersArray[i-1].lastIndexOf(" "));
				String user = membersArray[i].substring(0,membersArray[i].lastIndexOf(" ")).trim();
				
				Person ru = userPending.get(user);
				if (ru==null)
				{
					ru = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(user);			
					if(ru==null)
					{
						ru = new RedmineUser();
						ru.setName(user);
						platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(ru);
					}
					userPending.put(user, ru);
				}
				Role gr = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(role);
				if (gr == null)
				{
					gr = rolePending.get(role);
					if(gr==null)
					{
						gr = new Role();
						gr.setName(role);
						
						platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(gr);
					}
				}
				rolePending.put(role, gr);
				ru.getRoles().add(gr);
				result.add((RedmineUser) ru);
			}
			if(membersArray.length % 2 == 1  && membersArray.length-2 >0)
			{
				String role = "";
				role = membersArray[membersArray.length-2].substring(membersArray[membersArray.length-2].lastIndexOf(" "));
				
				String user = membersArray[membersArray.length-1];
				
				Person ru = userPending.get(user);
				if (ru==null)
				{
					ru = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(user);			
					if(ru==null)
					{
						ru = new RedmineUser();
						ru.setName(user);
						platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(ru);
						
						
					}
					userPending.put(user, ru);
				}
	
				Role gr = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(role);
				if (gr == null)
				{
					gr = rolePending.get(role);
					if(gr==null)
					{
						gr = new Role();
						gr.setName(role);
						rolePending.put(role, gr);
						platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(gr);
					}
				}
				ru.getRoles().add(gr);	
				result.add((RedmineUser) ru);
			}
		}
		return result;
	}
	private Map<String, Role> rolePending = new HashMap<String, Role>();
	private Map<String, Person> userPending = new HashMap<String, Person>();
	private Map<String, RedmineCategory> categoryPending = new HashMap<String, RedmineCategory>();
	private JSONArray issueArray = new JSONArray();
	
	
	public void importAllFromHTML(Platform platform) 
	{
		String URL_PROJECT = "http://demo.redmine.org/projects/sdk";
		RedmineProject currentProg = importProjectFromHTML(URL_PROJECT, platform);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(currentProg);
		
	}
	
	private RedmineIssue getRedmineIssue (Platform platform, String url) 
	{
		
		RedmineIssue result = new RedmineIssue();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = url;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			
			Element e = doc.getElementsByClass("description").first();
			if (e!=null)
			{
				if (e.getElementsByTag("div").size() >= 2)
				{
					Element summary = e.getElementsByTag("div").get(2);
					result.setDescription(summary.text());
				}
			
			Elements starsList = e.getElementsByTag("tr");
			}
			e = doc.getElementsByClass("attributes").first();
			Elements el = e.getElementsByTag("tr");
//			
			String cat = el.get(4).getElementsByClass("category").get(1).text();
			if (!cat.equals("-"))
			{
				RedmineCategory g = new RedmineCategory();
				g.setName(cat);
				result.setCategory(g);
			}
			
			String priority = el.get(1).getElementsByClass("priority").get(1).text();
			if (!priority.equals("-"))
				result.setPriority(priority);
/*			{
				switch (priority) {
				case "Normal":
					result.setPriority(<RedmineIssuePriority.Normal);
					break;
				case "Low":
					result.setPriority(RedmineIssuePriority.Low);
					break;
				case "High":
					result.setPriority(RedmineIssuePriority.High);
					break;
				case "Urgent":
					result.setPriority(RedmineIssuePriority.High);
					break;
				case "Immediate":
					result.setPriority(RedmineIssuePriority.Immediate);
					break;
				}
			}
*/
			String startDate = el.get(0).getElementsByTag("td").get(1).text();
			if (!startDate.equals("-"))
			{
				result.setStart_date(startDate);
			}
			String due_date = el.get(1).getElementsByTag("td").get(1).text();
			if (!due_date.equals("-"))
			{
				result.setDue_date(due_date);
			}
			e = doc.getElementsByClass("author").first();
			
			
			String author = e.getElementsByTag("a").first().text();
			result.setAuthor((RedmineUser)userPending.get(author));		
			//Set Relation
			
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			logger.error("Error import redmina issue from url "+ url + " " + e1.getMessage());
		}
		return result;
	}
	
	public void importAll(Platform platform ) 
	{
		int offset = 0;
		int total = 0;
		while (offset <= total)
		{ 
			String issuesUrlString = baseRepo + "issues.json?key=" + key + "&offset=" + offset;
			InputStream is2;
			try {
				is2 = new URL(issuesUrlString).openStream();
				BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
				String jsonText2 = readAll(rd2);
				JSONObject obj2 = (JSONObject)JSONValue.parse(jsonText2);
				total = Integer.parseInt(obj2.get("total_count").toString());
				issueArray.addAll((JSONArray)obj2.get("issues"));
				offset += 25;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			}
		}
		
		
		for (Role role : getRoles()) {
			platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(role);
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		offset = 0;
		total = 0;
		getRedmineUsers(platform);
		
		
		while (offset <= total)
		{ 
			InputStream is;
			try {
				is = new URL(baseRepo + "projects.json?key=" + key).openStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
				JSONArray projArray = ((JSONArray)obj.get("projects"));
				for (Object proj : projArray) {
					String shortName = ((JSONObject)proj).get("identifier").toString();
					platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(importProject(shortName, platform));
					platform.getProjectRepositoryManager().getProjectRepository().sync();
				}
				total = Integer.parseInt(obj.get("total_count").toString());
				offset += 25;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			}
		}
	}
	
	public void importProjects(Platform platform, int numberOfProjects ) 
	{
		int offset = 0;
		int total = 0;
		while (offset <= total)
		{ 
			String issuesUrlString = baseRepo + "issues.json?key=" + key + "&offset=" + offset;
			InputStream is2;
			try {
				is2 = new URL(issuesUrlString).openStream();
				BufferedReader rd2 = new BufferedReader(new InputStreamReader(is2, Charset.forName("UTF-8")));
				String jsonText2 = readAll(rd2);
				JSONObject obj2 = (JSONObject)JSONValue.parse(jsonText2);
				total = Integer.parseInt(obj2.get("total_count").toString());
				issueArray.addAll((JSONArray)obj2.get("issues"));
				offset += 25;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			}
		}
		
		
		for (Role role : getRoles()) {
			platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(role);
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		offset = 0;
		total = 0;
		getRedmineUsers(platform);
		
		int iteration = 0;
		while (offset <= total && iteration < numberOfProjects)
		{ 
			InputStream is;
			try {
				is = new URL(baseRepo + "projects.json?key=" + key).openStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				String jsonText = readAll(rd);
				JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
				JSONArray projArray = ((JSONArray)obj.get("projects"));
				for (Object proj : projArray) {
					String shortName = ((JSONObject)proj).get("identifier").toString();
					platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(importProject(shortName, platform));
					platform.getProjectRepositoryManager().getProjectRepository().sync();
					iteration ++;
					if(iteration > numberOfProjects)
						break;
				}
				total = Integer.parseInt(obj.get("total_count").toString());
				offset += 25;
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("Error during import all redmine project " + e.getMessage());
			}
		}
	}
	
	
	public RedmineProject importProject(String projectId, Platform platform) 
	{	
		RedmineProject project = null;
	
		Boolean projectToBeUpdated = false;
		Project projectTemp = null;
		Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
		Iterator<Project> iprojects = pl.iterator();
		
		
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			if (projectTemp instanceof RedmineProject) {
				project = (RedmineProject)projectTemp;
				projectToBeUpdated = true;
				logger.info("-----> project " + projectId + " already in the repository. Its metadata will be updated.");
				break;
					
			}
		}
		
		if (!projectToBeUpdated)  {
			project = new RedmineProject();
		}
		else	
		{
			project.getCommunicationChannels().clear();
			project.getVcsRepositories().clear();	
			project.getBugTrackingSystems().clear();
			project.getPersons().clear();
			project.getLicenses().clear();
			project.getVersions();
			platform.getProjectRepositoryManager().getProjectRepository().sync();
		}
		project.setHomePage(baseRepo + "projects/"+ projectId);
		try {
			InputStream is;
			is = new URL(baseRepo + "projects/"+ projectId +".json?key=" + key).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONObject jsonProj = (JSONObject)obj.get("project");
			project.setShortName(projectId);
			project.setName(jsonProj.get("name").toString());
			project.setDescription(jsonProj.get("description").toString());
			project.setCreated_on(jsonProj.get("created_on").toString());
			project.setUpdated_on(jsonProj.get("updated_on").toString());
			project.setIdentifier(jsonProj.get("id").toString());
			if(exisistWiki( baseRepo + "projects/" + projectId + "/wiki"))
			{
				RedmineWiki wiki = new RedmineWiki();
				wiki.setUrl(baseRepo + "projects/" + projectId + "/wiki");
				wiki.setNonProcessable(true);
				project.setWiki(wiki);
			}			
			project.getPersons().addAll(getPersonProject(project.getIdentifier(), platform));
			RedmineBugIssueTracker bit = new RedmineBugIssueTracker();
			bit.setName("Redmine_" + projectId);
			bit.getIssues().addAll(getIssue(project.getIdentifier(), platform));
			project.getBugTrackingSystems().add(bit);			
			project.getVersions().addAll(getRedmineProjectVersion(project.getIdentifier()));
			if(projectToBeUpdated)
				logger.info("Project " + projectId + " has benn updated");
			else
				logger.info("Project " + projectId + " has benn added");
			return project;
		}
		catch (MalformedURLException e) {
			if(project!=null)
				if(project.getExecutionInformation()!=null)
					project.getExecutionInformation().setInErrorState(true);
			logger.error("Error during import " + projectId + " redmine project ");
			return project;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			if(project!=null)
				if(project.getExecutionInformation()!=null)
					project.getExecutionInformation().setInErrorState(true);
			logger.error("Error during import " + projectId + " redmine project ");
			return project;
		}
		
	}
	
	private List<Person> getPersonProject(String id, Platform platform) {
		ArrayList<Person> result = new ArrayList<Person>();
		
		InputStream is;
		try {
			is = new URL("http://" + user + ":" + password + "@" + essentialRepo + "projects/" + id +  "/memberships.json?key=" + key).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONArray memberships = (JSONArray)obj.get("memberships");
			for (Object version : memberships) {
				JSONObject projId = (JSONObject)((JSONObject)version).get("project");
				
				if((projId.get("id").toString()).equals(id))
				{
					Person us = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(((JSONObject)((JSONObject)version).get("user")).get("name").toString());
					if( us != null && us instanceof RedmineUser)
					{
						JSONArray roleName = (JSONArray)((JSONObject)version).get("roles");
						for (Object object : roleName) {
							Role r = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(((JSONObject)object).get("name").toString());
							if (r != null)
								us.getRoles().add(r);
						}
						
						//us.getRoles().add(e)
						
						result.add(us);
					}
				}
			}
		} catch (MalformedURLException e) {
			logger.error("Error during import person in " + id +" project " + e.getMessage());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error during import person in " + id +" project " + e.getMessage());
		}
		return result;
	}

	private ArrayList<Role> getRoles()
	{
		ArrayList<Role> result = new ArrayList<Role>();
		
		InputStream is;
		try {
			is = new URL(baseRepo + "roles.json?key=" + key).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONArray roles = (JSONArray)obj.get("roles");
			for (Object version : roles) {
				Role role = new Role();
				role.setName(((JSONObject)version).get("name").toString());
				result.add(role);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("Error during import role " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error during import role " + e.getMessage());
		}
		return result;
	}
	
	private ArrayList<RedmineProjectVersion> getRedmineProjectVersion(String projectID) {
		ArrayList<RedmineProjectVersion> result = new ArrayList<RedmineProjectVersion>();
		InputStream is;
		try {
			is = new URL(baseRepo + "projects/"+ projectID +"/versions.json?key=" + key).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONArray versions = (JSONArray)obj.get("versions");
			for (Object version : versions) {
				RedmineProjectVersion rpv = new RedmineProjectVersion();
				rpv.setName(((JSONObject)version).get("name").toString());
				rpv.setDescription(((JSONObject)version).get("description").toString());
				rpv.setUpdated_on(((JSONObject)version).get("updated_on").toString());
				rpv.setCreated_on(((JSONObject)version).get("created_on").toString());
				String vs = ((JSONObject)version).get("status").toString();
				rpv.setStatus(vs);
/*				switch (vs) {
				case "open":
					rpv.setStatus(RedmineProjectVersionStatus.open);	
					break;
				case "locked":
					rpv.setStatus(RedmineProjectVersionStatus.locked);
					break;
				case "closed":
					rpv.setStatus(RedmineProjectVersionStatus.closed);
					break;
				default:
					break;
				}
*/
				result.add(rpv);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("Error during import project version for " + projectID +" " + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error during import project version for " + projectID +" " + e.getMessage());
		}
		
		return result;
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
	
	private List<RedmineIssue> getIssue(String id, Platform platform)
	{
		ArrayList<RedmineIssue> result = new ArrayList<RedmineIssue>();
		for (Object issue : issueArray) 
		{
			String issueProgId = ((JSONObject)((JSONObject)issue).get("project")).get("id").toString();
			if (issueProgId.equals(id))
			{
				RedmineIssue ri = new RedmineIssue();
				ri.setDescription(((JSONObject)issue).get("description").toString());
				String status = ((JSONObject)((JSONObject)issue).get("status")).get("name").toString();
				ri.setStatus(status);
/*				switch (status) {
				case "New":
					ri.setStatus(RedmineIssueStatus.New);
					break;
				case "Closed":
					ri.setStatus(RedmineIssueStatus.Closed);
					break;
				case "Feedback":
					ri.setStatus(RedmineIssueStatus.Feedback);
					break;
				case "In Progress":
					ri.setStatus(RedmineIssueStatus.In_Progress);
					break;
				case "Rejected":
					ri.setStatus(RedmineIssueStatus.Rejected);
					break;
				case "Resolved":
					ri.setStatus(RedmineIssueStatus.Resolved);
					break;
				default:
					break;
				}
*/
				if(((JSONObject)issue).get("start_date")!=null)
					ri.setStart_date(((JSONObject)issue).get("start_date").toString());
				if(((JSONObject)issue).get("due_date")!=null)
					ri.setDue_date(((JSONObject)issue).get("due_date").toString());
				if(((JSONObject)issue).get("update_date")!=null)
					ri.setUpdate_date(((JSONObject)issue).get("update_date").toString());
				if(((JSONObject)issue).get("description")!=null)
					ri.setDescription(((JSONObject)issue).get("description").toString());
				String priority = ((JSONObject)issue).get("priority").toString();
				ri.setPriority(priority);
/*
				switch (priority) {
				case "High":
					ri.setPriority(RedmineIssuePriority.High);
					break;
				case "Immediate":
					ri.setPriority(RedmineIssuePriority.Immediate);
					break;
				case "Low":
					ri.setPriority(RedmineIssuePriority.Low);
					break;
				case "Normal":
					ri.setPriority(RedmineIssuePriority.Normal);
					break;
				case "Urgent":
					ri.setPriority(RedmineIssuePriority.Urgent);
					break;
				default:
					break;
				}
*/				
				JSONObject cat = (JSONObject)((JSONObject)issue).get("category");
				
				if (cat != null)
				{
					RedmineCategory g = new RedmineCategory();
					g.setName(cat.get("name").toString());
					ri.setCategory(g);
				}
				JSONObject author = (JSONObject)((JSONObject)issue).get("author");
				Person p = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(author.get("name").toString());
				if (p!=null && p instanceof RedmineUser)
					ri.setAuthor((RedmineUser)p);
				JSONObject assigned = (JSONObject)((JSONObject)issue).get("assigned_to");
				if (assigned != null)
				{
					Person assignedPerson = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(assigned.get("name").toString());
					if (assignedPerson !=null && assignedPerson instanceof RedmineUser)
						ri.setAssignedTo((RedmineUser)assignedPerson);
				}
				result.add(ri);
				
				
			}
		}
		return result;
	}

	private void getRedmineUsers(Platform platform)
	{
		ArrayList<RedmineUser> result = new ArrayList<RedmineUser>();
		
		InputStream is;
		try {
			is = new URL("http://" + user + ":" + password + "@" + essentialRepo + "users.json?key=" + key).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONArray users = (JSONArray)obj.get("users");
			int offset = 0;
			int total = 0;
			while (offset <= total)
			{ 
				for (Object user : users) {
					String fullname = ((JSONObject)user).get("firstname").toString() + " " + ((JSONObject)user).get("lastname").toString();
					
					RedmineUser ru = (RedmineUser) platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(fullname);
					if(ru==null)
					{
						ru = new RedmineUser();
					}
					ru.setName(((JSONObject)user).get("firstname").toString() + " " +
							((JSONObject)user).get("lastname").toString());
					ru.setLogin(((JSONObject)user).get("login").toString());
					platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(ru);
					platform.getProjectRepositoryManager().getProjectRepository().sync();
					result.add(ru);
				}
				total = Integer.parseInt(obj.get("total_count").toString());
				offset += 25;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error("Error during import users" + e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Error during import users" + e.getMessage());
		}
		
	}
	
}
