package org.ossmeter.metricprovider.trans.overallemotionsnntp.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class EmotionCollection extends PongoCollection<Emotion> {
	
	public EmotionCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url_name");
		createIndex("emotionLabel");
	}
	
	public Iterable<Emotion> findById(String id) {
		return new IteratorIterable<Emotion>(new PongoCursorIterator<Emotion>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Emotion> findByUrl_name(String q) {
		return new IteratorIterable<Emotion>(new PongoCursorIterator<Emotion>(this, dbCollection.find(new BasicDBObject("url_name", q + ""))));
	}
	
	public Emotion findOneByUrl_name(String q) {
		Emotion emotion = (Emotion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url_name", q + "")));
		if (emotion != null) {
			emotion.setPongoCollection(this);
		}
		return emotion;
	}
	

	public long countByUrl_name(String q) {
		return dbCollection.count(new BasicDBObject("url_name", q + ""));
	}
	public Iterable<Emotion> findByEmotionLabel(String q) {
		return new IteratorIterable<Emotion>(new PongoCursorIterator<Emotion>(this, dbCollection.find(new BasicDBObject("emotionLabel", q + ""))));
	}
	
	public Emotion findOneByEmotionLabel(String q) {
		Emotion emotion = (Emotion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("emotionLabel", q + "")));
		if (emotion != null) {
			emotion.setPongoCollection(this);
		}
		return emotion;
	}
	

	public long countByEmotionLabel(String q) {
		return dbCollection.count(new BasicDBObject("emotionLabel", q + ""));
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