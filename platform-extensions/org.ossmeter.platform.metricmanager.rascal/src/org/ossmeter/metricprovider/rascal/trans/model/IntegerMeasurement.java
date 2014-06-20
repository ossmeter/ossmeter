package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class IntegerMeasurement extends Measurement {
	
	
	
	public IntegerMeasurement() { 
		super();
		super.setSuperTypes("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement");
		VALUE.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer("value");
	
	
	public long getValue() {
		return parseLong(dbObject.get("value")+"", 0);
	}
	
	public IntegerMeasurement setValue(long value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}