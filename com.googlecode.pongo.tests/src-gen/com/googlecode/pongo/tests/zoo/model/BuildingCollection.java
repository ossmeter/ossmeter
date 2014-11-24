package com.googlecode.pongo.tests.zoo.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BuildingCollection extends PongoCollection<Building> {
	
	public BuildingCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Building> findById(String id) {
		return new IteratorIterable<Building>(new PongoCursorIterator<Building>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Building> iterator() {
		return new PongoCursorIterator<Building>(this, dbCollection.find());
	}
	
	public void add(Building building) {
		super.add(building);
	}
	
	public void remove(Building building) {
		super.remove(building);
	}
	
}