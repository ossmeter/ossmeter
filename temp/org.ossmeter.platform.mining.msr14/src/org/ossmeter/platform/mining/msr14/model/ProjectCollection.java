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
package org.ossmeter.platform.mining.msr14.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ProjectCollection extends PongoCollection<Project> {
	
	public ProjectCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Project> findById(String id) {
		return new IteratorIterable<Project>(new PongoCursorIterator<Project>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Project> iterator() {
		return new PongoCursorIterator<Project>(this, dbCollection.find());
	}
	
	public void add(Project project) {
		super.add(project);
	}
	
	public void remove(Project project) {
		super.remove(project);
	}
	
}