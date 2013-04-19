package org.ossmeter.platform;

import org.ossmeter.repository.model.ProjectRepository;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class ProjectRepositoryManager {
	
	protected ProjectRepository projectRepository = null;
	protected final String projectsDatabaseName = "ossmeter";
	protected DB db;
	protected Mongo mongo;
	
	public ProjectRepositoryManager(Mongo mongo) {
		this.mongo = mongo;
		init();
	}
	
	protected void init() {
		this.db = mongo.getDB(projectsDatabaseName);
		projectRepository = new ProjectRepository(db);
	}
	
	public ProjectRepository getProjectRepository() {
		return projectRepository;
	}
	
	public DB getDb() {
		return db;
	}
	
	public boolean exists(String projectName){
		if(projectRepository.getProjects().findOneByName(projectName) !=null) return true;
		return false;
	}
	
	public void reset() {
		mongo.dropDatabase(projectsDatabaseName);
		init();
	}
}
