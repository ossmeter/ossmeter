package org.ossmeter.metricprovider.rascal.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Measurement extends Pongo {
	
	
	
	public Measurement() { 
		super();
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		DATE.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getUri() {
		return parseString(dbObject.get("uri")+"", "");
	}
	
	public Measurement setUri(String uri) {
		dbObject.put("uri", uri);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public Measurement setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}