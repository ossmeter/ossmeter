package org.ossmeter.metricprovider.activeusers.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class ActiveUsers extends PongoDB {
	
	public ActiveUsers() {}
	
	public ActiveUsers(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("ActiveUsers.newsgroups"));
		pongoCollections.add(newsgroups);
	}
}