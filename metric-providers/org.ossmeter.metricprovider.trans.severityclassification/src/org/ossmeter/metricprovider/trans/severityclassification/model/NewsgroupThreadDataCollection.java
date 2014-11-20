package org.ossmeter.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupThreadDataCollection extends PongoCollection<NewsgroupThreadData> {
	
	public NewsgroupThreadDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<NewsgroupThreadData> findById(String id) {
		return new IteratorIterable<NewsgroupThreadData>(new PongoCursorIterator<NewsgroupThreadData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupThreadData> findByUrl(String q) {
		return new IteratorIterable<NewsgroupThreadData>(new PongoCursorIterator<NewsgroupThreadData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public NewsgroupThreadData findOneByUrl(String q) {
		NewsgroupThreadData newsgroupThreadData = (NewsgroupThreadData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (newsgroupThreadData != null) {
			newsgroupThreadData.setPongoCollection(this);
		}
		return newsgroupThreadData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupThreadData> iterator() {
		return new PongoCursorIterator<NewsgroupThreadData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupThreadData newsgroupThreadData) {
		super.add(newsgroupThreadData);
	}
	
	public void remove(NewsgroupThreadData newsgroupThreadData) {
		super.remove(newsgroupThreadData);
	}
	
}