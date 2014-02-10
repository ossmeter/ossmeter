package org.ossmeter.repository.model.eclipse.importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.License;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Role;
import org.ossmeter.repository.model.VcsRepository;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.repository.model.cc.forum.Forum;
import org.ossmeter.repository.model.eclipse.EclipsePlatform;
import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.MailingList;
import org.ossmeter.repository.model.eclipse.ProjectStatus;
import org.ossmeter.repository.model.eclipse.Wiki;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicScrollPaneUI.VSBChangeListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.importer.util.XML;
import org.ossmeter.repository.model.vcs.git.GitRepository;
import org.ossmeter.repository.model.vcs.svn.SvnRepository;
import org.ossmeter.repository.model.vcs.cvs.CvsRepository;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONValue;

public class EclipseProjectImporter {
	
	
	private boolean isNotNull(JSONObject currentProg, String attribute ) 
	{
		if (((JSONArray)currentProg.get(attribute)).isEmpty())
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
	
	public Collection<EclipseProject> importAll() {
		
		Collection<EclipseProject> projects = new ArrayList<EclipseProject>();
		
		try {
			System.out.println("Retrieving the list of Eclipse projects...");

//			InputStream is = new FileInputStream(new File("C:\\eclipse.json"));
			InputStream is = new URL("http://projects.eclipse.org/json/projects/all").openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);		
	
			JSONObject obj=(JSONObject)JSONValue.parse(jsonText);	
			JSONObject jsonProjects = (JSONObject)((JSONObject)obj.get("projects"));

			Iterator iter  = jsonProjects.entrySet().iterator();
			    while(iter.hasNext()){
			      Map.Entry entry = (Map.Entry)iter.next();

			      System.out.println("---> Retrieving metadata of " + entry.getKey());			     
			      projects.add(importProject((String)entry.getKey()));
			    }
			 
			return projects;
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	    return null;

	}
	
	public EclipseProject importProject(String projectId) {
			
		String URL_PROJECT = "http://projects.eclipse.org/projects/"+ projectId;
		String html = null;
		XML xml = null;
		
		EclipseProject project = new EclipseProject();
		
		//Retrieving data by parsing the Web page of the project
		//This is necessary to retrieve metadata not available in JSON
		try {
			html = getRawHtml(URL_PROJECT);
			xml = new XML(html);
			Document xmlDoc = xml.getDOM();
			String platformName;
			
			List<String> eclipsePlatformNames = getPlatforms(xmlDoc);
			Iterator it  = (Iterator) eclipsePlatformNames.iterator();
			while (it.hasNext()) {
				platformName = (String) it.next();
				EclipsePlatform eclipsePlatform = new EclipsePlatform();
				eclipsePlatform.setName(platformName);
				project.getPlatforms().add(eclipsePlatform);
			}

			/*
			HashMap<String, List> persons = getPersons(xmlDoc);
			it = persons.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey() + " = " + pairs.getValue());	        
		        Person p = new Person();
				p.setName((String)pairs.getKey());     
				Iterator it2 = ((List)pairs.getValue()).iterator();
				while (it2.hasNext()) {
					String roleName = (String) it2.next();
					Role r = new Role();
					r.setName(roleName);
					p.getRoles().add(r);
				}
				project.getPersons().add(p);	
		        it.remove(); 
		    }
		    */
			
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
			
			if ((isNotNull(currentProg,"description")))
				project.setDescription(((JSONObject)((JSONArray)currentProg.get("description")).get(0)).get("value").toString());
		
			/* TODO Parent projects require a specific management. Probably the metamodel has to be changed
			 * by modifying the type of "parent". It might be a string containing the name of the parent project 
			if ((isNotNull(currentProg,"parent_project")))
				project.setParent(parent);
			*/
			
			if ((isNotNull(currentProg,"documentation_url")))
				project.setDescriptionUrl(((JSONObject)((JSONArray)currentProg.get("documentation_url")).get(0)).get("url").toString());

			project.setParagraphUrl(getParagraphUrl(projectId));
			
			if ((isNotNull(currentProg,"download_url")))
					project.setDownloadsUrl(((JSONObject)((JSONArray)currentProg.get("download_url")).get(0)).get("url").toString());

			if ((isNotNull(currentProg,"website_url")))
				project.setHomePage(((JSONObject)((JSONArray)currentProg.get("website_url")).get(0)).get("url").toString());
	
			if ((isNotNull(currentProg,"plan_url")))
				project.setProjectplanUrl(((JSONObject)((JSONArray)currentProg.get("plan_url")).get(0)).get("url").toString());
	
			if ((isNotNull(currentProg,"wiki_url"))) {
				Wiki wiki = new Wiki();
				wiki.setUrl(((JSONObject)((JSONArray)currentProg.get("wiki_url")).get(0)).get("url").toString());
				project.getCommunicationChannels().add(wiki);
			}
			 
			if ((isNotNull(currentProg,"bugzilla"))) {
				Bugzilla bugzilla = new Bugzilla();
				JSONObject bugzillaJsonObject = (JSONObject)((JSONArray)currentProg.get("bugzilla")).get(0);
				bugzilla.setComponent((String)bugzillaJsonObject.get("component"));
				bugzilla.setCgiQueryProgram((String)bugzillaJsonObject.get("query_url"));
				bugzilla.setUrl((String)bugzillaJsonObject.get("create_url"));
				bugzilla.setComponent((String)bugzillaJsonObject.get("component"));
				project.getBugTrackingSystems().add(bugzilla);
			}
			
			if ((isNotNull(currentProg,"update_sites")))
				project.setUpdatesiteUrl(((JSONObject)((JSONArray)currentProg.get("update_sites")).get(0)).get("url").toString());
	
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

			if ((isNotNull(currentProg,"mailing_lists"))){			
				JSONArray mailingLists = (JSONArray)currentProg.get("mailing_lists");
				Iterator<JSONObject> iter  = mailingLists.iterator();
			    MailingList mailingList = null;
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					mailingList = new MailingList();
					mailingList.setName((String)entry.get("name"));
					mailingList.setUrl((String)entry.get("url"));
					project.getCommunicationChannels().add(mailingList);
				}
			}

			if ((isNotNull(currentProg,"state")))
				project.setState(((JSONObject)((JSONArray)currentProg.get("state")).get(0)).get("value").toString());		
			
			if ((isNotNull(currentProg,"forums"))){
				JSONArray forums = (JSONArray)currentProg.get("forums");
				Iterator<JSONObject> iter  = forums.iterator();
			    Forum forum = null;
				while(iter.hasNext()){					
					JSONObject entry = (JSONObject)iter.next();
					forum = new Forum();
					forum.setName((String)entry.get("name"));
					forum.setUrl((String)entry.get("url"));
					forum.setDescription((String)entry.get("description"));
					project.getCommunicationChannels().add(forum);
				}
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
					repository.setUrl((String)entry.get("url"));
				}
				project.getVcsRepositories().add(repository);
				}
			}	
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		return project;	
		
	}

	private HashMap getPersons(Node xml) {
		// TODO Auto-generated method stub
		/*
		HashMap persons = new HashMap();

		List roles_person1 = new ArrayList();
		roles_person1.add("committer");
		roles_person1.add("leader");
		roles_person1.add("mentor");	
		persons.put("Dimitris Kolovos", roles_person1);
		
		List roles_person2 = new ArrayList();
		roles_person2.add("committer");
		persons.put("Davide Di_Ruscio", roles_person2);
		
		return persons;*/

		return null;

	}

	private String getReleases(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getArticles(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getReviews(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getMentors(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getLeaders(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getCommitters(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<String> getPlatforms(Node xml) {
		// Left region, first div -> Platforms
		// //*[@id="block-summary-block-summary-block"]/div/div/div[1]/a
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
		// TODO Auto-generated method stub
		return "http://www.eclipse.org/"+name+"/project-info/project-page-paragraph.html";
	}

	private String getFirstColumnSecondDivHrefInfo(Node xml, String attribute) {
		// Left region, second div -> Links to home page, plans, etc...
		// *[@id="block-summary-block-summary-block"]/div/div/div[2]/div/ul/li/a
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
		// int index1=text.indexOf("<table class=\"views-table cols-4\"");
		// int index2=text.indexOf("<tbody",index1);
		// int index3=text.indexOf("</tbody>",index2);
		// text=text.substring(index2,index3+"</tbody>".length());
		return text;
	}

	
}
