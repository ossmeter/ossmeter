package org.ossmeter.metricprovider.rascal.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class Measurement extends Pongo {
	
	
	
	public Measurement() { 
		super();
	}
	
	public String getUri() {
		return parseString(dbObject.get("uri")+"", "");
	}
	
	public Measurement setUri(String uri) {
		dbObject.put("uri", uri + "");
		notifyChanged();
		return this;
	}
	public String getMetric() {
		return parseString(dbObject.get("metric")+"", "");
	}
	
	public Measurement setMetric(String metric) {
		dbObject.put("metric", metric + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}