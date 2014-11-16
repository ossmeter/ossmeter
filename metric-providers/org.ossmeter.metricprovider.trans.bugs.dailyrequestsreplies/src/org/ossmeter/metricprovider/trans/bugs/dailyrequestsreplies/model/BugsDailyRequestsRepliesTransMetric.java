package org.ossmeter.metricprovider.trans.bugs.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsDailyRequestsRepliesTransMetric extends PongoDB {
	
	public BugsDailyRequestsRepliesTransMetric() {}
	
	public BugsDailyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected DayCommentsCollection dayComments = null;
	
	
	
	public DayCommentsCollection getDayComments() {
		return dayComments;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dayComments = new DayCommentsCollection(db.getCollection("BugsDailyRequestsRepliesTransMetric.dayComments"));
		pongoCollections.add(dayComments);
	}
}