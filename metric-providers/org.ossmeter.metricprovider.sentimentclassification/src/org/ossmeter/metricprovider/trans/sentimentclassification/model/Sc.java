package org.ossmeter.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Sc extends PongoDB {
	
	public Sc() {}
	
	public Sc(DB db) {
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
		bugzillaComments = new BugzillaCommentsDataCollection(db.getCollection("Sc.bugzillaComments"));
		pongoCollections.add(bugzillaComments);
		newsgroupArticles = new NewsgroupArticlesDataCollection(db.getCollection("Sc.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
	}
}