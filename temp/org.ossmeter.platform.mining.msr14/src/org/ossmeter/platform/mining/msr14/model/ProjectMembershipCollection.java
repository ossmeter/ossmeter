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

public class ProjectMembershipCollection extends PongoCollection<ProjectMembership> {
	
	public ProjectMembershipCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ProjectMembership> findById(String id) {
		return new IteratorIterable<ProjectMembership>(new PongoCursorIterator<ProjectMembership>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ProjectMembership> iterator() {
		return new PongoCursorIterator<ProjectMembership>(this, dbCollection.find());
	}
	
	public void add(ProjectMembership projectMembership) {
		super.add(projectMembership);
	}
	
	public void remove(ProjectMembership projectMembership) {
		super.remove(projectMembership);
	}
	
}