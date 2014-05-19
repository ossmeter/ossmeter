package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DayRequestsCollection extends PongoCollection<DayRequests> {
	
	public DayRequestsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<DayRequests> findById(String id) {
		return new IteratorIterable<DayRequests>(new PongoCursorIterator<DayRequests>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DayRequests> findByName(String q) {
		return new IteratorIterable<DayRequests>(new PongoCursorIterator<DayRequests>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public DayRequests findOneByName(String q) {
		DayRequests dayRequests = (DayRequests) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (dayRequests != null) {
			dayRequests.setPongoCollection(this);
		}
		return dayRequests;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<DayRequests> iterator() {
		return new PongoCursorIterator<DayRequests>(this, dbCollection.find());
	}
	
	public void add(DayRequests dayRequests) {
		super.add(dayRequests);
	}
	
	public void remove(DayRequests dayRequests) {
		super.remove(dayRequests);
	}
	
}