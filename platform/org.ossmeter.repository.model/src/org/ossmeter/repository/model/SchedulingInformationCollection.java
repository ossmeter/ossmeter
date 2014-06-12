package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class SchedulingInformationCollection extends PongoCollection<SchedulingInformation> {
	
	public SchedulingInformationCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<SchedulingInformation> findById(String id) {
		return new IteratorIterable<SchedulingInformation>(new PongoCursorIterator<SchedulingInformation>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<SchedulingInformation> iterator() {
		return new PongoCursorIterator<SchedulingInformation>(this, dbCollection.find());
	}
	
	public void add(SchedulingInformation schedulingInformation) {
		super.add(schedulingInformation);
	}
	
	public void remove(SchedulingInformation schedulingInformation) {
		super.remove(schedulingInformation);
	}
	
}