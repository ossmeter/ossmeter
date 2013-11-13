package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NOADataCollection extends PongoCollection<NOAData> {
	
	public NOADataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("className");
	}
	
	public Iterable<NOAData> findById(String id) {
		return new IteratorIterable<NOAData>(new PongoCursorIterator<NOAData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NOAData> findByClassName(String q) {
		return new IteratorIterable<NOAData>(new PongoCursorIterator<NOAData>(this, dbCollection.find(new BasicDBObject("className", q + ""))));
	}
	
	public NOAData findOneByClassName(String q) {
		NOAData nOAData = (NOAData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("className", q + "")));
		if (nOAData != null) {
			nOAData.setPongoCollection(this);
		}
		return nOAData;
	}
	

	public long countByClassName(String q) {
		return dbCollection.count(new BasicDBObject("className", q + ""));
	}
	
	@Override
	public Iterator<NOAData> iterator() {
		return new PongoCursorIterator<NOAData>(this, dbCollection.find());
	}
	
	public void add(NOAData nOAData) {
		super.add(nOAData);
	}
	
	public void remove(NOAData nOAData) {
		super.remove(nOAData);
	}
	
}