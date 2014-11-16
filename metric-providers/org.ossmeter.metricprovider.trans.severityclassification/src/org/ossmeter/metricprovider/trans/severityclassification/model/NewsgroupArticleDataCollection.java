package org.ossmeter.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticleDataCollection extends PongoCollection<NewsgroupArticleData> {
	
	public NewsgroupArticleDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<NewsgroupArticleData> findById(String id) {
		return new IteratorIterable<NewsgroupArticleData>(new PongoCursorIterator<NewsgroupArticleData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticleData> findByUrl(String q) {
		return new IteratorIterable<NewsgroupArticleData>(new PongoCursorIterator<NewsgroupArticleData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public NewsgroupArticleData findOneByUrl(String q) {
		NewsgroupArticleData newsgroupArticleData = (NewsgroupArticleData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (newsgroupArticleData != null) {
			newsgroupArticleData.setPongoCollection(this);
		}
		return newsgroupArticleData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticleData> iterator() {
		return new PongoCursorIterator<NewsgroupArticleData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticleData newsgroupArticleData) {
		super.add(newsgroupArticleData);
	}
	
	public void remove(NewsgroupArticleData newsgroupArticleData) {
		super.remove(newsgroupArticleData);
	}
	
}