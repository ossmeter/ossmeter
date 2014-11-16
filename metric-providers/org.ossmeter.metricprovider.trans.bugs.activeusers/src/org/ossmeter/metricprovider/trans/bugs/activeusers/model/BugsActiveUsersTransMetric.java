package org.ossmeter.metricprovider.trans.bugs.activeusers.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsActiveUsersTransMetric extends PongoDB {
	
	public BugsActiveUsersTransMetric() {}
	
	public BugsActiveUsersTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugDataCollection bugs = null;
	protected UserCollection users = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugDataCollection getBugs() {
		return bugs;
	}
	
	public UserCollection getUsers() {
		return users;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugs = new BugDataCollection(db.getCollection("BugsActiveUsersTransMetric.bugs"));
		pongoCollections.add(bugs);
		users = new UserCollection(db.getCollection("BugsActiveUsersTransMetric.users"));
		pongoCollections.add(users);
	}
}