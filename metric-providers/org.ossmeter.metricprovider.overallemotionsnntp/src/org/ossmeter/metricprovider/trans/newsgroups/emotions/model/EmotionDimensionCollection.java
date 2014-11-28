package org.ossmeter.metricprovider.trans.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class EmotionDimensionCollection extends PongoCollection<EmotionDimension> {
	
	public EmotionDimensionCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<EmotionDimension> findById(String id) {
		return new IteratorIterable<EmotionDimension>(new PongoCursorIterator<EmotionDimension>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<EmotionDimension> iterator() {
		return new PongoCursorIterator<EmotionDimension>(this, dbCollection.find());
	}
	
	public void add(EmotionDimension emotionDimension) {
		super.add(emotionDimension);
	}
	
	public void remove(EmotionDimension emotionDimension) {
		super.remove(emotionDimension);
	}
	
}