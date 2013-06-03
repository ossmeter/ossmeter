package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class RascalMetrics extends PongoDB {
	
	public RascalMetrics() {}
	
	public RascalMetrics(DB db) {
		setDb(db);
	}
	
	protected MeasurementCollection measurements = null;
	
	
	
	public MeasurementCollection getMeasurements() {
		return measurements;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		measurements = new MeasurementCollection(db.getCollection("measurements"));
		pongoCollections.add(measurements);
	}
}