/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class MetricAnalysisCollection extends PongoCollection<MetricAnalysis> {
	
	public MetricAnalysisCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<MetricAnalysis> findById(String id) {
		return new IteratorIterable<MetricAnalysis>(new PongoCursorIterator<MetricAnalysis>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<MetricAnalysis> iterator() {
		return new PongoCursorIterator<MetricAnalysis>(this, dbCollection.find());
	}
	
	public void add(MetricAnalysis metricAnalysis) {
		super.add(metricAnalysis);
	}
	
	public void remove(MetricAnalysis metricAnalysis) {
		super.remove(metricAnalysis);
	}
	
}