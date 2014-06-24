package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DatetimeMeasurement extends Measurement {
	
	
	
	public DatetimeMeasurement() { 
		super();
		super.setSuperTypes("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.DatetimeMeasurement");
		VALUE.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.DatetimeMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer("value");
	
	
	public long getValue() {
		return parseLong(dbObject.get("value")+"", 0);
	}
	
	public DatetimeMeasurement setValue(long value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}