/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.metricprovider.trans.hourlycommits.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class HourCollection extends PongoCollection<Hour> {
	
	public HourCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("hour");
	}
	
	public Iterable<Hour> findById(String id) {
		return new IteratorIterable<Hour>(new PongoCursorIterator<Hour>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Hour> findByHour(String q) {
		return new IteratorIterable<Hour>(new PongoCursorIterator<Hour>(this, dbCollection.find(new BasicDBObject("hour", q + ""))));
	}
	
	public Hour findOneByHour(String q) {
		Hour hour = (Hour) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("hour", q + "")));
		if (hour != null) {
			hour.setPongoCollection(this);
		}
		return hour;
	}
	

	public long countByHour(String q) {
		return dbCollection.count(new BasicDBObject("hour", q + ""));
	}
	
	@Override
	public Iterator<Hour> iterator() {
		return new PongoCursorIterator<Hour>(this, dbCollection.find());
	}
	
	public void add(Hour hour) {
		super.add(hour);
	}
	
	public void remove(Hour hour) {
		super.remove(hour);
	}
	
}