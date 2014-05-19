package org.ossmeter.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugzillaCommentsDataCollection extends PongoCollection<BugzillaCommentsData> {
	
	public BugzillaCommentsDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<BugzillaCommentsData> findById(String id) {
		return new IteratorIterable<BugzillaCommentsData>(new PongoCursorIterator<BugzillaCommentsData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugzillaCommentsData> findByUrl(String q) {
		return new IteratorIterable<BugzillaCommentsData>(new PongoCursorIterator<BugzillaCommentsData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public BugzillaCommentsData findOneByUrl(String q) {
		BugzillaCommentsData bugzillaCommentsData = (BugzillaCommentsData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (bugzillaCommentsData != null) {
			bugzillaCommentsData.setPongoCollection(this);
		}
		return bugzillaCommentsData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<BugzillaCommentsData> iterator() {
		return new PongoCursorIterator<BugzillaCommentsData>(this, dbCollection.find());
	}
	
	public void add(BugzillaCommentsData bugzillaCommentsData) {
		super.add(bugzillaCommentsData);
	}
	
	public void remove(BugzillaCommentsData bugzillaCommentsData) {
		super.remove(bugzillaCommentsData);
	}
	
}