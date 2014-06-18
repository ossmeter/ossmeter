package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BooleanMeasurement extends Measurement {
	
	
	
	public BooleanMeasurement() { 
		super();
		super.setSuperTypes("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement");
		VALUE.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.BooleanMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static StringQueryProducer VALUE = new StringQueryProducer("value"); 
	
	
	public boolean getValue() {
		return parseBoolean(dbObject.get("value")+"", false);
	}
	
	public BooleanMeasurement setValue(boolean value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}