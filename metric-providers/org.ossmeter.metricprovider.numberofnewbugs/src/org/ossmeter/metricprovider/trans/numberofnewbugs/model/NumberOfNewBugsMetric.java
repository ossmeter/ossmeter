package org.ossmeter.metricprovider.trans.numberofnewbugs.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NumberOfNewBugsMetric extends PongoDB {
	
	public NumberOfNewBugsMetric() {}
	
	public NumberOfNewBugsMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerDataCollection bugTrackerData = null;
	
	
	
	public BugTrackerDataCollection getBugTrackerData() {
		return bugTrackerData;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerData = new BugTrackerDataCollection(db.getCollection("NumberOfNewBugsMetric.bugTrackerData"));
		pongoCollections.add(bugTrackerData);
	}
}