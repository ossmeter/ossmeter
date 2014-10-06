package org.ossmeter.metricprovider.trans.newsgroups.contentclasses.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ContentClassCollection extends PongoCollection<ContentClass> {
	
	public ContentClassCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url_name");
	}
	
	public Iterable<ContentClass> findById(String id) {
		return new IteratorIterable<ContentClass>(new PongoCursorIterator<ContentClass>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ContentClass> findByUrl_name(String q) {
		return new IteratorIterable<ContentClass>(new PongoCursorIterator<ContentClass>(this, dbCollection.find(new BasicDBObject("url_name", q + ""))));
	}
	
	public ContentClass findOneByUrl_name(String q) {
		ContentClass contentClass = (ContentClass) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url_name", q + "")));
		if (contentClass != null) {
			contentClass.setPongoCollection(this);
		}
		return contentClass;
	}
	

	public long countByUrl_name(String q) {
		return dbCollection.count(new BasicDBObject("url_name", q + ""));
	}
	
	@Override
	public Iterator<ContentClass> iterator() {
		return new PongoCursorIterator<ContentClass>(this, dbCollection.find());
	}
	
	public void add(ContentClass contentClass) {
		super.add(contentClass);
	}
	
	public void remove(ContentClass contentClass) {
		super.remove(contentClass);
	}
	
}