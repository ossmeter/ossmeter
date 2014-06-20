package org.ossmeter.platform.factoids;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class FactoidCollection extends PongoCollection<Factoid> {
	
	public FactoidCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("metricId");
	}
	
	public Iterable<Factoid> findById(String id) {
		return new IteratorIterable<Factoid>(new PongoCursorIterator<Factoid>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Factoid> findByMetricId(String q) {
		return new IteratorIterable<Factoid>(new PongoCursorIterator<Factoid>(this, dbCollection.find(new BasicDBObject("metricId", q + ""))));
	}
	
	public Factoid findOneByMetricId(String q) {
		Factoid factoid = (Factoid) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("metricId", q + "")));
		if (factoid != null) {
			factoid.setPongoCollection(this);
		}
		return factoid;
	}
	

	public long countByMetricId(String q) {
		return dbCollection.count(new BasicDBObject("metricId", q + ""));
	}
	
	@Override
	public Iterator<Factoid> iterator() {
		return new PongoCursorIterator<Factoid>(this, dbCollection.find());
	}
	
	public void add(Factoid factoid) {
		super.add(factoid);
	}
	
	public void remove(Factoid factoid) {
		super.remove(factoid);
	}
	
}