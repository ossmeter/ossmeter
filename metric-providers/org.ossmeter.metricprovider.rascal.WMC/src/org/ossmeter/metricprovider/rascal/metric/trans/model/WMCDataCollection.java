package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class WMCDataCollection extends PongoCollection<WMCData> {
	
	public WMCDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("className");
	}
	
	public Iterable<WMCData> findById(String id) {
		return new IteratorIterable<WMCData>(new PongoCursorIterator<WMCData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<WMCData> findByClassName(String q) {
		return new IteratorIterable<WMCData>(new PongoCursorIterator<WMCData>(this, dbCollection.find(new BasicDBObject("className", q + ""))));
	}
	
	public WMCData findOneByClassName(String q) {
		WMCData wMCData = (WMCData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("className", q + "")));
		if (wMCData != null) {
			wMCData.setPongoCollection(this);
		}
		return wMCData;
	}
	

	public long countByClassName(String q) {
		return dbCollection.count(new BasicDBObject("className", q + ""));
	}
	
	@Override
	public Iterator<WMCData> iterator() {
		return new PongoCursorIterator<WMCData>(this, dbCollection.find());
	}
	
	public void add(WMCData wMCData) {
		super.add(wMCData);
	}
	
	public void remove(WMCData wMCData) {
		super.remove(wMCData);
	}
	
}