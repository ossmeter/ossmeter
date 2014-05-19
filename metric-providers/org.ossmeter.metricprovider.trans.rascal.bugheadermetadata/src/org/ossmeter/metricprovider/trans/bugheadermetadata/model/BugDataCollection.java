package org.ossmeter.metricprovider.trans.bugheadermetadata.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugDataCollection extends PongoCollection<BugData> {
	
	public BugDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<BugData> findById(String id) {
		return new IteratorIterable<BugData>(new PongoCursorIterator<BugData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugData> findByUrl(String q) {
		return new IteratorIterable<BugData>(new PongoCursorIterator<BugData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public BugData findOneByUrl(String q) {
		BugData bugData = (BugData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (bugData != null) {
			bugData.setPongoCollection(this);
		}
		return bugData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<BugData> iterator() {
		return new PongoCursorIterator<BugData>(this, dbCollection.find());
	}
	
	public void add(BugData bugData) {
		super.add(bugData);
	}
	
	public void remove(BugData bugData) {
		super.remove(bugData);
	}
	
}