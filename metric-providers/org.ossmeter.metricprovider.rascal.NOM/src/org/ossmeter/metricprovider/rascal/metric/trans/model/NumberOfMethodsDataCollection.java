package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NumberOfMethodsDataCollection extends PongoCollection<NumberOfMethodsData> {
	
	public NumberOfMethodsDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("className");
	}
	
	public Iterable<NumberOfMethodsData> findById(String id) {
		return new IteratorIterable<NumberOfMethodsData>(new PongoCursorIterator<NumberOfMethodsData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NumberOfMethodsData> findByClassName(String q) {
		return new IteratorIterable<NumberOfMethodsData>(new PongoCursorIterator<NumberOfMethodsData>(this, dbCollection.find(new BasicDBObject("className", q + ""))));
	}
	
	public NumberOfMethodsData findOneByClassName(String q) {
		NumberOfMethodsData numberOfMethodsData = (NumberOfMethodsData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("className", q + "")));
		if (numberOfMethodsData != null) {
			numberOfMethodsData.setPongoCollection(this);
		}
		return numberOfMethodsData;
	}
	

	public long countByClassName(String q) {
		return dbCollection.count(new BasicDBObject("className", q + ""));
	}
	
	@Override
	public Iterator<NumberOfMethodsData> iterator() {
		return new PongoCursorIterator<NumberOfMethodsData>(this, dbCollection.find());
	}
	
	public void add(NumberOfMethodsData numberOfMethodsData) {
		super.add(numberOfMethodsData);
	}
	
	public void remove(NumberOfMethodsData numberOfMethodsData) {
		super.remove(numberOfMethodsData);
	}
	
}