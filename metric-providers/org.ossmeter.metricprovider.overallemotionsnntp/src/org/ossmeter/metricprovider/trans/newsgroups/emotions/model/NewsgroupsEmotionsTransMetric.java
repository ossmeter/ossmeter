package org.ossmeter.metricprovider.trans.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsEmotionsTransMetric extends PongoDB {
	
	public NewsgroupsEmotionsTransMetric() {}
	
	public NewsgroupsEmotionsTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	protected EmotionDimensionCollection dimensions = null;
	
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	public EmotionDimensionCollection getDimensions() {
		return dimensions;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("NewsgroupsEmotionsTransMetric.newsgroups"));
		pongoCollections.add(newsgroups);
		dimensions = new EmotionDimensionCollection(db.getCollection("NewsgroupsEmotionsTransMetric.dimensions"));
		pongoCollections.add(dimensions);
	}
}