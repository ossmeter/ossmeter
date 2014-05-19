package org.ossmeter.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Rrc extends PongoDB {
	
	public Rrc() {}
	
	public Rrc(DB db) {
		setDb(db);
	}
	
	protected BugzillaCommentsDataCollection bugzillaComments = null;
	protected NewsgroupArticlesDataCollection newsgroupArticles = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugzillaCommentsDataCollection getBugzillaComments() {
		return bugzillaComments;
	}
	
	public NewsgroupArticlesDataCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugzillaComments = new BugzillaCommentsDataCollection(db.getCollection("Rrc.bugzillaComments"));
		pongoCollections.add(bugzillaComments);
		newsgroupArticles = new NewsgroupArticlesDataCollection(db.getCollection("Rrc.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
	}
}