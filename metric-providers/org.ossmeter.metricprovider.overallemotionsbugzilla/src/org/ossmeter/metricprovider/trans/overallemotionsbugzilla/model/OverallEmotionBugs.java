package org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class OverallEmotionBugs extends PongoDB {
	
	public OverallEmotionBugs() {}
	
	public OverallEmotionBugs(DB db) {
		setDb(db);
	}
	
	protected BugzillaDataCollection bugzillas = null;
	protected EmotionCollection dimensions = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugzillaDataCollection getBugzillas() {
		return bugzillas;
	}
	
	public EmotionCollection getDimensions() {
		return dimensions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugzillas = new BugzillaDataCollection(db.getCollection("OverallEmotionBugs.bugzillas"));
		pongoCollections.add(bugzillas);
		dimensions = new EmotionCollection(db.getCollection("OverallEmotionBugs.dimensions"));
		pongoCollections.add(dimensions);
	}
}