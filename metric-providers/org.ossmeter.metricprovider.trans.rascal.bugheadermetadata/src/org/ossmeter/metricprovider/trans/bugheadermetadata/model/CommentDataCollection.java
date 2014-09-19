package org.ossmeter.metricprovider.trans.bugheadermetadata.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CommentDataCollection extends PongoCollection<CommentData> {
	
	public CommentDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
	}
	
	public Iterable<CommentData> findById(String id) {
		return new IteratorIterable<CommentData>(new PongoCursorIterator<CommentData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CommentData> findByUrl(String q) {
		return new IteratorIterable<CommentData>(new PongoCursorIterator<CommentData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public CommentData findOneByUrl(String q) {
		CommentData commentData = (CommentData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (commentData != null) {
			commentData.setPongoCollection(this);
		}
		return commentData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<CommentData> iterator() {
		return new PongoCursorIterator<CommentData>(this, dbCollection.find());
	}
	
	public void add(CommentData commentData) {
		super.add(commentData);
	}
	
	public void remove(CommentData commentData) {
		super.remove(commentData);
	}
	
}