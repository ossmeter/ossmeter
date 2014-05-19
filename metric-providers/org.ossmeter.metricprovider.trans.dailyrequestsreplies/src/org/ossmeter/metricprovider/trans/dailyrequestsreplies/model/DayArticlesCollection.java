package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DayArticlesCollection extends PongoCollection<DayArticles> {
	
	public DayArticlesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<DayArticles> findById(String id) {
		return new IteratorIterable<DayArticles>(new PongoCursorIterator<DayArticles>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DayArticles> findByName(String q) {
		return new IteratorIterable<DayArticles>(new PongoCursorIterator<DayArticles>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public DayArticles findOneByName(String q) {
		DayArticles dayArticles = (DayArticles) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (dayArticles != null) {
			dayArticles.setPongoCollection(this);
		}
		return dayArticles;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<DayArticles> iterator() {
		return new PongoCursorIterator<DayArticles>(this, dbCollection.find());
	}
	
	public void add(DayArticles dayArticles) {
		super.add(dayArticles);
	}
	
	public void remove(DayArticles dayArticles) {
		super.remove(dayArticles);
	}
	
}