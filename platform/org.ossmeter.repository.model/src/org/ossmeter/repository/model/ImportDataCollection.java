package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ImportDataCollection extends PongoCollection<ImportData> {
	
	public ImportDataCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ImportData> findById(String id) {
		return new IteratorIterable<ImportData>(new PongoCursorIterator<ImportData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ImportData> iterator() {
		return new PongoCursorIterator<ImportData>(this, dbCollection.find());
	}
	
	public void add(ImportData importData) {
		super.add(importData);
	}
	
	public void remove(ImportData importData) {
		super.remove(importData);
	}
	
}