package org.ossmeter.repository.model.googlecode.importer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.eclipse.ui.progress.PendingUpdateAdapter;
import org.jsoup.Jsoup;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Role;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.ossmeter.repository.model.googlecode.*;
import org.ossmeter.platform.Platform;

public class GoogleCodeImporter {
	
	
	private Map<String, Role> rolePending = new HashMap<String, Role>();
	private Map<String, Person> userPending = new HashMap<String, Person>();
	
	public GoogleCodeProject importProject(String projectId, Platform platform) 
			throws MalformedURLException, IOException 
	{
		GoogleCodeProject result = new GoogleCodeProject();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		
		String URL_PROJECT = projectId;
		
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element e = doc.getElementById("pname");
			result.setName(e.toString());
			//SET NAME
			Jsoup.parse(e.getElementsByTag("span").toString()).text();
			result.setName(Jsoup.parse(e.getElementsByTag("span").toString()).text());
			e = doc.getElementById("wikicontent");
			result.setDescription(Jsoup.parse(e.toString()).text());
			e = doc.getElementById("mt");
			//SET WIKI
			Elements wikis = e.getElementsContainingText("Wiki");
			Element wiki = null;
			for (Element element : wikis) 
			{
				if(element.text().toString().contains("Wiki"))
					wiki = element;
			}
			if (wiki != null)
			{
				GoogleWiki gw = new GoogleWiki();
				gw.setUrl("https://code.google.com" + wiki.outerHtml().substring(wiki.outerHtml().indexOf("href")+6, wiki.outerHtml().indexOf("class")-2));
				gw.setNonProcessable(true);
				result.setWiki(gw);
				
			}
			//SET PERSON
			List<GoogleUser> gul = getPersonProject(platform, projectId + "people/list");
			for (GoogleUser googleUser : gul) {
				platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(googleUser);
				result.getPersons().add(googleUser);
			}
			result.getPersons().addAll(gul);
			
			//SET GoogleIssueTracker
			Elements issues = e.getElementsContainingText("Issues");
			Element issue = null;
			for (Element element : issues) 
			{
				if(element.text().toString().contains("Issues"))
					issue = element;
			}
			
			if (issue != null)
			{
				GoogleIssueTracker git = new GoogleIssueTracker();
				git.setUrl("https://code.google.com" + issue.outerHtml().substring(issue.outerHtml().indexOf("href")+6, issue.outerHtml().indexOf("class")-2));
				System.out.println(git.getUrl());
				List<GoogleIssue> gi = getGoogleIssueList(platform, git.getUrl());
				git.getIssues().addAll(gi);
				result.setIssueTracker(git);
			}
			
			//SET DOWNLOAD
			Elements dwnldLinks = e.getElementsContainingText("Downloads");
			Element dwnldLink = null;
			String downloadPage = "";
			for (Element element : dwnldLinks) 
			{
				if(element.text().toString().contains("Downloads"))
					dwnldLink = element;
			}
			if (dwnldLink != null)
			{
				downloadPage = "https://code.google.com" + dwnldLink.outerHtml().substring(dwnldLink.outerHtml().indexOf("href")+6, dwnldLink.outerHtml().indexOf("class")-2);
				result.getDownloads().addAll(getGoogleDownload(downloadPage));
			}
			
			
			
			
			//SET CODE LICENSE
			Elements leftColumns = doc.getElementsByClass("pscolumnl");
			Element leftColumn = leftColumns.first();
			Element listLeftColumn = leftColumn.getElementsByTag("ul").first();
			Elements listElement = listLeftColumn.getElementsByTag("li");
			for (int i = 0; i < listElement.size(); i++)
			{
				if (listElement.get(i).text().equals("Code license"))
				{
					i++;
					boolean guard = false;
					while (!guard && i < listElement.size())
					{
						if (!listElement.get(i).attr("class").equals("psgap"))
						{
							String licenseName = listElement.get(i).text();
							License l = platform.getProjectRepositoryManager().getProjectRepository().getLicenses().findOneByName(licenseName);
							if(l==null)
							{
								l = new License();
								Element linkLicense = listElement.get(i).getElementsByTag("a").first();
								l.setUrl(linkLicense.outerHtml().substring(linkLicense.outerHtml().indexOf("href")+6, linkLicense.outerHtml().indexOf("rel")-2));
								l.setName(listElement.get(i).text());
								platform.getProjectRepositoryManager().getProjectRepository().getLicenses().add(l);
							}
							result.getLicenses().add(l);
							
						}
							
						else guard = true;
						i++;
					}
				}
			}
			

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			
			e1.printStackTrace();
		}
		return result;
	}
	
	private List<GoogleIssue> getGoogleIssueList(Platform platform, String url) {
		List<GoogleIssue> result = new ArrayList<GoogleIssue>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = url;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			//Paginazione
			Element pagination = doc.getElementsByClass("pagination").first();
			Integer totPagination = Integer.parseInt(pagination.text().split(" ")[4]);
			Integer numPag = (totPagination % 100) == 0 ? totPagination/100:totPagination/100+1;
			//fine paginazione
			
			Element e = doc.getElementById("resultstable");
			e = e.getElementsByTag("tbody").first();
			Elements tableRows = e.getElementsByTag("tr");
			for (Element iterable_element : tableRows) 
			{
				String urlIssue = url.substring(0,url.length()-4) +
						"detail?id="+ iterable_element.getElementsByTag("td").get(1).getElementsByTag("a").first().text();
				GoogleIssue gi = getGoogleIssue(platform, urlIssue);
				result.add(gi);//System.out.println(iterable_element.getElementsByTag("td").get(1).toString());
				//DOPO UN PO GOOGLE SI INCAZZA
				break;
			}
			
			//Paginazione
			
			//fine paginazione
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	
	private GoogleIssue getGoogleIssue (Platform platform, String url) 
	{
		GoogleIssue result = new GoogleIssue();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = url;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			
			Element e = doc.getElementById("issueheader");
			
			Element summary = e.getElementsByTag("span").first();
			result.setSummary(summary.text());
			Elements starsList = e.getElementsByTag("tr");
			String stars = starsList.get(1).getElementsByTag("td").get(1).text().split(" ")[0];
			if (stars!=null)
			{
				result.setStars(Integer.parseInt(stars));
				
			}
			
			
			result.setCreated_at(doc.getElementById("cursorarea").getElementsByClass("date").first().text());
			e = doc.getElementById("issuemeta");
			Elements menu = e.getElementsByTag("tr");
			String status = menu.get(0).getElementsByTag("td").first().text();
			if (status.equals("Started"))
				result.setStatus(GoogleIssueStatus.Started);
			if (status.equals("New"))
				result.setStatus(GoogleIssueStatus.Started);
			if (status.equals("Accepted"))
				result.setStatus(GoogleIssueStatus.Accepted);
			if (status.equals("Reviewed"))
				result.setStatus(GoogleIssueStatus.Reviewed);
			if (status.equals("Acknowledged"))
				result.setStatus(GoogleIssueStatus.Acknowledged);
			status = menu.get(1).getElementsByTag("td").first().text();
			Person gu = userPending.get(status);
			if (gu==null)
			{
				gu = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(status);			
				if(gu==null)
				{
					gu = new GoogleUser();
					gu.setName(status);
					((GoogleUser)gu).setEmail(status);
				}
				userPending.put(status, gu);
			}
			result.setOwner((GoogleUser) gu);
			for(int i = 2; i < menu.size(); i++ )
			{
				Elements el = menu.get(i).getElementsByTag("td");
				if( el.size() != 0)
					if(el.first().getElementsByAttribute("colspan") != null)
					{
						for (Element element : el.first().getElementsByTag("div")) {
							String string = element.text();
							if(string.startsWith("Type"))
							{	if (string.substring(5).equals("Bug"))
									result.setType(GoogleIssueType.Bug);
								if (string.substring(5).equals("Defect"))
									result.setType(GoogleIssueType.Defect);
								if (string.substring(5).equals("Enhancement"))
									result.setType(GoogleIssueType.Enhancement);
							}
							if(string.startsWith("Pri"))
							{	if (string.substring(4).equals("3") || string.substring(4).equals("Low") )
									result.setPriority(GoogleIssuePriority.Low);
								if (string.substring(4).equals("2") || string.substring(4).equals("Medium"))
									result.setPriority(GoogleIssuePriority.Medium);
								if (string.substring(4).equals("1") || string.substring(4).equals("High"))
									result.setPriority(GoogleIssuePriority.High);
							}
							
						}
					}
			}
			boolean guard = false;
			int i = 0;
			while (!guard)
			{
				Element k = doc.getElementById("hc"+i);
				if (k==null)
					guard=true;
				else
				{
					i++;
					GoogleIssueComment gic = new GoogleIssueComment();
					gic.setText(k.getElementsByTag("pre").first().text());
					gic.setDate(k.getElementsByClass("date").first().text());
					result.getComments().add(gic);
				}
			}
			
			
			
			
			//###DA QUI
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}
	
	private List<GoogleUser> getPersonProject(Platform platform,
			String projectId) 
	{
		List<GoogleUser> result = new ArrayList<GoogleUser>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;	
		String URL_PROJECT = projectId;	
		try 
		{
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element e = doc.getElementById("resultstable");
			e = e.getElementsByTag("tbody").first();
			Elements tableRows = e.getElementsByTag("tr");
			for (Element iterable_element : tableRows) 
			{
				Elements columns = iterable_element.getElementsByTag("td");
				String username = columns.get(0).text();
				Person gu = userPending.get(username);
				if (gu==null)
				{
					gu = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(username);			
					if(gu==null)
					{
						gu = new GoogleUser();
						gu.setName(username);
						((GoogleUser)gu).setEmail(columns.get(0).text());
						platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(gu);
						
					}
					userPending.put(username, gu);
					String [] arrayRole = columns.get(1).text().split("\\+");
					for (String string : arrayRole) 
					{
						Role gr = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName(string);
						if (gr == null)
						{
							gr = rolePending.get(string);
							if(gr==null)
							{
								gr = new Role();
								gr.setName(string);
								rolePending.put(string, gr);
								platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(gr);
							}
						}
						gu.getRoles().add(gr);				
					}
				}
				result.add((GoogleUser) gu);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return result;
	}

	private List<GoogleDownload> getGoogleDownload(String downloadPage)
	{
		List<GoogleDownload> result = new ArrayList<GoogleDownload>();
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		
		String URL_PROJECT = downloadPage;
		
		
			try {
				doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
				Element e = doc.getElementById("resultstable");
				e = e.getElementsByTag("tbody").first();
				Elements tableRows = e.getElementsByTag("tr");
				for (Element iterable_element : tableRows) {
					Elements columns = iterable_element.getElementsByTag("td");
					
					GoogleDownload gd = new GoogleDownload();
					gd.setFileName(columns.get(1).text());
					gd.setUploaded_at(columns.get(3).text());
					gd.setUpdated_at(columns.get(4).text());
					gd.setSize(columns.get(5).text());
					gd.setDownloadCounts(Integer.parseInt(columns.get(6).text()));
					
					result.add(gd);
					
					
				}
				
				
				//result.add(e.toString());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
		
		return result;
	}
	
	public void importAll(Platform platform) 
	{
		String URL_PROJECT = "https://code.google.com/p/gmaps-api-issues/";
		try {
			GoogleCodeProject currentProg = importProject(URL_PROJECT, platform);
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(currentProg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
