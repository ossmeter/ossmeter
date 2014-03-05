package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class RascalMetrics extends PongoDB {
	
	public RascalMetrics() {}
	
	public RascalMetrics(DB db) {
		setDb(db);
	}
	
	protected MeasurementCollection measurements = null;
	
	// protected region custom-fields-and-methods on begin
	public void setMeasurementsCollectionName(String newName) {
		measurements.getDbCollection().rename(newName);
	}
	// protected region custom-fields-and-methods end
	
	
	public MeasurementCollection getMeasurements() {
		return measurements;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		measurements = new MeasurementCollection(db.getCollection("RascalMetrics.measurements"));
		pongoCollections.add(measurements);
	}
}