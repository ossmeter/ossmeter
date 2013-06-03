package org.ossmeter.metricprovider.rascal.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class IntegerMeasurement extends Measurement {
	
	
	
	public IntegerMeasurement() { 
		super();
	}
	
	public long getValue() {
		return parseLong(dbObject.get("value")+"", 0);
	}
	
	public IntegerMeasurement setValue(long value) {
		dbObject.put("value", value + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}