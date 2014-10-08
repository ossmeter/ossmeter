package org.ossmeter.metricprovider.historic.newsgroups.threads.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.threads.model.DailyNewsgroupData");
		NUMBEROFTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.threads.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFTHREADS = new NumericalQueryProducer("numberOfThreads");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfThreads() {
		return parseInteger(dbObject.get("numberOfThreads")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfThreads(int numberOfThreads) {
		dbObject.put("numberOfThreads", numberOfThreads);
		notifyChanged();
		return this;
	}
	
	
	
	
}