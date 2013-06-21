package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ProjectRepository extends PongoDB {
	
	public ProjectRepository(DB db) {
		super(db);
		projects = new ProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
	}
	
	protected ProjectCollection projects = null;
	
	public ProjectCollection getProjects() {
		return projects;
	}
	
	
}