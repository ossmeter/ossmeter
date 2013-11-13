package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class WMCData extends Pongo {
	
	
	
	public WMCData() { 
		super();
		CLASSNAME.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.WMCData");
		WEIGHTEDCOUNT.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.WMCData");
		REVISIONNUMBER.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.WMCData");
		DATE.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.WMCData");
	}
	
	public static StringQueryProducer CLASSNAME = new StringQueryProducer("className"); 
	public static NumericalQueryProducer WEIGHTEDCOUNT = new NumericalQueryProducer("weightedCount");
	public static StringQueryProducer REVISIONNUMBER = new StringQueryProducer("revisionNumber"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getClassName() {
		return parseString(dbObject.get("className")+"", "");
	}
	
	public WMCData setClassName(String className) {
		dbObject.put("className", className);
		notifyChanged();
		return this;
	}
	public long getWeightedCount() {
		return parseLong(dbObject.get("weightedCount")+"", 0);
	}
	
	public WMCData setWeightedCount(long weightedCount) {
		dbObject.put("weightedCount", weightedCount);
		notifyChanged();
		return this;
	}
	public String getRevisionNumber() {
		return parseString(dbObject.get("revisionNumber")+"", "");
	}
	
	public WMCData setRevisionNumber(String revisionNumber) {
		dbObject.put("revisionNumber", revisionNumber);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public WMCData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}