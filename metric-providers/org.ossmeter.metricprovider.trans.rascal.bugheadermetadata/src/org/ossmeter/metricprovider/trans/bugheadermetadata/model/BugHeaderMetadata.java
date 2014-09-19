package org.ossmeter.metricprovider.trans.bugheadermetadata.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugHeaderMetadata extends PongoDB {
	
	public BugHeaderMetadata() {}
	
	public BugHeaderMetadata(DB db) {
		setDb(db);
	}
	
	protected BugDataCollection bugs = null;
	protected CommentDataCollection comments = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugDataCollection getBugs() {
		return bugs;
	}
	
	public CommentDataCollection getComments() {
		return comments;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugs = new BugDataCollection(db.getCollection("BugHeaderMetadata.bugs"));
		pongoCollections.add(bugs);
		comments = new CommentDataCollection(db.getCollection("BugHeaderMetadata.comments"));
		pongoCollections.add(comments);
	}
}