package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NumberOfMethodsData extends Pongo {
	
	
	
	public NumberOfMethodsData() { 
		super();
		CLASSNAME.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NumberOfMethodsData");
		NOM.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NumberOfMethodsData");
		REVISIONNUMBER.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NumberOfMethodsData");
		DATE.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.NumberOfMethodsData");
	}
	
	public static StringQueryProducer CLASSNAME = new StringQueryProducer("className"); 
	public static NumericalQueryProducer NOM = new NumericalQueryProducer("NOM");
	public static StringQueryProducer REVISIONNUMBER = new StringQueryProducer("revisionNumber"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getClassName() {
		return parseString(dbObject.get("className")+"", "");
	}
	
	public NumberOfMethodsData setClassName(String className) {
		dbObject.put("className", className);
		notifyChanged();
		return this;
	}
	public long getNOM() {
		return parseLong(dbObject.get("NOM")+"", 0);
	}
	
	public NumberOfMethodsData setNOM(long NOM) {
		dbObject.put("NOM", NOM);
		notifyChanged();
		return this;
	}
	public String getRevisionNumber() {
		return parseString(dbObject.get("revisionNumber")+"", "");
	}
	
	public NumberOfMethodsData setRevisionNumber(String revisionNumber) {
		dbObject.put("revisionNumber", revisionNumber);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public NumberOfMethodsData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}