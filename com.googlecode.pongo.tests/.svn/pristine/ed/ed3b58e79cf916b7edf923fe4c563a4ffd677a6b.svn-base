package com.googlecode.pongo.tests.ossmeter.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ProjectCollection extends PongoCollection<Project> {
	
	public ProjectCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Project> findById(String id) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Project> iterator() {
		return new PongoCursorIterator<Project>(this, dbCollection.find());
	}
	
	public void add(Project project) {
		super.add(project);
	}
	
	public void remove(Project project) {
		super.remove(project);
	}
	
}