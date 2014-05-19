package org.ossmeter.metricprovider.trans.activeusers.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class UserCollection extends PongoCollection<User> {
	
	public UserCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url_name");
	}
	
	public Iterable<User> findById(String id) {
		return new IteratorIterable<User>(new PongoCursorIterator<User>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<User> findByUrl_name(String q) {
		return new IteratorIterable<User>(new PongoCursorIterator<User>(this, dbCollection.find(new BasicDBObject("url_name", q + ""))));
	}
	
	public User findOneByUrl_name(String q) {
		User user = (User) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url_name", q + "")));
		if (user != null) {
			user.setPongoCollection(this);
		}
		return user;
	}
	

	public long countByUrl_name(String q) {
		return dbCollection.count(new BasicDBObject("url_name", q + ""));
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