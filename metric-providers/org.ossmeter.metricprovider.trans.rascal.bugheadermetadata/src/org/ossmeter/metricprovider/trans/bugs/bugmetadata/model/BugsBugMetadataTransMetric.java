package org.ossmeter.metricprovider.trans.bugs.bugmetadata.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsBugMetadataTransMetric extends PongoDB {
	
	public BugsBugMetadataTransMetric() {}
	
	public BugsBugMetadataTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerDataCollection bugTrackerData = null;
	protected CommentDataCollection comments = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerDataCollection getBugTrackerData() {
		return bugTrackerData;
	}
	
	public CommentDataCollection getComments() {
		return comments;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerData = new BugTrackerDataCollection(db.getCollection("BugsBugMetadataTransMetric.bugTrackerData"));
		pongoCollections.add(bugTrackerData);
		comments = new CommentDataCollection(db.getCollection("BugsBugMetadataTransMetric.comments"));
		pongoCollections.add(comments);
	}
}