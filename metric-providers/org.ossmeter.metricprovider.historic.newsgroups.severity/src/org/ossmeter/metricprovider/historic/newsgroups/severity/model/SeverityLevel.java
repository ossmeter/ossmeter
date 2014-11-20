package org.ossmeter.metricprovider.historic.newsgroups.severity.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SeverityLevel extends Pongo {
	
	
	
	public SeverityLevel() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severity.model.SeverityLevel");
		SEVERITYLEVEL.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severity.model.SeverityLevel");
		NUMBEROFTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severity.model.SeverityLevel");
		PERCENTAGE.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severity.model.SeverityLevel");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer SEVERITYLEVEL = new StringQueryProducer("severityLevel"); 
	public static NumericalQueryProducer NUMBEROFTHREADS = new NumericalQueryProducer("numberOfThreads");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public SeverityLevel setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getSeverityLevel() {
		return parseString(dbObject.get("severityLevel")+"", "");
	}
	
	public SeverityLevel setSeverityLevel(String severityLevel) {
		dbObject.put("severityLevel", severityLevel);
		notifyChanged();
		return this;
	}
	public int getNumberOfThreads() {
		return parseInteger(dbObject.get("numberOfThreads")+"", 0);
	}
	
	public SeverityLevel setNumberOfThreads(int numberOfThreads) {
		dbObject.put("numberOfThreads", numberOfThreads);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public SeverityLevel setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}