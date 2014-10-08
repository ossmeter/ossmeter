package org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsDailyRequestsRepliesTransMetric extends PongoDB {
	
	public NewsgroupsDailyRequestsRepliesTransMetric() {}
	
	public NewsgroupsDailyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected DayArticlesCollection dayArticles = null;
	
	
	
	public DayArticlesCollection getDayArticles() {
		return dayArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dayArticles = new DayArticlesCollection(db.getCollection("NewsgroupsDailyRequestsRepliesTransMetric.dayArticles"));
		pongoCollections.add(dayArticles);
	}
}