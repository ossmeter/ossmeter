package org.ossmeter.metricprovider.loc.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

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