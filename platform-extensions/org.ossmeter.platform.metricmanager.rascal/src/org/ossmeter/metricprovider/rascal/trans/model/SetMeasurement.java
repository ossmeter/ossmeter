package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class SetMeasurement extends PongoDB {
	
	public SetMeasurement() {}
	
	public SetMeasurement(DB db) {
		setDb(db);
	}
	
	protected MeasurementCollection value = null;
	
	
	
	public MeasurementCollection getValue() {
		return value;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		value = new MeasurementCollection(db.getCollection("SetMeasurement.value"));
		pongoCollections.add(value);
	}
}