package com.googlecode.pongo.tests.zoo.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class StaffCollection extends PongoCollection<Staff> {
	
	public StaffCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Staff> findById(String id) {
		return new IteratorIterable<Staff>(new PongoCursorIterator<Staff>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Staff> iterator() {
		return new PongoCursorIterator<Staff>(this, dbCollection.find());
	}
	
	public void add(Staff staff) {
		super.add(staff);
	}
	
	public void remove(Staff staff) {
		super.remove(staff);
	}
	
}