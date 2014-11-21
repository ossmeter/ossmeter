package com.googlecode.pongo.tests.svnloc.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class LinesOfCodeMetricCollection extends PongoCollection<LinesOfCodeMetric> {
	
	public LinesOfCodeMetricCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("file");
	}
	
	public Iterable<LinesOfCodeMetric> findById(String id) {
		return new IteratorIterable<LinesOfCodeMetric>(new PongoCursorIterator<LinesOfCodeMetric>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<LinesOfCodeMetric> findByFile(String q) {
		return new IteratorIterable<LinesOfCodeMetric>(new PongoCursorIterator<LinesOfCodeMetric>(this, dbCollection.find(new BasicDBObject("file", q + ""))));
	}
	
	public LinesOfCodeMetric findOneByFile(String q) {
		return (LinesOfCodeMetric) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("file", q + "")));
	}

	public long countByFile(String q) {
		return dbCollection.count(new BasicDBObject("file", q + ""));
	}
	
	@Override
	public Iterator<LinesOfCodeMetric> iterator() {
		return new PongoCursorIterator<LinesOfCodeMetric>(this, dbCollection.find());
	}
	
	public void add(LinesOfCodeMetric linesOfCodeMetric) {
		super.add(linesOfCodeMetric);
	}
	
	public void remove(LinesOfCodeMetric linesOfCodeMetric) {
		super.remove(linesOfCodeMetric);
	}
	
}