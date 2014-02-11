package org.ossmeter.metricprovider.threadsrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ThreadStatisticsCollection extends PongoCollection<ThreadStatistics> {
	
	public ThreadStatisticsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url_name");
		createIndex("threadId");
	}
	
	public Iterable<ThreadStatistics> findById(String id) {
		return new IteratorIterable<ThreadStatistics>(new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ThreadStatistics> findByUrl_name(String q) {
		return new IteratorIterable<ThreadStatistics>(new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find(new BasicDBObject("url_name", q + ""))));
	}
	
	public ThreadStatistics findOneByUrl_name(String q) {
		ThreadStatistics threadStatistics = (ThreadStatistics) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url_name", q + "")));
		if (threadStatistics != null) {
			threadStatistics.setPongoCollection(this);
		}
		return threadStatistics;
	}
	

	public long countByUrl_name(String q) {
		return dbCollection.count(new BasicDBObject("url_name", q + ""));
	}
	public Iterable<ThreadStatistics> findByThreadId(int q) {
		return new IteratorIterable<ThreadStatistics>(new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find(new BasicDBObject("threadId", q + ""))));
	}
	
	public ThreadStatistics findOneByThreadId(int q) {
		ThreadStatistics threadStatistics = (ThreadStatistics) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("threadId", q + "")));
		if (threadStatistics != null) {
			threadStatistics.setPongoCollection(this);
		}
		return threadStatistics;
	}
	

	public long countByThreadId(int q) {
		return dbCollection.count(new BasicDBObject("threadId", q + ""));
	}
	
	@Override
	public Iterator<ThreadStatistics> iterator() {
		return new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find());
	}
	
	public void add(ThreadStatistics threadStatistics) {
		super.add(threadStatistics);
	}
	
	public void remove(ThreadStatistics threadStatistics) {
		super.remove(threadStatistics);
	}
	
}