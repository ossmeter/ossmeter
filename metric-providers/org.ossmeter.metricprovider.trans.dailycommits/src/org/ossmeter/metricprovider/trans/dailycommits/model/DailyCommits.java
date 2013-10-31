package org.ossmeter.metricprovider.trans.dailycommits.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class DailyCommits extends PongoDB {
	
	public DailyCommits() {}
	
	public DailyCommits(DB db) {
		setDb(db);
	}
	
	protected DayCollection days = null;
	
	
	
	public DayCollection getDays() {
		return days;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		days = new DayCollection(db.getCollection("DailyCommits.days"));
		pongoCollections.add(days);
	}
}