package org.ossmeter.metricprovider.historic.newsgroups.newthreads.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsNewThreadsHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public NewsgroupsNewThreadsHistoricMetric() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
		NUMBEROFNEWTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric");
		CUMULATIVENUMBEROFNEWTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFNEWTHREADS = new NumericalQueryProducer("numberOfNewThreads");
	public static NumericalQueryProducer CUMULATIVENUMBEROFNEWTHREADS = new NumericalQueryProducer("cumulativeNumberOfNewThreads");
	
	
	public int getNumberOfNewThreads() {
		return parseInteger(dbObject.get("numberOfNewThreads")+"", 0);
	}
	
	public NewsgroupsNewThreadsHistoricMetric setNumberOfNewThreads(int numberOfNewThreads) {
		dbObject.put("numberOfNewThreads", numberOfNewThreads);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfNewThreads() {
		return parseInteger(dbObject.get("cumulativeNumberOfNewThreads")+"", 0);
	}
	
	public NewsgroupsNewThreadsHistoricMetric setCumulativeNumberOfNewThreads(int cumulativeNumberOfNewThreads) {
		dbObject.put("cumulativeNumberOfNewThreads", cumulativeNumberOfNewThreads);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyNewsgroupData> getNewsgroups() {
		if (newsgroups == null) {
			newsgroups = new PongoList<DailyNewsgroupData>(this, "newsgroups", true);
		}
		return newsgroups;
	}
	
	
}