package org.ossmeter.metricprovider.rascal.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RealMeasurement extends Measurement {
	
	
	
	public RealMeasurement() { 
		super();
		super.setSuperTypes("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement");
		VALUE.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.RealMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer("value");
	
	
	public float getValue() {
		return parseFloat(dbObject.get("value")+"", 0.0f);
	}
	
	public RealMeasurement setValue(float value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}