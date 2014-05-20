package org.ossmeter.repository.model.redmine.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Role;


import org.ossmeter.repository.model.redmine.RedmineBugIssueTracker;
import org.ossmeter.repository.model.redmine.RedmineCategory;
import org.ossmeter.repository.model.redmine.RedmineIssue;
import org.ossmeter.repository.model.redmine.RedmineIssuePriority;
import org.ossmeter.repository.model.redmine.RedmineProject;
import org.ossmeter.repository.model.redmine.RedmineProjectVersion;
import org.ossmeter.repository.model.redmine.RedmineUser;
import org.ossmeter.repository.model.redmine.RedmineWiki;

public class RedmineImporter {
	public RedmineProject importProject(String projectId, Platform platform) 
	{
		
		
		RedmineProject result = new RedmineProject();
		org.jsoup.nodes.Document doc;
		String URL_PROJECT = projectId;
		
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			//SET NAME
			Element e = doc.getElementById("header");
			Element name =  e.getElementsByTag("h1").first();
			
			result.setName(name.text());
			
			
			
			//SET WIKI
			if(exisistWiki(projectId + "/wiki"))
			{
				RedmineWiki wiki = new RedmineWiki();
				wiki.setUrl(projectId + "/wiki");
				result.setWiki(wiki);
			}
			//SET PERSON
			List<RedmineUser> gul = getPersonProject(platform, doc);
			for (RedmineUser googleUser : gul) {
				platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(googleUser);
				result.getPersons().add(googleUser);
			}
			result.getPersons().addAll(gul);
			//SET GoogleIssueTracker
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
				
				String s = projectId.substring(0,projectId.lastIndexOf("project")-1) + issue.getElementsByTag("a").first().attr("href") + "?set_filter=1&f%5B%5D=status_id&op%5Bstatus_id%5D=*&page=1";
				git.setUrl(s);
				//This line work fine but there is a google access limit
				List<RedmineIssue> gi = getRedmineIssueList(platform, git.getUrl());
				git.getIssues().addAll(gi);
				result.getBugTrackingSystems().add(git);
			}
			
			List<RedmineProjectVersion> versions = getRedmineProjectVersion(platform, projectId +"/roadmap");
			

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
		return result;
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
				Elements versioni = e.getElementsByTag("h3");
				for (Element element : versioni) {
					RedmineProjectVersion pv = new RedmineProjectVersion();
					pv.setName(element.text());
					//Description version miss
					result.add(pv);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			//Paginazione
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
			//fine paginazione
			
			Element e = doc.getElementsByClass("autoscroll").first().getElementsByClass("issues").first().getElementsByTag("tbody").first();
			Elements tableRows = e.getElementsByTag("tr");
			for (Element iterable_element : tableRows) 
			{
				String urlIssue = url.substring(0,url.lastIndexOf("/projects/")) +
						iterable_element.getElementsByTag("a").first().attr("href");
				RedmineIssue gi = getRedmineIssue(platform, urlIssue);
				result.add(gi);//System.out.println(iterable_element.getElementsByTag("td").get(1).toString());
				break;
			}
//			
			//Paginazione
			if(end<totPagination)
			{
				
				if (url.contains("page="));
				{
					String s = url.substring(0,url.lastIndexOf("="));
					//System.out.println(s);
					int k=currentPage+1;
					getRedmineIssueList(platform, s+"="+(k));
				}
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	
	private List<RedmineUser> getPersonProject(Platform platform,
			org.jsoup.nodes.Document doc) 
	{
		List<RedmineUser> result = new ArrayList<RedmineUser>();
		Element e = doc.getElementsByClass("memebers").first();
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

		return result;
	}
	private Map<String, Role> rolePending = new HashMap<String, Role>();
	private Map<String, Person> userPending = new HashMap<String, Person>();
	private Map<String, Person> categoryPending = new HashMap<String, Person>();
	public void importAll(Platform platform) 
	{
		String URL_PROJECT = "http://demo.redmine.org/projects/demo-ossmeter";
		RedmineProject currentProg = importProject(URL_PROJECT, platform);
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
			
			Element summary = e.getElementsByTag("div").get(2);
			result.setDescription(summary.text());
			
			Elements starsList = e.getElementsByTag("tr");
			e = doc.getElementsByClass("attributes").first();
			Elements el = e.getElementsByTag("tr");
//			for (Element element : el) {
//				System.out.println(element.text());
//			}
			String cat = el.get(4).getElementsByClass("category").get(1).text();
			if (!cat.equals("-"))
			{
				RedmineCategory g = new RedmineCategory();
				g.setName(cat);
				result.setCategory(g);
			}
			
			String priority = el.get(1).getElementsByClass("priority").get(1).text();
			if (!priority.equals("-"))
			{
				switch (priority) {
				case "Normal":
					result.setPriority(RedmineIssuePriority.Normal);
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
			e = doc.getElementById("relations");
			
			
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

}
