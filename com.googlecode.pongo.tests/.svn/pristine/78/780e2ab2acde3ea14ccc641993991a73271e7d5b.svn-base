package com.googlecode.pongo.tests.ossmeter.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Ossmeter extends PongoDB {
	
	public Ossmeter() {}
	
	public Ossmeter(DB db) {
		setDb(db);
	}
	
	protected ProjectCollection projects = null;
	
	
	
	public ProjectCollection getProjects() {
		return projects;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		projects = new ProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
	}
}