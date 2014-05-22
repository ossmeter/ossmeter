package org.ossmeter.metricprovider.numberofbugzillapatches.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugzillaDataCollection extends PongoCollection<BugzillaData> {
	
	public BugzillaDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<BugzillaData> findById(String id) {
		return new IteratorIterable<BugzillaData>(new PongoCursorIterator<BugzillaData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugzillaData> findByUrl(String q) {
		return new IteratorIterable<BugzillaData>(new PongoCursorIterator<BugzillaData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public BugzillaData findOneByUrl(String q) {
		BugzillaData bugzillaData = (BugzillaData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (bugzillaData != null) {
			bugzillaData.setPongoCollection(this);
		}
		return bugzillaData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<BugzillaData> iterator() {
		return new PongoCursorIterator<BugzillaData>(this, dbCollection.find());
	}
	
	public void add(BugzillaData bugzillaData) {
		super.add(bugzillaData);
	}
	
	public void remove(BugzillaData bugzillaData) {
		super.remove(bugzillaData);
	}
	
}