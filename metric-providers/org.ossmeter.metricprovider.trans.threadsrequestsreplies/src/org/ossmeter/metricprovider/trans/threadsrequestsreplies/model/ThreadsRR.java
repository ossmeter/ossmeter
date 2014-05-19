package org.ossmeter.metricprovider.trans.threadsrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ThreadsRR extends PongoDB {
	
	public ThreadsRR() {}
	
	public ThreadsRR(DB db) {
		setDb(db);
	}
	
	protected ThreadStatisticsCollection threads = null;
	protected CurrentDateCollection date = null;
	
	
	
	public ThreadStatisticsCollection getThreads() {
		return threads;
	}
	
	public CurrentDateCollection getDate() {
		return date;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		threads = new ThreadStatisticsCollection(db.getCollection("ThreadsRR.threads"));
		pongoCollections.add(threads);
		date = new CurrentDateCollection(db.getCollection("ThreadsRR.date"));
		pongoCollections.add(date);
	}
}