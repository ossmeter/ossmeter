package org.ossmeter.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlesDataCollection extends PongoCollection<NewsgroupArticlesData> {
	
	public NewsgroupArticlesDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsGroupName");
	}
	
	public Iterable<NewsgroupArticlesData> findById(String id) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticlesData> findByNewsGroupName(String q) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("newsGroupName", q + ""))));
	}
	
	public NewsgroupArticlesData findOneByNewsGroupName(String q) {
		NewsgroupArticlesData newsgroupArticlesData = (NewsgroupArticlesData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsGroupName", q + "")));
		if (newsgroupArticlesData != null) {
			newsgroupArticlesData.setPongoCollection(this);
		}
		return newsgroupArticlesData;
	}
	

	public long countByNewsGroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsGroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticlesData> iterator() {
		return new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticlesData newsgroupArticlesData) {
		super.add(newsgroupArticlesData);
	}
	
	public void remove(NewsgroupArticlesData newsgroupArticlesData) {
		super.remove(newsgroupArticlesData);
	}
	
}