package com.googlecode.pongo.tests.inheritance.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ParentCollection extends PongoCollection<Parent> {
	
	public ParentCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<Parent> findById(String id) {
		return new IteratorIterable<Parent>(new PongoCursorIterator<Parent>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Parent> findByName(String q) {
		return new IteratorIterable<Parent>(new PongoCursorIterator<Parent>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Parent findOneByName(String q) {
		Parent parent = (Parent) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (parent != null) {
			parent.setPongoCollection(this);
		}
		return parent;
	}
	
	
	public Iterable<Parent> findChildOnesByName(String q) {
		return new IteratorIterable<Parent>(new PongoCursorIterator<Parent>(this, dbCollection.find(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.inheritance.model.ChildOne"))));
	}
	
	public Parent findOneChildOneByName(String q) {
		Parent parent = (Parent) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.inheritance.model.ChildOne")));
		if (parent != null) {
			parent.setPongoCollection(this);
		}
		return parent;
	}
	
	
	public Iterable<Parent> findChildTwosByName(String q) {
		return new IteratorIterable<Parent>(new PongoCursorIterator<Parent>(this, dbCollection.find(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.inheritance.model.ChildTwo"))));
	}
	
	public Parent findOneChildTwoByName(String q) {
		Parent parent = (Parent) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.inheritance.model.ChildTwo")));
		if (parent != null) {
			parent.setPongoCollection(this);
		}
		return parent;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Parent> iterator() {
		return new PongoCursorIterator<Parent>(this, dbCollection.find());
	}
	
	public void add(Parent parent) {
		super.add(parent);
	}
	
	public void remove(Parent parent) {
		super.remove(parent);
	}
	
}