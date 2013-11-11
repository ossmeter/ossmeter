package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class RascalMetric extends PongoDB {
	
	public RascalMetric() {}
	
	public RascalMetric(DB db) {
		setDb(db);
	}
	
	
	
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
	}
}