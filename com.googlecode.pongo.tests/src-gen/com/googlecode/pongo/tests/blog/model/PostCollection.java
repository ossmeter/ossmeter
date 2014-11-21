package com.googlecode.pongo.tests.blog.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class PostCollection extends PongoCollection<Post> {
	
	public PostCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("title");
	}
	
	public Iterable<Post> findById(String id) {
		return new IteratorIterable<Post>(new PongoCursorIterator<Post>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Post> findByTitle(String q) {
		return new IteratorIterable<Post>(new PongoCursorIterator<Post>(this, dbCollection.find(new BasicDBObject("title", q + ""))));
	}
	
	public Post findOneByTitle(String q) {
		Post post = (Post) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("title", q + "")));
		if (post != null) {
			post.setPongoCollection(this);
		}
		return post;
	}

	public long countByTitle(String q) {
		return dbCollection.count(new BasicDBObject("title", q + ""));
	}
	
	@Override
	public Iterator<Post> iterator() {
		return new PongoCursorIterator<Post>(this, dbCollection.find());
	}
	
	public void add(Post post) {
		super.add(post);
	}
	
	public void remove(Post post) {
		super.remove(post);
	}
	
}