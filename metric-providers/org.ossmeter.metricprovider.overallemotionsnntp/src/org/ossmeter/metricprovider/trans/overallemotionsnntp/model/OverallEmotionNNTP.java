package org.ossmeter.metricprovider.trans.overallemotionsnntp.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class OverallEmotionNNTP extends PongoDB {
	
	public OverallEmotionNNTP() {}
	
	public OverallEmotionNNTP(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	protected EmotionCollection dimensions = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	public EmotionCollection getDimensions() {
		return dimensions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("OverallEmotionNNTP.newsgroups"));
		pongoCollections.add(newsgroups);
		dimensions = new EmotionCollection(db.getCollection("OverallEmotionNNTP.dimensions"));
		pongoCollections.add(dimensions);
	}
}