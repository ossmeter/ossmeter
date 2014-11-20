package org.ossmeter.metricprovider.trans.bugs.emotions.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class EmotionDimensionCollection extends PongoCollection<EmotionDimension> {
	
	public EmotionDimensionCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<EmotionDimension> findById(String id) {
		return new IteratorIterable<EmotionDimension>(new PongoCursorIterator<EmotionDimension>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<EmotionDimension> findByBugTrackerId(String q) {
		return new IteratorIterable<EmotionDimension>(new PongoCursorIterator<EmotionDimension>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public EmotionDimension findOneByBugTrackerId(String q) {
		EmotionDimension emotionDimension = (EmotionDimension) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (emotionDimension != null) {
			emotionDimension.setPongoCollection(this);
		}
		return emotionDimension;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
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