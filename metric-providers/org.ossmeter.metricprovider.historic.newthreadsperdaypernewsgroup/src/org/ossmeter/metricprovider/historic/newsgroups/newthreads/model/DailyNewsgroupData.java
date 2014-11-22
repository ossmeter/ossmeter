package org.ossmeter.metricprovider.historic.newsgroups.newthreads.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData");
		NUMBEROFNEWTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData");
		CUMULATIVENUMBEROFNEWTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer NUMBEROFNEWTHREADS = new NumericalQueryProducer("numberOfNewThreads");
	public static NumericalQueryProducer CUMULATIVENUMBEROFNEWTHREADS = new NumericalQueryProducer("cumulativeNumberOfNewThreads");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public DailyNewsgroupData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getNumberOfNewThreads() {
		return parseInteger(dbObject.get("numberOfNewThreads")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfNewThreads(int numberOfNewThreads) {
		dbObject.put("numberOfNewThreads", numberOfNewThreads);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfNewThreads() {
		return parseInteger(dbObject.get("cumulativeNumberOfNewThreads")+"", 0);
	}
	
	public DailyNewsgroupData setCumulativeNumberOfNewThreads(int cumulativeNumberOfNewThreads) {
		dbObject.put("cumulativeNumberOfNewThreads", cumulativeNumberOfNewThreads);
		notifyChanged();
		return this;
	}
	
	
	
	
}