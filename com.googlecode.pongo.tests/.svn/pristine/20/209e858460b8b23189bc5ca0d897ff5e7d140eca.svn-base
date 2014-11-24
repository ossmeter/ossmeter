package com.googlecode.pongo.tests.stubs.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DeveloperCollection extends PongoCollection<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary> {
	
	public DeveloperCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary> findById(String id) {
		return new IteratorIterable<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary>(new PongoCursorIterator<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary> iterator() {
		return new PongoCursorIterator<com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary>(this, dbCollection.find());
	}
	
	public void add(com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary developer) {
		super.add(developer);
	}
	
	public void remove(com.googlecode.pongo.tests.stubs.model.DeveloperFromAnotherLibrary developer) {
		super.remove(developer);
	}
	
}