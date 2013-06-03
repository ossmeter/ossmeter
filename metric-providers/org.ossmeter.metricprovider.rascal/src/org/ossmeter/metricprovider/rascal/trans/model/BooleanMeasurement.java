package org.ossmeter.metricprovider.rascal.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class BooleanMeasurement extends Measurement {
	
	
	
	public BooleanMeasurement() { 
		super();
	}
	
	public boolean getValue() {
		return parseBoolean(dbObject.get("value")+"", false);
	}
	
	public BooleanMeasurement setValue(boolean value) {
		dbObject.put("value", value + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}