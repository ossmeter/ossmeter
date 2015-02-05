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

public class ProjectErrorCollection extends PongoCollection<ProjectError> {
	
	public ProjectErrorCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ProjectError> findById(String id) {
		return new IteratorIterable<ProjectError>(new PongoCursorIterator<ProjectError>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ProjectError> iterator() {
		return new PongoCursorIterator<ProjectError>(this, dbCollection.find());
	}
	
	public void add(ProjectError projectError) {
		super.add(projectError);
	}
	
	public void remove(ProjectError projectError) {
		super.remove(projectError);
	}
	
}