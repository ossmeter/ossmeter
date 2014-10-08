package org.ossmeter.metricprovider.trans.bugs.emotions.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class EmotionCollection extends PongoCollection<Emotion> {
	
	public EmotionCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<Emotion> findById(String id) {
		return new IteratorIterable<Emotion>(new PongoCursorIterator<Emotion>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Emotion> findByBugTrackerId(String q) {
		return new IteratorIterable<Emotion>(new PongoCursorIterator<Emotion>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public Emotion findOneByBugTrackerId(String q) {
		Emotion emotion = (Emotion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (emotion != null) {
			emotion.setPongoCollection(this);
		}
		return emotion;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<Emotion> iterator() {
		return new PongoCursorIterator<Emotion>(this, dbCollection.find());
	}
	
	public void add(Emotion emotion) {
		super.add(emotion);
	}
	
	public void remove(Emotion emotion) {
		super.remove(emotion);
	}
	
}