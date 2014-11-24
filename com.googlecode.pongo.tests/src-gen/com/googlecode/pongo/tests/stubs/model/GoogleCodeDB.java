package com.googlecode.pongo.tests.stubs.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class GoogleCodeDB extends PongoDB {
	
	public GoogleCodeDB(DB db) {
		super(db);
		projects = new GoogleProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		developers = new DeveloperCollection(db.getCollection("developers"));
		pongoCollections.add(developers);
	}
	
	protected GoogleProjectCollection projects = null;
	protected DeveloperCollection developers = null;
	
	public GoogleProjectCollection getProjects() {
		return projects;
	}
	
	public DeveloperCollection getDevelopers() {
		return developers;
	}
	
	
}