package com.googlecode.pongo.tests.blog.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class AuthorCollection extends PongoCollection<Author> {
	
	public AuthorCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Author> findById(String id) {
		return new IteratorIterable<Author>(new PongoCursorIterator<Author>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Author> findByName(String q) {
		return new IteratorIterable<Author>(new PongoCursorIterator<Author>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Author findOneByName(String q) {
		Author author = (Author) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (author != null) {
			author.setPongoCollection(this);
		}
		return author;
	}

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Author> iterator() {
		return new PongoCursorIterator<Author>(this, dbCollection.find());
	}
	
	public void add(Author author) {
		super.add(author);
	}
	
	public void remove(Author author) {
		super.remove(author);
	}
	
}