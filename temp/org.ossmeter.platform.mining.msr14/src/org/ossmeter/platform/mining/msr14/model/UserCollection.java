package org.ossmeter.platform.mining.msr14.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class UserCollection extends PongoCollection<User> {
	
	public UserCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<User> findById(String id) {
		return new IteratorIterable<User>(new PongoCursorIterator<User>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<User> iterator() {
		return new PongoCursorIterator<User>(this, dbCollection.find());
	}
	
	public void add(User user) {
		super.add(user);
	}
	
	public void remove(User user) {
		super.remove(user);
	}
	
}