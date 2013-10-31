package org.ossmeter.metricprovider.trans.commits.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Commits extends PongoDB {
	
	public Commits() {}
	
	public Commits(DB db) {
		setDb(db);
	}
	
	protected RepositoryDataCollection repositories = null;
	protected CommitDataCollection commits = null;
	
	
	
	public RepositoryDataCollection getRepositories() {
		return repositories;
	}
	
	public CommitDataCollection getCommits() {
		return commits;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		repositories = new RepositoryDataCollection(db.getCollection("repositories"));
		pongoCollections.add(repositories);
		commits = new CommitDataCollection(db.getCollection("commits"));
		pongoCollections.add(commits);
	}
}