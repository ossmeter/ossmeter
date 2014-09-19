package org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class EmotionCollection extends PongoCollection<Emotion> {
	
	public EmotionCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<Emotion> findById(String id) {
		return new IteratorIterable<Emotion>(new PongoCursorIterator<Emotion>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Emotion> findByUrl(String q) {
		return new IteratorIterable<Emotion>(new PongoCursorIterator<Emotion>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public Emotion findOneByUrl(String q) {
		Emotion emotion = (Emotion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (emotion != null) {
			emotion.setPongoCollection(this);
		}
		return emotion;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
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