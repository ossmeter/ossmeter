package org.ossmeter.metricprovider.trans.commits.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CommitDataCollection extends PongoCollection<CommitData> {
	
	public CommitDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("date");
	}
	
	public Iterable<CommitData> findById(String id) {
		return new IteratorIterable<CommitData>(new PongoCursorIterator<CommitData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CommitData> findByDate(String q) {
		return new IteratorIterable<CommitData>(new PongoCursorIterator<CommitData>(this, dbCollection.find(new BasicDBObject("date", q + ""))));
	}
	
	public CommitData findOneByDate(String q) {
		CommitData commitData = (CommitData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("date", q + "")));
		if (commitData != null) {
			commitData.setPongoCollection(this);
		}
		return commitData;
	}
	

	public long countByDate(String q) {
		return dbCollection.count(new BasicDBObject("date", q + ""));
	}
	
	@Override
	public Iterator<CommitData> iterator() {
		return new PongoCursorIterator<CommitData>(this, dbCollection.find());
	}
	
	public void add(CommitData commitData) {
		super.add(commitData);
	}
	
	public void remove(CommitData commitData) {
		super.remove(commitData);
	}
	
}