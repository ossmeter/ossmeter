package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class CCData extends Pongo {
	
	
	
	public CCData() { 
		super();
		FILEURL.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.CCData");
		METHODNAME.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.CCData");
		CCVALUE.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.CCData");
		REVISIONNUMBER.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.CCData");
		DATE.setOwningType("org.ossmeter.metricprovider.rascal.metric.trans.model.CCData");
	}
	
	public static StringQueryProducer FILEURL = new StringQueryProducer("fileURL"); 
	public static StringQueryProducer METHODNAME = new StringQueryProducer("methodName"); 
	public static NumericalQueryProducer CCVALUE = new NumericalQueryProducer("ccValue");
	public static StringQueryProducer REVISIONNUMBER = new StringQueryProducer("revisionNumber"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getFileURL() {
		return parseString(dbObject.get("fileURL")+"", "");
	}
	
	public CCData setFileURL(String fileURL) {
		dbObject.put("fileURL", fileURL);
		notifyChanged();
		return this;
	}
	public String getMethodName() {
		return parseString(dbObject.get("methodName")+"", "");
	}
	
	public CCData setMethodName(String methodName) {
		dbObject.put("methodName", methodName);
		notifyChanged();
		return this;
	}
	public long getCcValue() {
		return parseLong(dbObject.get("ccValue")+"", 0);
	}
	
	public CCData setCcValue(long ccValue) {
		dbObject.put("ccValue", ccValue);
		notifyChanged();
		return this;
	}
	public String getRevisionNumber() {
		return parseString(dbObject.get("revisionNumber")+"", "");
	}
	
	public CCData setRevisionNumber(String revisionNumber) {
		dbObject.put("revisionNumber", revisionNumber);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public CCData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}