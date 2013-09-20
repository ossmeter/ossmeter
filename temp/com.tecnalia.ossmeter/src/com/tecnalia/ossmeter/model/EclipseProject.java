package com.tecnalia.ossmeter.model;

public class EclipseProject {
	
	public final String name;
	public final String url;
	public final int activeCommiters;
	public final int activeOrganizations;
	
	public EclipseProject(String name, String url, int activeCommiters,int activeOrganizations){
		this.name=name;
		this.url=url;
		this.activeCommiters=activeCommiters;
		this.activeOrganizations=activeOrganizations;
	}
	

}
