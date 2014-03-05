package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CCDataCollection extends PongoCollection<CCData> {
	
	public CCDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("fileURL");
	}
	
	public Iterable<CCData> findById(String id) {
		return new IteratorIterable<CCData>(new PongoCursorIterator<CCData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CCData> findByFileURL(String q) {
		return new IteratorIterable<CCData>(new PongoCursorIterator<CCData>(this, dbCollection.find(new BasicDBObject("fileURL", q + ""))));
	}
	
	public CCData findOneByFileURL(String q) {
		CCData cCData = (CCData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("fileURL", q + "")));
		if (cCData != null) {
			cCData.setPongoCollection(this);
		}
		return cCData;
	}
	

	public long countByFileURL(String q) {
		return dbCollection.count(new BasicDBObject("fileURL", q + ""));
	}
	
	@Override
	public Iterator<CCData> iterator() {
		return new PongoCursorIterator<CCData>(this, dbCollection.find());
	}
	
	public void add(CCData cCData) {
		super.add(cCData);
	}
	
	public void remove(CCData cCData) {
		super.remove(cCData);
	}
	
}