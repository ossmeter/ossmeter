package org.ossmeter.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlesCollection extends PongoCollection<NewsgroupArticles> {
	
	public NewsgroupArticlesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<NewsgroupArticles> findById(String id) {
		return new IteratorIterable<NewsgroupArticles>(new PongoCursorIterator<NewsgroupArticles>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticles> findByUrl(String q) {
		return new IteratorIterable<NewsgroupArticles>(new PongoCursorIterator<NewsgroupArticles>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public NewsgroupArticles findOneByUrl(String q) {
		NewsgroupArticles newsgroupArticles = (NewsgroupArticles) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (newsgroupArticles != null) {
			newsgroupArticles.setPongoCollection(this);
		}
		return newsgroupArticles;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticles> iterator() {
		return new PongoCursorIterator<NewsgroupArticles>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticles newsgroupArticles) {
		super.add(newsgroupArticles);
	}
	
	public void remove(NewsgroupArticles newsgroupArticles) {
		super.remove(newsgroupArticles);
	}
	
}