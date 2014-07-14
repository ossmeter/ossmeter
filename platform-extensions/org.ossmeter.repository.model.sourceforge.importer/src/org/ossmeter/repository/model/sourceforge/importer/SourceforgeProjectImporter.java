package org.ossmeter.repository.model.sourceforge.importer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ossmeter.repository.model.ImportData;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.PersonCollection;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectCollection;
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

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;



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
					url = URL_PROJECT;
					doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
					content = doc.getElementsByClass("projects").first();
					Elements e = content.getElementsByClass("project_info");
					for (int i = startingProject; i < e.size(); i++){
						try {
							url = e.get(i).getElementsByAttributeValue("itemprop", "url").first().attr("href");
							count++;
							System.out.println("--> (" + count + ") " + url);
							SourceForgeProject project = importProject(url.split("/")[2], platform);
							lastImportedProject = new String(j + "/" + i);
							platform.getProjectRepositoryManager().getProjectRepository().getSfImportData().first().setLastImportedProject(lastImportedProject);
							platform.getProjectRepositoryManager().getProjectRepository().sync();
						}
						catch(SocketTimeoutException  st) {
							System.err.println("Single project: Read timed out during the connection to " + url + ". I'll retry later with it.");
							toRetry.add(url);
							continue;
						}
						catch(IOException er) {
							System.err.println("Single project: No further details available for the project " + url );
							continue;
						}
					}
					startingProject=0;
				}
				catch(SocketTimeoutException  st) {
					System.err.println("Page summary: Read timed out during the connection to " + url + ". I'll retry later with it.");
					toRetry.add(url);
//					continue;
				}
				catch(IOException e) {
					System.err.println("Page summary: No further details available for the project " + url );
//					continue;
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
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
					continue;
				} catch (IOException e) {
					System.err.println("No further details available for the project " + url );
	//				continue;
				}
			}
		}
	}
	
	
	
	public SourceForgeProject importProject(String projectId, Platform platform) throws MalformedURLException, IOException {

		Boolean projectToBeUpdated = false;
		
		Role rc = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("sf_developers");
		if(rc==null)
		{
			rc = new Role();
			rc.setName("sf_developers");
			platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(rc);
		}
		
		Role rc2 = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("sf_maintaners");
		if(rc2==null)
		{
			rc2 = new Role();
			rc2.setName("sf_maintaners");
			platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(rc2);
		}
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		Iterable<Project> projects = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
		Iterator<Project> iprojects = projects.iterator();
		SourceForgeProject project = null;
		Project projectTemp = null;
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			if (projectTemp instanceof SourceForgeProject) {
				project = (SourceForgeProject)projectTemp;
				if (project.getShortName().equals(projectId)) {
					projectToBeUpdated = true;
					System.out.println("-----> project " + projectId + " already in the repository. Its metadata will be updated.");
					break;
				}	
			}
		}
		
		if (!projectToBeUpdated)  {
			project = new SourceForgeProject();
		}
		
		InputStream is = new URL("http://sourceforge.net/api/project/name/" + projectId + "/json").openStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));	
		String jsonText = readAll(rd);
		
		JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
		JSONObject currentProg = (JSONObject)((JSONObject)obj.get("Project"));
			
		project.setShortName(projectId); 
		project.setName((String)currentProg.get("name"));
		
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
		
	

		
		// Clear containments to be updated
		project.getOs().clear();
		project.getTopics().clear();
		project.getProgramminLanguages();
		project.getAudiences().clear();
		project.getEnvironments().clear();
		project.getCategories().clear();
		project.getFeatureRequests().clear();
		project.getPatches().clear();
		project.getFeatureRequests().clear();
		project.getBugs().clear();		
		project.getCommunicationChannels().clear();
		project.getVcsRepositories().clear();	

		platform.getProjectRepositoryManager().getProjectRepository().sync();	
		// 
		
		
		// BEGIN Management of Operating Systems
		if ((isNotNull(currentProg,"os"))){			
			JSONArray oss = (JSONArray)currentProg.get("os");
		    OS os = null;
		    for (int i=0; i < oss.size(); i++){					
				os = new OS();
				os.setName(oss.get(i).toString());
				project.getOs().add(os);
			}
	    }
		// END Management of Operating Systems 
		
		// BEGIN Management of Topics
		if ((isNotNull(currentProg,"topics"))){			
			JSONArray topics = (JSONArray)currentProg.get("topics");
			Topic topic = null;
			for (int i=0; i < topics.size(); i++){					
				topic = new Topic();
				topic.setName(topics.get(i).toString());
				project.getTopics().add(topic);
			}
		}
		// END Management of Topics
		
		
		// BEGIN Management of Programming Languages
		if ((isNotNull(currentProg,"programming-languages"))){			
			JSONArray pls = (JSONArray)currentProg.get("programming-languages");
		    ProgrammingLanguage pl = null;
		    for (int i=0; i < pls.size(); i++){					
				pl = new ProgrammingLanguage();
				pl.setName(pls.get(i).toString());
				project.getProgramminLanguages().add(pl);
			}
	    }
		// END Management of Operating Systems 
		
		// BEGIN Management of Audiences
		if ((isNotNull(currentProg,"audiences"))){			
			JSONArray audiences = (JSONArray)currentProg.get("audiences");
		    Audience audience = null;
		    for (int i=0; i < audiences.size(); i++){					
				audience = new Audience();
				audience.setName(audiences.get(i).toString());
				project.getAudiences().add(audience);
			}
		}
		// END Management of Audiences
		
		// BEGIN Management of Environments
		if ((isNotNull(currentProg,"environments"))){			
			JSONArray environments = (JSONArray)currentProg.get("environments");
		    Environment environment = null;
		    for (int i=0; i < environments.size(); i++){					
		    	environment = new Environment();
		    	environment.setName(environments.get(i).toString());
				project.getEnvironments().add(environment);
			}
		}
		// END Management of Environments
		
		// BEGIN Management of Categories	
		if ((isNotNull(currentProg,"categories"))){			
			JSONArray categories = (JSONArray)currentProg.get("categories");
		    Category category = null;
		    for (int i=0; i < categories.size(); i++){					
				category = new Category();
				category.setName(categories.get(i).toString());
				project.getCategories().add(category);
			}
	    }
		// END Management of Categories	
		
		// BEGIN Management of Trackers
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
				}
				// END Management of Trackers
				
		
		// BEGIN Management of CommunicationsChannels
		MailingList ml = new MailingList();
		ml.setUrl((String)currentProg.get("mailing-list"));
		if(ml.getUrl().startsWith("news://")
				|| ml.getUrl().startsWith("git://")
				|| ml.getUrl().startsWith("svn://"))
			ml.setNonProcessable(false);
		else ml.setNonProcessable(true);
		project.getCommunicationChannels().add(ml);
		// END Management of CommunicationsChannels		
		
		// BEGIN Management of VCS
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
		// END Management of VCS
		
		
		// BEGIN Management of Licenses
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
				}
			}
		}
		// END Management of Licenses 

		
		// BEGIN Management of Persons
		if ((isNotNull(currentProg,"maintainers"))){			
			JSONArray maintainers = (JSONArray)currentProg.get("maintainers");
			Iterator<JSONObject> iter  = maintainers.iterator();
		    Person maintainer = null;
			Role role = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("sf_maintaners");
			
			while(iter.hasNext()){					
				JSONObject entry = (JSONObject)iter.next();
				maintainer = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName((String)entry.get("name"));
				
				if ((maintainer != null) & checkRole(maintainer, role) )
					break;
				
				if (maintainer == null) {
						maintainer = new Person();
						maintainer.setName((String)entry.get("name"));
						maintainer.setHomePage((String)entry.get("homepage"));
						maintainer.getRoles().add(role);
						platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(maintainer);
						project.getPersons().add(maintainer);
						break;
				}
			}
		}
		
		if ((isNotNull(currentProg,"developers"))){			
			JSONArray developers = (JSONArray)currentProg.get("developers");
			Iterator<JSONObject> iter  = developers.iterator();
		    Person developer = null;
			Role role = platform.getProjectRepositoryManager().getProjectRepository().getRoles().findOneByName("sf_developers");

		    while(iter.hasNext()){					
				JSONObject entry = (JSONObject)iter.next();
				developer = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName((String)entry.get("name"));
				
				if ((developer != null) & checkRole(developer, role) )
					break;

				if (developer == null) {	
					developer = new Person();
					developer.setName((String)entry.get("name"));
					developer.setHomePage((String)entry.get("homepage"));					
					developer.getRoles().add(role);
					platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(developer);
					project.getPersons().add(developer);
					break;
				}				
			}
		}
		// END Management of Persons
		
		if (!projectToBeUpdated) {
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		}
		
		platform.getProjectRepositoryManager().getProjectRepository().sync();	
		return project;		
		
	}
	
	
	private boolean checkRole(Person p, Role r) {
		boolean hasRole = false;
		Iterator<Role> iroles = null;
		
		try {
			iroles = p.getRoles().iterator();
		} catch (NullPointerException e) {
			return hasRole;
		}
		
		Role role = null;
		while (iroles.hasNext()){
			role = iroles.next(); 
			if (role.getName().equals(r.getName())) {
				hasRole = true;
				break;
			}
		}
		
		return hasRole;
	}

}
