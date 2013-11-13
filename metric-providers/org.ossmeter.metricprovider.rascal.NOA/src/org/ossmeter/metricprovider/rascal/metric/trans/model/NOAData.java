package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NOAData extends Pongo {
	
	
	
	public NOAData() { 
		super();
		CLASSNAME.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NOAData");
		ATTRIBUTES.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NOAData");
		REVISIONNUMBER.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NOAData");
		DATE.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NOAData");
	}
	
	public static StringQueryProducer CLASSNAME = new StringQueryProducer("className"); 
	public static NumericalQueryProducer ATTRIBUTES = new NumericalQueryProducer("attributes");
	public static StringQueryProducer REVISIONNUMBER = new StringQueryProducer("revisionNumber"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getClassName() {
		return parseString(dbObject.get("className")+"", "");
	}
	
	public NOAData setClassName(String className) {
		dbObject.put("className", className);
		notifyChanged();
		return this;
	}
	public long getAttributes() {
		return parseLong(dbObject.get("attributes")+"", 0);
	}
	
	public NOAData setAttributes(long attributes) {
		dbObject.put("attributes", attributes);
		notifyChanged();
		return this;
	}
	public String getRevisionNumber() {
		return parseString(dbObject.get("revisionNumber")+"", "");
	}
	
	public NOAData setRevisionNumber(String revisionNumber) {
		dbObject.put("revisionNumber", revisionNumber);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public NOAData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}