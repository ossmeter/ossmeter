package org.ossmeter.metricprovider.trans.bugs.patches.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsPatchesTransMetric extends PongoDB {
	
	public BugsPatchesTransMetric() {}
	
	public BugsPatchesTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerDataCollection bugTrackerData = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerDataCollection getBugTrackerData() {
		return bugTrackerData;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerData = new BugTrackerDataCollection(db.getCollection("BugsPatchesTransMetric.bugTrackerData"));
		pongoCollections.add(bugTrackerData);
	}
}