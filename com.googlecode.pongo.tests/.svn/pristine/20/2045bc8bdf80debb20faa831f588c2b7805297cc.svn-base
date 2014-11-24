package com.googlecode.pongo.tests.stubs.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class GoogleCode extends PongoDB {
	
	public GoogleCode() {}
	
	public GoogleCode(DB db) {
		setDb(db);
	}
	
	protected GoogleProjectCollection projects = null;
	protected DeveloperCollection developers = null;
	
	public GoogleProjectCollection getProjects() {
		return projects;
	}
	
	public DeveloperCollection getDevelopers() {
		return developers;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		projects = new GoogleProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		developers = new DeveloperCollection(db.getCollection("developers"));
		pongoCollections.add(developers);
	}
}