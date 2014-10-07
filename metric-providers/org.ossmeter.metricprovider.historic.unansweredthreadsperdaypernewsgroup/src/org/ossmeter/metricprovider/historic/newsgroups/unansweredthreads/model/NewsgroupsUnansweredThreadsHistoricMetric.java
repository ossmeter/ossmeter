package org.ossmeter.metricprovider.historic.newsgroups.unansweredthreads.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class NewsgroupsUnansweredThreadsHistoricMetric extends Pongo {
	
	
	
	public NewsgroupsUnansweredThreadsHistoricMetric() { 
		super();
		NUMBEROFUNANSWEREDTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.unansweredthreads.model.NewsgroupsUnansweredThreadsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFUNANSWEREDTHREADS = new NumericalQueryProducer("numberOfUnansweredThreads");
	
	
	public int getNumberOfUnansweredThreads() {
		return parseInteger(dbObject.get("numberOfUnansweredThreads")+"", 0);
	}
	
	public NewsgroupsUnansweredThreadsHistoricMetric setNumberOfUnansweredThreads(int numberOfUnansweredThreads) {
		dbObject.put("numberOfUnansweredThreads", numberOfUnansweredThreads);
		notifyChanged();
		return this;
	}
	
	
	
	
}