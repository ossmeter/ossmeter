package org.ossmeter.metricprovider.trans.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class HourRepliesCollection extends PongoCollection<HourReplies> {
	
	public HourRepliesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("hour");
	}
	
	public Iterable<HourReplies> findById(String id) {
		return new IteratorIterable<HourReplies>(new PongoCursorIterator<HourReplies>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<HourReplies> findByHour(String q) {
		return new IteratorIterable<HourReplies>(new PongoCursorIterator<HourReplies>(this, dbCollection.find(new BasicDBObject("hour", q + ""))));
	}
	
	public HourReplies findOneByHour(String q) {
		HourReplies hourReplies = (HourReplies) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("hour", q + "")));
		if (hourReplies != null) {
			hourReplies.setPongoCollection(this);
		}
		return hourReplies;
	}
	

	public long countByHour(String q) {
		return dbCollection.count(new BasicDBObject("hour", q + ""));
	}
	
	@Override
	public Iterator<HourReplies> iterator() {
		return new PongoCursorIterator<HourReplies>(this, dbCollection.find());
	}
	
	public void add(HourReplies hourReplies) {
		super.add(hourReplies);
	}
	
	public void remove(HourReplies hourReplies) {
		super.remove(hourReplies);
	}
	
}