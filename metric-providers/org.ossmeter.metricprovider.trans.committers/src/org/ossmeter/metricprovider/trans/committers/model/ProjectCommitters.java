package org.ossmeter.metricprovider.trans.committers.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ProjectCommitters extends PongoDB {
	
	public ProjectCommitters() {}
	
	public ProjectCommitters(DB db) {
		setDb(db);
	}
	
	protected CommitterCollection committers = null;
	
	
	
	public CommitterCollection getCommitters() {
		return committers;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		committers = new CommitterCollection(db.getCollection("committers"));
		pongoCollections.add(committers);
	}
}