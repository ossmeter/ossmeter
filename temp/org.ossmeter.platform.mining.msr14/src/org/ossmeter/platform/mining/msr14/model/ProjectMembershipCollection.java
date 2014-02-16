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