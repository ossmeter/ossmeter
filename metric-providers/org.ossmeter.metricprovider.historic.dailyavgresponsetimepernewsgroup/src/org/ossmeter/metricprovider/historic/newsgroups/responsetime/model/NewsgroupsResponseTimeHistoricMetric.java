package org.ossmeter.metricprovider.historic.newsgroups.responsetime.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupsResponseTimeHistoricMetric extends Pongo {
	
	
	
	public NewsgroupsResponseTimeHistoricMetric() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		AVGRESPONSETIME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		THREADSCONSIDERED.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		CUMULATIVEAVGRESPONSETIME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		CUMULATIVETHREADSCONSIDERED.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static StringQueryProducer AVGRESPONSETIME = new StringQueryProducer("avgResponseTime"); 
	public static NumericalQueryProducer THREADSCONSIDERED = new NumericalQueryProducer("threadsConsidered");
	public static StringQueryProducer CUMULATIVEAVGRESPONSETIME = new StringQueryProducer("cumulativeAvgResponseTime"); 
	public static NumericalQueryProducer CUMULATIVETHREADSCONSIDERED = new NumericalQueryProducer("cumulativeThreadsConsidered");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public NewsgroupsResponseTimeHistoricMetric setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public String getAvgResponseTime() {
		return parseString(dbObject.get("avgResponseTime")+"", "");
	}
	
	public NewsgroupsResponseTimeHistoricMetric setAvgResponseTime(String avgResponseTime) {
		dbObject.put("avgResponseTime", avgResponseTime);
		notifyChanged();
		return this;
	}
	public int getThreadsConsidered() {
		return parseInteger(dbObject.get("threadsConsidered")+"", 0);
	}
	
	public NewsgroupsResponseTimeHistoricMetric setThreadsConsidered(int threadsConsidered) {
		dbObject.put("threadsConsidered", threadsConsidered);
		notifyChanged();
		return this;
	}
	public String getCumulativeAvgResponseTime() {
		return parseString(dbObject.get("cumulativeAvgResponseTime")+"", "");
	}
	
	public NewsgroupsResponseTimeHistoricMetric setCumulativeAvgResponseTime(String cumulativeAvgResponseTime) {
		dbObject.put("cumulativeAvgResponseTime", cumulativeAvgResponseTime);
		notifyChanged();
		return this;
	}
	public int getCumulativeThreadsConsidered() {
		return parseInteger(dbObject.get("cumulativeThreadsConsidered")+"", 0);
	}
	
	public NewsgroupsResponseTimeHistoricMetric setCumulativeThreadsConsidered(int cumulativeThreadsConsidered) {
		dbObject.put("cumulativeThreadsConsidered", cumulativeThreadsConsidered);
		notifyChanged();
		return this;
	}
	
	
	
	
}