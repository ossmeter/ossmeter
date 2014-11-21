package com.googlecode.pongo.tests.blog.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class MemberCollection extends PongoCollection<Member> {
	
	public MemberCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Member> findById(String id) {
		return new IteratorIterable<Member>(new PongoCursorIterator<Member>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Member> findByName(String q) {
		return new IteratorIterable<Member>(new PongoCursorIterator<Member>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Member findOneByName(String q) {
		Member member = (Member) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (member != null) {
			member.setPongoCollection(this);
		}
		return member;
	}

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Member> iterator() {
		return new PongoCursorIterator<Member>(this, dbCollection.find());
	}
	
	public void add(Member member) {
		super.add(member);
	}
	
	public void remove(Member member) {
		super.remove(member);
	}
	
}