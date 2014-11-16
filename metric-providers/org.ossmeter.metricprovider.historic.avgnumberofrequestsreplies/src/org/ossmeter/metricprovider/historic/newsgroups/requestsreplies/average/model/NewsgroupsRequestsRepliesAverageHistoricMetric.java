package org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class NewsgroupsRequestsRepliesAverageHistoricMetric extends Pongo {
	
	
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric() { 
		super();
		AVERAGEARTICLESPERDAY.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric");
		AVERAGEREQUESTSPERDAY.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric");
		AVERAGEREPLIESPERDAY.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric");
	}
	
	public static NumericalQueryProducer AVERAGEARTICLESPERDAY = new NumericalQueryProducer("averageArticlesPerDay");
	public static NumericalQueryProducer AVERAGEREQUESTSPERDAY = new NumericalQueryProducer("averageRequestsPerDay");
	public static NumericalQueryProducer AVERAGEREPLIESPERDAY = new NumericalQueryProducer("averageRepliesPerDay");
	
	
	public float getAverageArticlesPerDay() {
		return parseFloat(dbObject.get("averageArticlesPerDay")+"", 0.0f);
	}
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric setAverageArticlesPerDay(float averageArticlesPerDay) {
		dbObject.put("averageArticlesPerDay", averageArticlesPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRequestsPerDay() {
		return parseFloat(dbObject.get("averageRequestsPerDay")+"", 0.0f);
	}
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric setAverageRequestsPerDay(float averageRequestsPerDay) {
		dbObject.put("averageRequestsPerDay", averageRequestsPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRepliesPerDay() {
		return parseFloat(dbObject.get("averageRepliesPerDay")+"", 0.0f);
	}
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric setAverageRepliesPerDay(float averageRepliesPerDay) {
		dbObject.put("averageRepliesPerDay", averageRepliesPerDay);
		notifyChanged();
		return this;
	}
	
	
	
	
}