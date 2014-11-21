package org.ossmeter.metricprovider.historic.newsgroups.threads.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.threads.model.DailyNewsgroupData");
		NUMBEROFTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.threads.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer NUMBEROFTHREADS = new NumericalQueryProducer("numberOfThreads");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public DailyNewsgroupData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
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