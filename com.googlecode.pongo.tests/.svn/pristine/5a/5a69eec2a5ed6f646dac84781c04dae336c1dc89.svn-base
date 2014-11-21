package com.googlecode.pongo.tests.inheritance.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class InheritanceDB extends PongoDB {
	
	public InheritanceDB() {}
	
	public InheritanceDB(DB db) {
		setDb(db);
	}
	
	protected ParentCollection classes = null;
	
	
	
	public ParentCollection getClasses() {
		return classes;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		classes = new ParentCollection(db.getCollection("classes"));
		pongoCollections.add(classes);
	}
}