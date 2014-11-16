package org.ossmeter.metricprovider.trans.bugs.emotions.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsEmotionsTransMetric extends PongoDB {
	
	public BugsEmotionsTransMetric() {}
	
	public BugsEmotionsTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerDataCollection bugTrackerData = null;
	protected EmotionDimensionCollection dimensions = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerDataCollection getBugTrackerData() {
		return bugTrackerData;
	}
	
	public EmotionDimensionCollection getDimensions() {
		return dimensions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerData = new BugTrackerDataCollection(db.getCollection("BugsEmotionsTransMetric.bugTrackerData"));
		pongoCollections.add(bugTrackerData);
		dimensions = new EmotionDimensionCollection(db.getCollection("BugsEmotionsTransMetric.dimensions"));
		pongoCollections.add(dimensions);
	}
}