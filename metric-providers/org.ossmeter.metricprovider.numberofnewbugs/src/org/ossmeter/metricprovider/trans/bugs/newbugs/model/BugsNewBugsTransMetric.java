package org.ossmeter.metricprovider.trans.bugs.newbugs.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsNewBugsTransMetric extends PongoDB {
	
	public BugsNewBugsTransMetric() {}
	
	public BugsNewBugsTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerDataCollection bugTrackerData = null;
	
	
	
	public BugTrackerDataCollection getBugTrackerData() {
		return bugTrackerData;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerData = new BugTrackerDataCollection(db.getCollection("BugsNewBugsTransMetric.bugTrackerData"));
		pongoCollections.add(bugTrackerData);
	}
}