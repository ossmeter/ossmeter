package org.ossmeter.metricprovider.trans.newsgroups.activeusers.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class UserCollection extends PongoCollection<User> {
	
	public UserCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<User> findById(String id) {
		return new IteratorIterable<User>(new PongoCursorIterator<User>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<User> findByNewsgroupName(String q) {
		return new IteratorIterable<User>(new PongoCursorIterator<User>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public User findOneByNewsgroupName(String q) {
		User user = (User) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (user != null) {
			user.setPongoCollection(this);
		}
		return user;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
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