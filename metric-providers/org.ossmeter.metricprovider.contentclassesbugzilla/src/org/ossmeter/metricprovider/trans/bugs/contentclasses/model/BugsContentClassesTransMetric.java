package org.ossmeter.metricprovider.trans.bugs.contentclasses.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsContentClassesTransMetric extends PongoDB {
	
	public BugsContentClassesTransMetric() {}
	
	public BugsContentClassesTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerDataCollection bugTrackerData = null;
	protected ContentClassCollection contentClasses = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerDataCollection getBugTrackerData() {
		return bugTrackerData;
	}
	
	public ContentClassCollection getContentClasses() {
		return contentClasses;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerData = new BugTrackerDataCollection(db.getCollection("BugsContentClassesTransMetric.bugTrackerData"));
		pongoCollections.add(bugTrackerData);
		contentClasses = new ContentClassCollection(db.getCollection("BugsContentClassesTransMetric.contentClasses"));
		pongoCollections.add(contentClasses);
	}
}