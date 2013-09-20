package com.tecnalia.ossmeter.model;

import java.util.ArrayList;
import java.util.List;

public class EclipseTopLevelProject {
	
	public final String name;
	//public final String url;
	public final List<EclipseProject> projects;
	
	public EclipseTopLevelProject(String name){
		this.name=name;
		//this.url=url;
		this.projects=new ArrayList<EclipseProject>();
	}
	
	public EclipseProject createEclipseProject(String name, String url, int activeCommiters,int activeOrganizations){
		EclipseProject pr=new EclipseProject(name,url,activeCommiters,activeOrganizations);
		projects.add(pr);
		return pr;
	}
	
}
