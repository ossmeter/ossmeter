package org.ossmeter.metricprovider.trans.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupDataCollection extends PongoCollection<NewsgroupData> {
	
	public NewsgroupDataCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<NewsgroupData> findById(String id) {
		return new IteratorIterable<NewsgroupData>(new PongoCursorIterator<NewsgroupData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<NewsgroupData> iterator() {
		return new PongoCursorIterator<NewsgroupData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupData newsgroupData) {
		super.add(newsgroupData);
	}
	
	public void remove(NewsgroupData newsgroupData) {
		super.remove(newsgroupData);
	}
	
}