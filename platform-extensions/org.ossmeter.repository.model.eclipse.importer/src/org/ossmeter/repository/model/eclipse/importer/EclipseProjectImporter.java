package org.ossmeter.repository.model.eclipse.importer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Role;
import org.ossmeter.repository.model.eclipse.EclipsePlatform;
import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.ProjectStatus;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.ossmeter.repository.model.eclipse.EclipseProject;
import org.ossmeter.repository.model.eclipse.importer.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class EclipseProjectImporter {
	
	public EclipseProject importProject(String projectId) {
			
		EclipseProject ep = new EclipseProject();
		String URL_PROJECT = "http://projects.eclipse.org/projects/"
				+ projectId;

		String html = null;
		try {
			html = getRawHtml(URL_PROJECT);
		} catch (Exception e) {
			e.printStackTrace();
		}

		XML xml = null;
		try {
			xml = new XML(html);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		Document xmlDoc = xml.getDOM();
		
		EclipseProject project = new EclipseProject();
		project.setShortName(projectId); 
		project.setDescriptionUrl(getDescriptionUrl(projectId));
		project.setParagraphUrl(getParagraphUrl(projectId));
		project.setDownloadsUrl(getDownloadsUrl(xmlDoc));
		project.setHomePage(getHomePage(xmlDoc));
		project.setProjectplanUrl(getProjectplanUrl(xmlDoc));
		project.setUpdatesiteUrl(getUpdatesiteUrl(xmlDoc));
	

		//Managing Eclipse Platforms
		String platformName;
		List<String> eclipsePlatformNames = getPlatforms(xmlDoc);
		Iterator it  = (Iterator) eclipsePlatformNames.iterator();
		while (it.hasNext()) {
			platformName = (String) it.next();
			EclipsePlatform eclipsePlatform = new EclipsePlatform();
			eclipsePlatform.setName(platformName);
			project.getPlatforms().add(eclipsePlatform);
		}
		
		
		//Managing Project Status
		//project.setStatus(ProjectStatus.valueOf(getStatus(xmlDoc)));
		
		
		//Managing Persons
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
	        it.remove(); // avoids a ConcurrentModificationException        
	    }
		
	    return project;
	}


	private HashMap getPersons(Node xml) {
		// TODO
		HashMap persons = new HashMap();

		List roles_person1 = new ArrayList();
		roles_person1.add("committer");
		roles_person1.add("leader");
		roles_person1.add("mentor");	
		persons.put("Dimitris Kolovos", roles_person1);
		
		List roles_person2 = new ArrayList();
		roles_person2.add("committer");
		persons.put("Davide Di_Ruscio", roles_person2);
		
		return persons;
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

	private String getParent(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getProjectplanUrl(Node xml) {
		return getFirstColumnSecondDivHrefInfo(xml, "Plan");
	}

	private String getUpdatesiteUrl(Node xml) {
		return null;
	}

	private String getStatus(Node xml) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getHomePage(Node xml) {
		return getFirstColumnSecondDivHrefInfo(xml, "Website");		
	}

	private String getDownloadsUrl(Node xml) {
		return getFirstColumnSecondDivHrefInfo(xml, "Downloads");
	}

	private String getDescriptionUrl(String name) {
		// TODO Auto-generated method stub
		return "http://www.eclipse.org/"+name+"/project-info/summary.html";
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

	// private String getShortName(Node xml) {
	// // TODO Auto-generated method stub
	// return null;
	// }

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
