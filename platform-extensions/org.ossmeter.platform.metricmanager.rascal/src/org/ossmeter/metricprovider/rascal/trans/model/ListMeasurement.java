package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ListMeasurement extends PongoDB {
	
	public ListMeasurement() {}
	
	public ListMeasurement(DB db) {
		setDb(db);
	}
	
	protected MeasurementCollection value = null;
	
	
	
	public MeasurementCollection getValue() {
		return value;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		value = new MeasurementCollection(db.getCollection("ListMeasurement.value"));
		pongoCollections.add(value);
	}
}