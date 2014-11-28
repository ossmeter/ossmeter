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
import com.mongodb.*;

public class Biodiversity extends PongoDB {
	
	public Biodiversity() {}
	
	public Biodiversity(DB db) {
		setDb(db);
	}
	
	protected UserCollection users = null;
	protected ProjectCollection projects = null;
	protected ProjectMembershipCollection projectMemberships = null;
	
	
	
	public UserCollection getUsers() {
		return users;
	}
	
	public ProjectCollection getProjects() {
		return projects;
	}
	
	public ProjectMembershipCollection getProjectMemberships() {
		return projectMemberships;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		users = new UserCollection(db.getCollection("users"));
		pongoCollections.add(users);
		projects = new ProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		projectMemberships = new ProjectMembershipCollection(db.getCollection("projectMemberships"));
		pongoCollections.add(projectMemberships);
	}
}