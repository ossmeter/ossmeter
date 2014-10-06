package org.ossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsHourlyRequestsRepliesTransMetric extends PongoDB {
	
	public NewsgroupsHourlyRequestsRepliesTransMetric() {}
	
	public NewsgroupsHourlyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected HourArticlesCollection hourArticles = null;
	
	
	
	public HourArticlesCollection getHourArticles() {
		return hourArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		hourArticles = new HourArticlesCollection(db.getCollection("NewsgroupsHourlyRequestsRepliesTransMetric.hourArticles"));
		pongoCollections.add(hourArticles);
	}
}