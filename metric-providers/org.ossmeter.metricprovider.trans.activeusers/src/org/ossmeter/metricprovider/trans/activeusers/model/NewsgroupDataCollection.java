package org.ossmeter.metricprovider.trans.activeusers.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupDataCollection extends PongoCollection<NewsgroupData> {
	
	public NewsgroupDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url_name");
	}
	
	public Iterable<NewsgroupData> findById(String id) {
		return new IteratorIterable<NewsgroupData>(new PongoCursorIterator<NewsgroupData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupData> findByUrl_name(String q) {
		return new IteratorIterable<NewsgroupData>(new PongoCursorIterator<NewsgroupData>(this, dbCollection.find(new BasicDBObject("url_name", q + ""))));
	}
	
	public NewsgroupData findOneByUrl_name(String q) {
		NewsgroupData newsgroupData = (NewsgroupData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url_name", q + "")));
		if (newsgroupData != null) {
			newsgroupData.setPongoCollection(this);
		}
		return newsgroupData;
	}
	

	public long countByUrl_name(String q) {
		return dbCollection.count(new BasicDBObject("url_name", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupData> iterator() {
		return new PongoCursorIterator<NewsgroupData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupData newsgroupData) {
		super.add(newsgroupData);
	}
	
	public void remove(NewsgroupData newsgroupData) {
		super.remove(newsgroupData);
	}
	
}