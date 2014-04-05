package org.ossmeter.metricprovider.rascal.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class StringMeasurement extends Measurement {
	
	
	
	public StringMeasurement() { 
		super();
		super.setSuperTypes("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement");
		DATE.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement");
		VALUE.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.StringMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer VALUE = new StringQueryProducer("value"); 
	
	
	public String getValue() {
		return parseString(dbObject.get("value")+"", "");
	}
	
	public StringMeasurement setValue(String value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}