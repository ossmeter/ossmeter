package org.ossmeter.metricprovider.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class HourRequestsCollection extends PongoCollection<HourRequests> {
	
	public HourRequestsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("hour");
	}
	
	public Iterable<HourRequests> findById(String id) {
		return new IteratorIterable<HourRequests>(new PongoCursorIterator<HourRequests>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<HourRequests> findByHour(String q) {
		return new IteratorIterable<HourRequests>(new PongoCursorIterator<HourRequests>(this, dbCollection.find(new BasicDBObject("hour", q + ""))));
	}
	
	public HourRequests findOneByHour(String q) {
		HourRequests hourRequests = (HourRequests) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("hour", q + "")));
		if (hourRequests != null) {
			hourRequests.setPongoCollection(this);
		}
		return hourRequests;
	}
	

	public long countByHour(String q) {
		return dbCollection.count(new BasicDBObject("hour", q + ""));
	}
	
	@Override
	public Iterator<HourRequests> iterator() {
		return new PongoCursorIterator<HourRequests>(this, dbCollection.find());
	}
	
	public void add(HourRequests hourRequests) {
		super.add(hourRequests);
	}
	
	public void remove(HourRequests hourRequests) {
		super.remove(hourRequests);
	}
	
}