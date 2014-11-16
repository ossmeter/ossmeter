package org.ossmeter.metricprovider.trans.bugs.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsHourlyRequestsRepliesTransMetric extends PongoDB {
	
	public BugsHourlyRequestsRepliesTransMetric() {}
	
	public BugsHourlyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected HourCommentsCollection hourComments = null;
	
	
	
	public HourCommentsCollection getHourComments() {
		return hourComments;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		hourComments = new HourCommentsCollection(db.getCollection("BugsHourlyRequestsRepliesTransMetric.hourComments"));
		pongoCollections.add(hourComments);
	}
}