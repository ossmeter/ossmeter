package org.ossmeter.metricprovider.trans.hourlycommits.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class HourlyCommits extends PongoDB {
	
	public HourlyCommits() {}
	
	public HourlyCommits(DB db) {
		setDb(db);
	}
	
	protected HourCollection hours = null;
	
	
	
	public HourCollection getHours() {
		return hours;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		hours = new HourCollection(db.getCollection("HourlyCommits.hours"));
		pongoCollections.add(hours);
	}
}