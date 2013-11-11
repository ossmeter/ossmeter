package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class LinesOfCodeDataCollection extends PongoCollection<LinesOfCodeData> {
	
	public LinesOfCodeDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("file");
	}
	
	public Iterable<LinesOfCodeData> findById(String id) {
		return new IteratorIterable<LinesOfCodeData>(new PongoCursorIterator<LinesOfCodeData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<LinesOfCodeData> findByFile(String q) {
		return new IteratorIterable<LinesOfCodeData>(new PongoCursorIterator<LinesOfCodeData>(this, dbCollection.find(new BasicDBObject("file", q + ""))));
	}
	
	public LinesOfCodeData findOneByFile(String q) {
		LinesOfCodeData linesOfCodeData = (LinesOfCodeData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("file", q + "")));
		if (linesOfCodeData != null) {
			linesOfCodeData.setPongoCollection(this);
		}
		return linesOfCodeData;
	}
	

	public long countByFile(String q) {
		return dbCollection.count(new BasicDBObject("file", q + ""));
	}
	
	@Override
	public Iterator<LinesOfCodeData> iterator() {
		return new PongoCursorIterator<LinesOfCodeData>(this, dbCollection.find());
	}
	
	public void add(LinesOfCodeData linesOfCodeData) {
		super.add(linesOfCodeData);
	}
	
	public void remove(LinesOfCodeData linesOfCodeData) {
		super.remove(linesOfCodeData);
	}
	
}