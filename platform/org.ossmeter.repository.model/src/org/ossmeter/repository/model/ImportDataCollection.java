/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ImportDataCollection extends PongoCollection<ImportData> {
	
	public ImportDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forge");
	}
	
	public Iterable<ImportData> findById(String id) {
		return new IteratorIterable<ImportData>(new PongoCursorIterator<ImportData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ImportData> findByForge(String q) {
		return new IteratorIterable<ImportData>(new PongoCursorIterator<ImportData>(this, dbCollection.find(new BasicDBObject("forge", q + ""))));
	}
	
	public ImportData findOneByForge(String q) {
		ImportData importData = (ImportData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forge", q + "")));
		if (importData != null) {
			importData.setPongoCollection(this);
		}
		return importData;
	}
	

	public long countByForge(String q) {
		return dbCollection.count(new BasicDBObject("forge", q + ""));
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