package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DayRepliesCollection extends PongoCollection<DayReplies> {
	
	public DayRepliesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<DayReplies> findById(String id) {
		return new IteratorIterable<DayReplies>(new PongoCursorIterator<DayReplies>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DayReplies> findByName(String q) {
		return new IteratorIterable<DayReplies>(new PongoCursorIterator<DayReplies>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public DayReplies findOneByName(String q) {
		DayReplies dayReplies = (DayReplies) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (dayReplies != null) {
			dayReplies.setPongoCollection(this);
		}
		return dayReplies;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<DayReplies> iterator() {
		return new PongoCursorIterator<DayReplies>(this, dbCollection.find());
	}
	
	public void add(DayReplies dayReplies) {
		super.add(dayReplies);
	}
	
	public void remove(DayReplies dayReplies) {
		super.remove(dayReplies);
	}
	
}