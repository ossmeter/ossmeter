package org.ossmeter.metricprovider.historic.newsgroups.severity.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severity.model.NewsgroupData");
		NUMBEROFTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severity.model.NewsgroupData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer NUMBEROFTHREADS = new NumericalQueryProducer("numberOfThreads");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public NewsgroupData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public int getNumberOfThreads() {
		return parseInteger(dbObject.get("numberOfThreads")+"", 0);
	}
	
	public NewsgroupData setNumberOfThreads(int numberOfThreads) {
		dbObject.put("numberOfThreads", numberOfThreads);
		notifyChanged();
		return this;
	}
	
	
	
	
}