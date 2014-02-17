package org.ossmeter.repository.model.sourceforge.importer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ossmeter.repository.model.ImportData;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Role;
import org.ossmeter.repository.model.sourceforge.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;


import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.ossmeter.repository.model.vcs.cvs.CvsRepository;

import org.jsoup.select.*;
import org.jsoup.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.ossmeter.platform.Platform;



public class SourceforgeProjectImporter {
	
	private boolean isNotNull(JSONObject currentProg, String attribute ) 
	{
		if (currentProg.get(attribute)==null)
			return false;
		else 
			return true;		
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
	
	public void importAll(Platform platform) {
		
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		Integer numPagesToBeScanned;
		String url = null;	
		List<String> toRetry = new ArrayList<String>();
		
		Role rc = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("developers");
		if(rc==null)
		{
			rc = new Role();
			rc.setName("developers");
			platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(rc);
		}
		Role rc2 = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("maintaners");
		if(rc2==null)
		{
			rc2 = new Role();
			rc2.setName("maintaners");
			platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(rc2);
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();

		
		try {
			doc = Jsoup.connect("http://sourceforge.net/directory/").get();
			content = doc.getElementById("result_count");
			numPagesToBeScanned = new Integer(content.toString().substring(("<p id=\"result_count\"> Showing page 1 of ".length()), content.toString().length()-6));
			int count = 0;

			String lastImportedProject = null;
			int startingPage = 1;
			int startingProject = 0;
			
			if (platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().size() != 0) {
				lastImportedProject = new String(platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().first().getLastImportedProject());
				startingPage = new Integer((lastImportedProject.split("/"))[0]);
				startingProject = new Integer((lastImportedProject.split("/"))[1]);
			} else {
				ImportData id = new ImportData();
				id.setLastImportedProject(new String());
				platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().add(id);	
				platform.getProjectRepositoryManager().getProjectRepository().sync();
			}

			
			
			for (int j = startingPage; j < (numPagesToBeScanned); j++)
			{
				System.out.println("Scanning the projects directory page " + j + " of " + numPagesToBeScanned);
				try {
					String URL_PROJECT = "http://sourceforge.net/directory/?page="+j;
					doc = Jsoup.connect(URL_PROJECT).get();
					content = doc.getElementsByClass("projects").first();
					Elements e = content.getElementsByClass("project_info");
					for (int i = startingProject; i < e.size(); i++){
						url = e.get(i).getElementsByAttributeValue("itemprop", "url").first().attr("href");
						count++;
						System.out.println("--> (" + count + ") " + url);
						SourceForgeProject project = importProject(url.split("/")[2], platform);
						platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
						lastImportedProject = new String(j + "/" + i);
						platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().first().setLastImportedProject(lastImportedProject);
						platform.getProjectRepositoryManager().getProjectRepository().sync();
					}
					startingProject=0;
				}
				catch(SocketTimeoutException  st) {
					System.err.println("Read timed out during the connection to " + url + ". I'll retry later with it.");
					toRetry.add(url);
					continue;
				}
				catch(IOException e) {
					System.err.println("No further details available for the project " + url );
					continue;
				}
			}
			
		} 
		catch(SocketTimeoutException st) {
			System.err.println("Read timed out during the connection to the projects directory, please try again.");
		}
		catch(Exception e){
				e.printStackTrace();
		}
		if (! toRetry.isEmpty()) {
			System.out.println("Trying again with...");
			Iterator<String> it = toRetry.iterator();
			String el;
			while (it.hasNext()) {
				el = (String) it.next();
				System.out.println(el);
				SourceForgeProject project = null;
				try {
					if ((platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByName(el.split("/")[2])) != null) {
						project = importProject(el.split("/")[2], platform);
						platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.err.println("No further details available for the project " + url );
					continue;
				}
			}
		}
	}
	
	public SourceForgeProject importProject(String projectId, Platform platform) throws MalformedURLException, IOException {
		SourceForgeProject project = new SourceForgeProject();
		
			InputStream is = new URL("http://sourceforge.net/api/project/name/" + projectId + "/json").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
		      		
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONObject currentProg = (JSONObject)((JSONObject)obj.get("Project"));
				
			project.setShortName(projectId); 
			project.setName(projectId);
			project.setCreated((String)currentProg.get("created"));
			String app = currentProg.get("id").toString();
			
			project.setProjectId(Integer.parseInt(app));
			app = currentProg.get("private").toString();
			project.set_private(Integer.parseInt(app));
			project.setShortDesc((String)currentProg.get("shortdesc"));
			app = currentProg.get("percentile").toString();
			project.setPercentile(Float.parseFloat(app));
			app = currentProg.get("ranking").toString();
			project.setRanking(Integer.parseInt(app));
			project.setDownloadPage((String)currentProg.get("download-page"));
			project.setSupportPage((String)currentProg.get("support-page"));
			project.setSummaryPage((String)currentProg.get("summary-page"));
			project.setHomePage((String)currentProg.get("homepage"));
			
			MailingList ml = new MailingList();
			ml.setUrl((String)currentProg.get("mailing-list"));
			project.getCommunicationChannels().add(ml);
			
			project.setName((String)currentProg.get("name"));
			
			if (isNotNull(currentProg,"SVNRepository"))
			{
				SvnRepository repository = new SvnRepository();
				repository.setBrowse(((String)((JSONObject)currentProg.get("SVNRepository")).get("browse")));
				repository.setUrl(((String)((JSONObject)currentProg.get("SVNRepository")).get("location")));
				project.getVcsRepositories().add(repository);
			}
			
			if (isNotNull(currentProg,"CVSRepository"))
			{
				CvsRepository repository = new CvsRepository();
				repository.setBrowse(((String)((JSONObject)currentProg.get("CVSRepository")).get("browse")));
				repository.setUrl(((String)((JSONObject)currentProg.get("CVSRepository")).get("anon-root")));
				project.getVcsRepositories().add(repository);
			}
					
			if ((isNotNull(currentProg,"licenses"))){			
				JSONArray licenses = (JSONArray)currentProg.get("licenses");
				Iterator<JSONObject> iter  = licenses.iterator();
			    License license = null;
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					license = new License();
					license.setName((String)entry.get("name"));
					license.setUrl((String)entry.get("url"));
					project.getLicenses().add(license);
				}
			}
			if ((isNotNull(currentProg,"topics"))){			
				JSONArray topics = (JSONArray)currentProg.get("topics");
				Topic topic = null;
				for (int i=0; i < topics.size(); i++){					
					
					topic = new Topic();
					topic.setName(topics.get(i).toString());
					
					project.getTopics().add(topic);
				}
			}
			if ((isNotNull(currentProg,"audiences"))){			
				JSONArray audiences = (JSONArray)currentProg.get("audiences");
			    Topic audience = null;
			    for (int i=0; i < audiences.size(); i++){					
					audience = new Topic();
					audience.setName(audiences.get(i).toString());
					project.getTopics().add(audience);
				}
			}
			if ((isNotNull(currentProg,"trackers"))){			
				JSONArray trackers = (JSONArray)currentProg.get("trackers");
				Iterator<JSONObject> iter  = trackers.iterator();
			    
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					String type = (String)entry.get("name");
					if (type == "Feature Requests")
					{
						FeatureRequest tracker = new FeatureRequest() ;
						tracker.setName((String)entry.get("name"));
						tracker.setLocation((String)entry.get("location"));
						project.getFeatureRequests().add(tracker);
					}
					if (type == "Patches")
					{
						Patch tracker = new Patch() ;
						tracker.setName((String)entry.get("name"));
						tracker.setLocation((String)entry.get("location"));
						project.getPatches().add(tracker);
					}
					if (type == "Support Requests")
					{
						FeatureRequest tracker = new FeatureRequest() ;
						tracker.setName((String)entry.get("name"));
						tracker.setLocation((String)entry.get("location"));
						project.getFeatureRequests().add(tracker);
					}
					if (type == "Bugs")
					{
						Bug tracker = new Bug() ;
						tracker.setName((String)entry.get("name"));
						tracker.setLocation((String)entry.get("location"));
						project.getBugs().add(tracker);
					}
				}
				if ((isNotNull(currentProg,"categories"))){			
					JSONArray categories = (JSONArray)currentProg.get("categories");
				    Category category = null;
				    for (int i=0; i < categories.size(); i++){					
						category = new Category();
						category.setName(categories.get(i).toString());
						project.getCategories().add(category);
					}
			    }
			}
			if ((isNotNull(currentProg,"maintainers"))){			
				JSONArray maintainers = (JSONArray)currentProg.get("maintainers");
				Iterator<JSONObject> iter  = maintainers.iterator();
			    Person maintainer = null;
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					maintainer = new Person();
					maintainer.setName((String)entry.get("name"));
					maintainer.setHomePage((String)entry.get("homepage"));
					Role rc = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("maintaners");
					maintainer.getRoles().add(rc);
					
					
					project.getPersons().add(maintainer);
				}
			}
			if ((isNotNull(currentProg,"developers"))){			
				JSONArray developers = (JSONArray)currentProg.get("developers");
				Iterator<JSONObject> iter  = developers.iterator();
			    Person developer = null;
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					developer = new Person();
					developer.setName((String)entry.get("name"));
					developer.setHomePage((String)entry.get("homepage"));					
					Role rc = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("developers");
					developer.getRoles().add(rc);
					
					project.getPersons().add(developer);
				}
			}
			
		
		
		return project;	
		
	}
	

}
