package org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class BugsRequestsRepliesHistoricMetric extends Pongo {
	
	
	
	public BugsRequestsRepliesHistoricMetric() { 
		super();
		AVERAGEARTICLESPERDAY.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
		AVERAGEREQUESTSPERDAY.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
		AVERAGEREPLIESPERDAY.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
		AVERAGEARTICLESPERUSER.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
		AVERAGEREQUESTSPERUSER.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
		AVERAGEREPLIESPERUSER.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
	}
	
	public static NumericalQueryProducer AVERAGEARTICLESPERDAY = new NumericalQueryProducer("averageArticlesPerDay");
	public static NumericalQueryProducer AVERAGEREQUESTSPERDAY = new NumericalQueryProducer("averageRequestsPerDay");
	public static NumericalQueryProducer AVERAGEREPLIESPERDAY = new NumericalQueryProducer("averageRepliesPerDay");
	public static NumericalQueryProducer AVERAGEARTICLESPERUSER = new NumericalQueryProducer("averageArticlesPerUser");
	public static NumericalQueryProducer AVERAGEREQUESTSPERUSER = new NumericalQueryProducer("averageRequestsPerUser");
	public static NumericalQueryProducer AVERAGEREPLIESPERUSER = new NumericalQueryProducer("averageRepliesPerUser");
	
	
	public float getAverageArticlesPerDay() {
		return parseFloat(dbObject.get("averageArticlesPerDay")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageArticlesPerDay(float averageArticlesPerDay) {
		dbObject.put("averageArticlesPerDay", averageArticlesPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRequestsPerDay() {
		return parseFloat(dbObject.get("averageRequestsPerDay")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageRequestsPerDay(float averageRequestsPerDay) {
		dbObject.put("averageRequestsPerDay", averageRequestsPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRepliesPerDay() {
		return parseFloat(dbObject.get("averageRepliesPerDay")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageRepliesPerDay(float averageRepliesPerDay) {
		dbObject.put("averageRepliesPerDay", averageRepliesPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageArticlesPerUser() {
		return parseFloat(dbObject.get("averageArticlesPerUser")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageArticlesPerUser(float averageArticlesPerUser) {
		dbObject.put("averageArticlesPerUser", averageArticlesPerUser);
		notifyChanged();
		return this;
	}
	public float getAverageRequestsPerUser() {
		return parseFloat(dbObject.get("averageRequestsPerUser")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageRequestsPerUser(float averageRequestsPerUser) {
		dbObject.put("averageRequestsPerUser", averageRequestsPerUser);
		notifyChanged();
		return this;
	}
	public float getAverageRepliesPerUser() {
		return parseFloat(dbObject.get("averageRepliesPerUser")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageRepliesPerUser(float averageRepliesPerUser) {
		dbObject.put("averageRepliesPerUser", averageRepliesPerUser);
		notifyChanged();
		return this;
	}
	
	
	
	
}