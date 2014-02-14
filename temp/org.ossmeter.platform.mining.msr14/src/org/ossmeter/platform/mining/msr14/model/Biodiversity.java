package org.ossmeter.platform.mining.msr14.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Biodiversity extends PongoDB {
	
	public Biodiversity() {}
	
	public Biodiversity(DB db) {
		setDb(db);
	}
	
	protected UserCollection users = null;
	protected ProjectCollection projects = null;
	
	
	
	public UserCollection getUsers() {
		return users;
	}
	
	public ProjectCollection getProjects() {
		return projects;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		users = new UserCollection(db.getCollection("users"));
		pongoCollections.add(users);
		projects = new ProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
	}
}