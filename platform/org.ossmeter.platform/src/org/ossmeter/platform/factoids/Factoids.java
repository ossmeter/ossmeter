package org.ossmeter.platform.factoids;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Factoids extends PongoDB {
	
	public Factoids() {}
	
	public Factoids(DB db) {
		setDb(db);
	}
	
	protected FactoidCollection factoids = null;
	
	
	
	public FactoidCollection getFactoids() {
		return factoids;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		factoids = new FactoidCollection(db.getCollection("factoids"));
		pongoCollections.add(factoids);
	}
}