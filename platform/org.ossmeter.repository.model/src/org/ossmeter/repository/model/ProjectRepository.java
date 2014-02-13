package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ProjectRepository extends PongoDB {
	
	public ProjectRepository() {}
	
	public ProjectRepository(DB db) {
		setDb(db);
	}
	
	protected ProjectCollection projects = null;
	protected RoleCollection roles = null;
	protected ImportDataCollection gitHubImportData = null;
	
	
	
	public ProjectCollection getProjects() {
		return projects;
	}
	
	public RoleCollection getRoles() {
		return roles;
	}
	
	public ImportDataCollection getGitHubImportData() {
		return gitHubImportData;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		projects = new ProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		roles = new RoleCollection(db.getCollection("roles"));
		pongoCollections.add(roles);
		gitHubImportData = new ImportDataCollection(db.getCollection("gitHubImportData"));
		pongoCollections.add(gitHubImportData);
	}
}