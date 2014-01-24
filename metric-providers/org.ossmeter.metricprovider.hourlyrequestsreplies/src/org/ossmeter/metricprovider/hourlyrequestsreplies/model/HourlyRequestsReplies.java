package org.ossmeter.metricprovider.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class HourlyRequestsReplies extends PongoDB {
	
	public HourlyRequestsReplies() {}
	
	public HourlyRequestsReplies(DB db) {
		setDb(db);
	}
	
	protected HourArticlesCollection hourArticles = null;
	protected HourRepliesCollection hourReplies = null;
	protected HourRequestsCollection hourRequests = null;
	
	
	
	public HourArticlesCollection getHourArticles() {
		return hourArticles;
	}
	
	public HourRepliesCollection getHourReplies() {
		return hourReplies;
	}
	
	public HourRequestsCollection getHourRequests() {
		return hourRequests;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		hourArticles = new HourArticlesCollection(db.getCollection("HourlyRequestsReplies.hourArticles"));
		pongoCollections.add(hourArticles);
		hourReplies = new HourRepliesCollection(db.getCollection("HourlyRequestsReplies.hourReplies"));
		pongoCollections.add(hourReplies);
		hourRequests = new HourRequestsCollection(db.getCollection("HourlyRequestsReplies.hourRequests"));
		pongoCollections.add(hourRequests);
	}
}