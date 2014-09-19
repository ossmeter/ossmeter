package org.ossmeter.metricprovider.trans.contentclassesbugzilla.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class ContentClassesBugs extends PongoDB {
	
	public ContentClassesBugs() {}
	
	public ContentClassesBugs(DB db) {
		setDb(db);
	}
	
	protected BugzillaDataCollection bugzillas = null;
	protected ContentClassCollection contentClasses = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugzillaDataCollection getBugzillas() {
		return bugzillas;
	}
	
	public ContentClassCollection getContentClasses() {
		return contentClasses;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugzillas = new BugzillaDataCollection(db.getCollection("ContentClassesBugs.bugzillas"));
		pongoCollections.add(bugzillas);
		contentClasses = new ContentClassCollection(db.getCollection("ContentClassesBugs.contentClasses"));
		pongoCollections.add(contentClasses);
	}
}