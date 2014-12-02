/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.tecnalia.ossmeter;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Element;

//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import com.tecnalia.ossmeter.model.EclipseTopLevelProject;
import com.tecnalia.ossmeter.util.XML;

/**
 * Retrieve data from eclipse projects
 */
public class EclipseProjectCollector {
	
	public final Map<String,EclipseTopLevelProject> eclipseTopLevelProjects=new TreeMap<String,EclipseTopLevelProject>();
	public static final String URL_ECLIPSE_PROJECTS="http://projects.eclipse.org";
	public static final String URL_LIST_PROJECTS="http://projects.eclipse.org/list-of-projects";
	public static final String URL_SUMMARY_PROJECTS="http://www.eclipse.org/projects/tools/status.php";
	public static final String CSS_SELECTOR_TO_SEARCH="#maincontent table";
	
	public static final int COLUMN_PROJECTTOP=0;
	public static final int COLUMN_PROJECT=1;
	public static final int COLUMN_COMMITERS=7;
	public static final int COLUMN_ORGANIZATIONS=8;
	
	
	private  final Logger logger;
	
	public EclipseProjectCollector(){
		logger=Logger.getLogger(this.getClass().getName());
	}
	
	
	public void collectDataFromInternet() throws Exception{
		try{
			loadProjectsFromStatusPage(); 
		}catch(Exception e){
			logger.log(Level.SEVERE,"Error when collecting data from Eclipse. Reason: "+e.getMessage(),e);
			throw e;
		}
		
	}
	
	/**
	 * Load projects and add some summary data
	 * http://www.eclipse.org/projects/tools/status.php
	 * @return
	 * @throws Exception
	 */
	protected void loadProjectsFromStatusPage() throws Exception{
		XML xml=new XML(new URL(URL_SUMMARY_PROJECTS));
		Element table=xml.get(CSS_SELECTOR_TO_SEARCH).get(0);
		List<Element> rows=XML.getChildrenElements(table);
		for(int r=1;r<rows.size();r++){//r=1 since first row is title
			Element row=rows.get(r);
			List<Element> columns=XML.getChildrenElements(row);
			//EclipseTopLevelProject
			Element elTProject=columns.get(COLUMN_PROJECTTOP);
			String projectTName=elTProject.getFirstChild().getNodeValue();
			if(eclipseTopLevelProjects.keySet().contains(projectTName)==false) eclipseTopLevelProjects.put(projectTName, new EclipseTopLevelProject(projectTName));
			EclipseTopLevelProject eclipseTopLevelProject=eclipseTopLevelProjects.get(projectTName);
			//EclipseProject
			String projectName,url;
			Element elProject=columns.get(COLUMN_PROJECT);
			try{projectName=elProject.getFirstChild().getFirstChild().getNodeValue();}catch(Exception e){projectName=null;}
			try{url=URL_ECLIPSE_PROJECTS+elProject.getFirstChild().getAttributes().getNamedItem("href").getNodeValue();}catch(Exception e){url=null;}
			int numberCommiters,numberOrganizations;
			try{numberCommiters=Integer.valueOf(columns.get(COLUMN_COMMITERS).getFirstChild().getNodeValue());}catch(Exception e){numberCommiters=0;}
			try{numberOrganizations=Integer.valueOf(columns.get(COLUMN_ORGANIZATIONS).getFirstChild().getNodeValue());}catch(Exception e){numberOrganizations=0;}
			eclipseTopLevelProject.createEclipseProject(projectName,url,numberCommiters,numberOrganizations);	
		}
	}
	
	private void printData(){
//		Gson gson=new GsonBuilder().serializeNulls().create();
//		String json=gson.toJson(eclipseTopLevelProjects);
//		System.out.println(json);
		
	}
	
	public static void main(String[] args) throws Exception{
		EclipseProjectCollector eclipseProjectCollector=new EclipseProjectCollector();
		eclipseProjectCollector.collectDataFromInternet();
		eclipseProjectCollector.printData();
	}

}
