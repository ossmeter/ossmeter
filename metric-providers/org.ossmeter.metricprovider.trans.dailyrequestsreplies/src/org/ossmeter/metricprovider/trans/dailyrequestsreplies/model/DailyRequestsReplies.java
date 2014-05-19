package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class DailyRequestsReplies extends PongoDB {
	
	public DailyRequestsReplies() {}
	
	public DailyRequestsReplies(DB db) {
		setDb(db);
	}
	
	protected DayArticlesCollection dayArticles = null;
	protected DayRepliesCollection dayReplies = null;
	protected DayRequestsCollection dayRequests = null;
	
	
	
	public DayArticlesCollection getDayArticles() {
		return dayArticles;
	}
	
	public DayRepliesCollection getDayReplies() {
		return dayReplies;
	}
	
	public DayRequestsCollection getDayRequests() {
		return dayRequests;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dayArticles = new DayArticlesCollection(db.getCollection("DailyRequestsReplies.dayArticles"));
		pongoCollections.add(dayArticles);
		dayReplies = new DayRepliesCollection(db.getCollection("DailyRequestsReplies.dayReplies"));
		pongoCollections.add(dayReplies);
		dayRequests = new DayRequestsCollection(db.getCollection("DailyRequestsReplies.dayRequests"));
		pongoCollections.add(dayRequests);
	}
}