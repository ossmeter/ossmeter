/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/

package org.ossmeter.repository.model.eclipse.importer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.Company;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.Role;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.cc.forum.Forum;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.repository.model.cc.wiki.Wiki;
import org.ossmeter.repository.model.eclipse.Documentation;
import org.ossmeter.repository.model.eclipse.EclipsePlatform;
import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.MailingList;
import org.ossmeter.repository.model.eclipse.importer.util.XML;
import org.ossmeter.repository.model.vcs.cvs.CvsRepository;
import org.ossmeter.repository.model.vcs.git.GitRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.ossmeter.repository.model.importer.exception.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.ossmeter.repository.model.importer.IImporter;

public class EclipseProjectImporter implements IImporter{
	protected OssmeterLogger logger;
	public EclipseProjectImporter(){
		logger = (OssmeterLogger) OssmeterLogger.getLogger("importer.eclipse ");
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
	/**
	 * Import all eclipse project present in the on-line repository.
	 *
	 * @param  platform Ossmeter Platform object
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void importAll(Platform platform) 
	{		
		try {
			logger.info("Retrieving the list of Eclipse projects...");

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
				try {
					EclipseProject project = importProjectFromImportAll((String) entry.getKey(), platform);
				} catch (ProjectUnknownException e) {
					e.printStackTrace();
				}
			}			
		} 
		catch (IOException e1) {
			logger.error("EclipseProject error: Unable to retrive eclipse project's list");
		} 
		logger.info("Importer has finished!");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void importProjects(Platform platform, int numberfOfProjects) 
	{		
		try {

			logger.info("Retrieving the list of Eclipse projects...");
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
			int MAX_ITERATION = 0;
			while (iter2.hasNext() &&
					 MAX_ITERATION <= numberfOfProjects) 
			{
				Map.Entry entry = (Map.Entry) iter2.next();
				EclipseProject pi = (EclipseProject)platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName((String) entry.getKey());
				try {
					importProjectFromImportAll((String) entry.getKey(), platform);
				} catch (ProjectUnknownException e) {
					e.printStackTrace();
				}
				MAX_ITERATION++;
			}
		} 
		catch (IOException e1) {
			logger.error("EclipseProject error: Unable to retrive eclipse project's list");
		} 
		logger.info("Importer has finished!");
	}
	
	@Override
	public EclipseProject importProject(String projectId, Platform platform) throws ProjectUnknownException{
			
		
		String URL_PROJECT = "http://projects.eclipse.org/projects/"+ projectId.replaceAll("-", "\\.");
		String html = null;
		XML xml = null;
		
		Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
		Iterator<Project> iprojects = pl.iterator();
		
		Project projectTemp = null;
		EclipseProject project = new EclipseProject();
		Boolean projectToBeUpdated = false;
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			if (projectTemp instanceof EclipseProject) {
				project = (EclipseProject)projectTemp;
				projectToBeUpdated = true;
				logger.info("-----> project " + projectId + " already in the repository. Its metadata will be updated.");
				break;
					
			}
		}
		
		//Retrieving data by parsing the Web page of the project
		//This is necessary to retrieve metadata not available in JSON
		try {
			html = getRawHtml(URL_PROJECT);
			xml = new XML(html);
			Document xmlDoc = xml.getDOM();
			List<String> eclipsePlatformNames = getPlatforms(xmlDoc, projectId.replaceAll("-", "\\."));
			String platformName;
			
			
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
			URL projectUrl = new URL("http://projects.eclipse.org/json/project/" + projectId.replaceAll("-", "\\."));
			URLConnection conn = projectUrl.openConnection();
			String sorry = conn.getHeaderField("STATUS");
			
			if (sorry !=null && sorry.startsWith("404"))
				throw new WrongUrlException();
			
			InputStream is = conn.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
						
			
			JSONObject obj2=(JSONObject)JSONValue.parse(jsonText);
			JSONObject currentProg = (JSONObject)((JSONObject)obj2.get("projects")).get(projectId.replaceAll("-", "\\."));
			
			project.setShortName(projectId.replaceAll("\\.", "-"));
			if ((isNotNullObj(currentProg,"title")))
				project.setName(currentProg.get("title").toString());
			logger.info("---> Retrieving metadata of " + project.getShortName());
						
			project.setParagraphUrl(getParagraphUrl(projectId.replaceAll("-", "\\.")));

			if ((isNotNull(currentProg,"description")))
				project.setDescription(((JSONObject)((JSONArray)currentProg.get("description")).get(0)).get("value").toString());
		
			if ((isNotNull(currentProg,"parent_project"))){
				String parentProjectName = ((JSONObject)((JSONArray)currentProg.get("parent_project")).get(0)).get("id").toString();
				if (parentProjectName != null) {
					project.setParent(importProjectFromImportAll(parentProjectName, platform));
					logger.info("The project " + parentProjectName + " is parent of " + project.getShortName());
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
							|| forum.getUrl().startsWith("git:	//")
							|| forum.getUrl().startsWith("svn://"))
						forum.setNonProcessable(false);
					else forum.setNonProcessable(true);
					forum.setDescription((String)entry.get("description"));
					project.getCommunicationChannels().add(forum);
				}
			}
			
			for (NntpNewsGroup cc : getNntpNewsGroup(projectId.replaceAll("-", "\\."))) {
				project.getCommunicationChannels().add(cc);
			}
			
			for (Company cc : getCompany(projectId.replaceAll("-", "\\."), platform)) {
				project.getCompanies().add(cc);
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
					bugzilla.setUrl("https://bugs.eclipse.org/bugs/xmlrpc.cgi");//(String)((JSONObject)object).get("create_url"));
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
					if (((String)entry.get("type")).equals("git")  || ((String)entry.get("type")).equals("github")) {
						repository = new GitRepository();
					} else if (((String)entry.get("type")).equals("svn")) {
						repository = new SvnRepository();
					} else if (((String)entry.get("type")).equals("cvs")) {
						repository = new CvsRepository();
					}
				if (repository != null) {
					repository.setName((String)entry.get("name"));
					if (!((String)entry.get("path")).startsWith("/") && ((String)entry.get("type")).equals("github"))
						repository.setUrl((String)entry.get("path"));
					if (((String)entry.get("path")).startsWith("/") && ((String)entry.get("type")).equals("git"))
						repository.setUrl("http://git.eclipse.org" + (String)entry.get("path"));
					if (((String)entry.get("path")).startsWith("/") && ((String)entry.get("type")).equals("svn"))
						repository.setUrl("http://dev.eclipse.org/" + (String)entry.get("path"));
				}
				project.getVcsRepositories().add(repository);
				}
			}
			// END Management of VcsRepositories
			
			List<Person> ps = getProjectPersons(platform, projectId.replaceAll("-", "\\."));
			for (Person person : ps) {
				project.getPersons().add(person);
			}
			if (!projectToBeUpdated) {
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Unable to import " + projectId + "project.");
			return null;
		} catch (Exception e) {
			logger.error("Unable to import " + projectId + "project.");
			return null;
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();	
		logger.info("Project " + projectId + " has been correctly parsed");
		return project;
		
	}
	
	private EclipseProject importProjectFromImportAll(String projectId, Platform platform) throws ProjectUnknownException, MalformedURLException, IOException{
		
		String URL_PROJECT = "http://projects.eclipse.org/projects/"+ projectId.replaceAll("-", "\\.");
		String html = null;
		XML xml = null;
		
		Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId.replaceAll("\\.", "-"));
		Iterator<Project> iprojects = pl.iterator();
		
		Project projectTemp = null;
		EclipseProject project = new EclipseProject();
		Boolean projectToBeUpdated = false;
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			if (projectTemp instanceof EclipseProject) {
				project = (EclipseProject)projectTemp;
				projectToBeUpdated = true;
				logger.info("-----> project " + projectId + " already in the repository. Its metadata will be updated.");
				break;
					
			}
		}
		
		//Retrieving data by parsing the Web page of the project
		//This is necessary to retrieve metadata not available in JSON
		try {
			html = getRawHtml(URL_PROJECT);
			xml = new XML(html);
			Document xmlDoc = xml.getDOM();
			String platformName;
			
			List<String> eclipsePlatformNames = getPlatforms(xmlDoc, projectId);
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
			
			InputStream is = new URL("http://projects.eclipse.org/json/project/" + projectId.replaceAll("-", "\\.")).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
						
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			JSONObject currentProg = (JSONObject)((JSONObject)obj.get("projects")).get(projectId.replaceAll("-", "\\."));
			
			project.setShortName(projectId.replaceAll("\\.", "-"));
			if ((isNotNullObj(currentProg,"title")))
				project.setName(currentProg.get("title").toString());
			logger.info("---> Retrieving metadata of " + project.getShortName());
						
			project.setParagraphUrl(getParagraphUrl(projectId.replaceAll("-", "\\.")));

			if ((isNotNull(currentProg,"description")))
				project.setDescription(((JSONObject)((JSONArray)currentProg.get("description")).get(0)).get("value").toString());
		
			if ((isNotNull(currentProg,"parent_project"))){
				String parentProjectName = ((JSONObject)((JSONArray)currentProg.get("parent_project")).get(0)).get("id").toString();
				if (parentProjectName != null) {
					project.setParent(importProjectFromImportAll(parentProjectName, platform));
					logger.info("The project " + parentProjectName + " is parent of " + project.getShortName());
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
							|| forum.getUrl().startsWith("git:	//")
							|| forum.getUrl().startsWith("svn://"))
						forum.setNonProcessable(false);
					else forum.setNonProcessable(true);
					forum.setDescription((String)entry.get("description"));
					project.getCommunicationChannels().add(forum);
				}
			}
			
			for (NntpNewsGroup cc : getNntpNewsGroup(projectId.replaceAll("-", "\\."))) {
				project.getCommunicationChannels().add(cc);
			}
			
			for (Company cc : getCompany(projectId.replaceAll("-", "\\."), platform)) {
				project.getCompanies().add(cc);
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
					bugzilla.setUrl("https://bugs.eclipse.org/bugs/xmlrpc.cgi");//(String)((JSONObject)object).get("create_url"));
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
					if (((String)entry.get("type")).equals("git")  || ((String)entry.get("type")).equals("github")) {
						repository = new GitRepository();
					} else if (((String)entry.get("type")).equals("svn")) {
						repository = new SvnRepository();
					} else if (((String)entry.get("type")).equals("cvs")) {
						repository = new CvsRepository();
					}
				if (repository != null) {
					repository.setName((String)entry.get("name"));
					if (!((String)entry.get("path")).startsWith("/") && ((String)entry.get("type")).equals("github"))
						repository.setUrl((String)entry.get("path"));
					if (((String)entry.get("path")).startsWith("/") && ((String)entry.get("type")).equals("git"))
						repository.setUrl("http://git.eclipse.org" + (String)entry.get("path"));
					if (((String)entry.get("path")).startsWith("/") && ((String)entry.get("type")).equals("svn"))
						repository.setUrl("http://dev.eclipse.org/" + (String)entry.get("path"));
				}
				project.getVcsRepositories().add(repository);
				}
			}
			// END Management of VcsRepositories
			
			List<Person> ps = getProjectPersons(platform, projectId.replaceAll("-", "\\."));
			for (Person person : ps) {
				project.getPersons().add(person);
			}
			if (!projectToBeUpdated) {
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			logger.error("Unable to import " + projectId + "project.");
			return null;
		} catch (Exception e) {
			logger.error("Unable to import " + projectId + "project.");
			return null;
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();	
		logger.info("Project " + projectId + " has been correctly parsed");
		return project;
		
	}
	
	
	private ArrayList<Company> getCompany(String projectShortName, Platform platform)
	{
		ArrayList<Company> result = new ArrayList<Company>(); 
		org.jsoup.nodes.Document doc;
		org.jsoup.nodes.Element content;
		Integer numPagesToBeScanned;
		String url = null;	
		List<String> toRetry = new ArrayList<String>();
		String URL_PROJECT = "https://projects.eclipse.org/projects/"+ projectShortName +"/who";
		url = URL_PROJECT;
		try {
			doc = Jsoup.connect(URL_PROJECT).timeout(10000).get();
			Element first = doc.getElementById("block-system-main").
						getElementsByClass("field-name-field-active-member-companies").
							first().getElementsByClass("project-active-members").first();
			if(first != null)
			{
				Elements e = first.getElementsByTag("li");
				for (int i = 0; i < e.size(); i++){
					String companyUrl = e.get(i).getElementsByTag("a").attr("href");
					Company company = platform.getProjectRepositoryManager().getProjectRepository().getCompanies().findOneByName(companyUrl);
					if (company == null)
					{
						company = new Company();
						company.setName(companyUrl);
						company.setUrl(companyUrl);
						platform.getProjectRepositoryManager().getProjectRepository().getCompanies().add(company);
						platform.getProjectRepositoryManager().getProjectRepository().sync();
					}
					
					result.add(company);				
				}
			}
			return result;
		} catch (IOException e1) {
			logger.error(projectShortName + " importer failed to load NNTP news group url");
			return new ArrayList<Company>();
		} catch (Exception e) {
			logger.info("The project " + projectShortName +" does not have a company");
		}
		return result;
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
				//NNTPuRL.setUrl(e.get(i).attr("href"));
				String tempUrl = e.get(i).attr("href");
				if (tempUrl.startsWith("news://"))
				{
					if(tempUrl.startsWith("news://news.eclipse.org"))
					{
						NNTPuRL.setUsername("exquisitus");
						NNTPuRL.setPassword("flinder1f7");
						NNTPuRL.setPort(119);
						
						NNTPuRL.setNewsGroupName(tempUrl.substring(24));
						NNTPuRL.setUrl("news.eclipse.org/");
						NNTPuRL.setAuthenticationRequired(true);
					}
					else
					{
						
						if (tempUrl.contains("/"))
						{
							NNTPuRL.setUrl(tempUrl.substring(0, tempUrl.lastIndexOf("/")+1));
							NNTPuRL.setNewsGroupName(tempUrl.substring(tempUrl.lastIndexOf("/")+1));
						}
						else
							NNTPuRL.setUrl(tempUrl);
					}
					
					
					NNTPuRL.setNonProcessable(false);
				}	
				else 
				{
					NNTPuRL.setUrl(tempUrl);
					NNTPuRL.setNonProcessable(true);
				}
					
				result.add(NNTPuRL);				
			}
			return result;
		} catch (IOException e1) {
			logger.error(projectShortName + " importer failed to load NNTP news group url");
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
					gr = new Role();
					gr.setName("Eclipse " + roleName);
					platform.getProjectRepositoryManager().getProjectRepository().getRoles().add(gr);
					platform.getProjectRepositoryManager().getProjectRepository().sync();
				}
				roleName = iterable_element.getElementsByTag("h3").first().text();
				
				Elements usersInRole = iterable_element.getElementsByTag("li");
				for (Element element : usersInRole) {
					String username = element.text();
					String url = "http://projects.eclipse.org/" + element.getElementsByAttribute("href").attr("href");
					Person gu = platform.getProjectRepositoryManager().getProjectRepository().getPersons().findOneByName(username);			
					if(gu==null)
					{
						gu = new Person();
						gu.setName(username);
						gu.setHomePage(url);
						platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(gu);
						platform.getProjectRepositoryManager().getProjectRepository().sync();
					}
					gu.getRoles().add(gr);
					result.add(gu);
				}
			}
		} catch (NullPointerException e){
						
		} catch (IOException e1) {
			logger.error("Problems occurred during the collection of the persons involved in the project " + projectId);
		}
		return result;
	}

	private List<String> getPlatforms(Node xml, String projectId) {
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
			logger.error("Unable to import Eclipse project's platform for " + projectId);
		}
		return result;
	}



	private String getParagraphUrl(String name) {
		return "http://www.eclipse.org/"+name+"/project-info/project-page-paragraph.html";
	}


	/**
	 * Get the html part relative to projects (because the list-of-projects url
	 * is not xhtml->SAX error)
	 * 
	 * @return
	 * @throws WrongUrlException 
	 * @throws Exception
	 */
	private String getRawHtml(String projectURL) throws WrongUrlException  {
		URL url;
		try {
			url = new URL(projectURL);
		
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
		} catch (IOException e) {
			throw new WrongUrlException();
		}
	}

	private String getProjectIdFromUrl(String url) throws WrongUrlException
	{
		
		url = url.replace("http://", "");
		url = url.replace("https://", "");
		url = url.replace("www.", "");
		WrongUrlException q;
		if (url.startsWith("projects.eclipse.org") ){//|| url.startsWith("eclipse.org")) {
			
			url = url.replace("projects/", "");
			url = url.replace("projects.", "");
			url = url.replace("eclipse.org/", "");
			if (url.contains("?")) {
				url = url.substring(0, url.indexOf("?"));
			}
			if (url.contains("/")) {
				url = url.substring(0, url.indexOf("/"));
			}
			return url;
		} 
		else 
			throw new WrongUrlException();
	}
	@Override
	public EclipseProject importProjectByUrl(String url, Platform platform) throws WrongUrlException, ProjectUnknownException
	{
		return importProject(getProjectIdFromUrl(url), platform);
	}
	@Override
	public boolean isProjectInDBByUrl(String url, Platform platform) throws WrongUrlException, ProjectUnknownException, MalformedURLException, IOException
	{
		return isProjectInDB(getProjectIdFromUrl(url),platform);
	}
	private boolean isValidProjectId(String projectId) 
	{
		boolean result = false;
		//InputStream is = new FileInputStream(new File("C:\\eclipse.json"));
		InputStream is;
		try {
			is = new URL("http://projects.eclipse.org/json/projects/all").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);		
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);
			Iterator iter = obj.entrySet().iterator();
			Map.Entry jsonAr =  null;
			if (iter.hasNext())
				jsonAr = (Map.Entry) iter.next(); 
			Object o =jsonAr.getValue();
			Iterator iter2 = ((JSONObject)jsonAr.getValue()).entrySet().iterator();
			while (iter2.hasNext()) 
			{
				Map.Entry entry = (Map.Entry) iter2.next();
				if (projectId.equals((String) entry.getKey()))
					return true;
			}
			return result;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	@Override
	public boolean isProjectInDB(String projectId, Platform platform) throws ProjectUnknownException, MalformedURLException, IOException
	{
		Iterable<Project> pl = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findByShortName(projectId);
		Iterator<Project> iprojects = pl.iterator();
		Project projectTemp = null;
		EclipseProject project = new EclipseProject();
		Boolean projectToBeUpdated = false;
		while (iprojects.hasNext()) {
			projectTemp = iprojects.next();
			if (projectTemp instanceof EclipseProject) {
				return true;
			}
		}
		return false;
	}
	
//#######OLD METHODS#####################
//	private String getCompleteName(Node xml) {
//		String result = "";
//		
//		String xPathPattern = "[@id=\"page-title\"]";
//		XPathFactory xFactory = XPathFactory.newInstance();
//		XPath xpath = xFactory.newXPath();
//		try {
//			XPathExpression expr = xpath.compile(xPathPattern);
//			Object evaluated = expr.evaluate(xml, XPathConstants.NODESET);
//			result = evaluated.toString();
//		} catch (XPathExpressionException e) {
//			logger.error("Unable to import eclilpse project's  complete name for ");
//		}
//		return result;
//	}
//	
//	private List<String> getPlatforms(JSONObject xml) {
//		List<String> result = new ArrayList<>();
//		
//		String xPathPattern = "//*[@id=\"block-summary-block-summary-block\"]/div/div/div[1]/a";
//		XPathFactory xFactory = XPathFactory.newInstance();
//		XPath xpath = xFactory.newXPath();
//		try {
//			XPathExpression expr = xpath.compile(xPathPattern);
//			Object evaluated = expr.evaluate(xml, XPathConstants.NODESET);
//			NodeList nodes = (NodeList) evaluated;
//			ArrayList<Element> elements = new ArrayList<Element>();
//			for (int i = 0; i < nodes.getLength(); i++) {
//				Node node = nodes.item(i);
//				if (node.getNodeType() != Node.ELEMENT_NODE)
//					continue;
//				result.add(node.getFirstChild().getNodeValue());
//			}
//		} catch (XPathExpressionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result;
//	}
//	private String getFirstColumnSecondDivHrefInfo(Node xml, String attribute) {
//		String result = "";
//		String xPathPattern = "//*[@id=\"block-summary-block-summary-block\"]/div/div/div[2]/div/ul/li/a";
//		XPathFactory xFactory = XPathFactory.newInstance();
//		XPath xpath = xFactory.newXPath();
//		try {
//			XPathExpression expr = xpath.compile(xPathPattern);
//			Object evaluated = expr.evaluate(xml, XPathConstants.NODESET);
//			NodeList nodes = (NodeList) evaluated;
//			ArrayList<Element> elements = new ArrayList<Element>();
//			for (int i = 0; i < nodes.getLength(); i++) {
//				Node node = nodes.item(i);
//				if (node.getNodeType() != Node.ELEMENT_NODE)
//					continue;
//				// Home page is the first link
//				if (node.getTextContent().equals(attribute)) {
//					result = node.getAttributes().getNamedItem("href")
//							.getNodeValue();
//					break; // No need of finding more
//				}
//			}
//
//		} catch (XPathExpressionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result;
//	}
	
}
