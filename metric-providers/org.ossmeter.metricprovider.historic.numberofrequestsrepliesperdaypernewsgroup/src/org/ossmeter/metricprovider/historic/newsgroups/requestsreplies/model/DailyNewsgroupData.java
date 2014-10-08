package org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.DailyNewsgroupData");
		NUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.DailyNewsgroupData");
		NUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.DailyNewsgroupData");
		CUMULATIVENUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.DailyNewsgroupData");
		CUMULATIVENUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.requestsreplies.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREQUESTS = new NumericalQueryProducer("cumulativeNumberOfRequests");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREPLIES = new NumericalQueryProducer("cumulativeNumberOfReplies");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfRequests() {
		return parseInteger(dbObject.get("cumulativeNumberOfRequests")+"", 0);
	}
	
	public DailyNewsgroupData setCumulativeNumberOfRequests(int cumulativeNumberOfRequests) {
		dbObject.put("cumulativeNumberOfRequests", cumulativeNumberOfRequests);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfReplies() {
		return parseInteger(dbObject.get("cumulativeNumberOfReplies")+"", 0);
	}
	
	public DailyNewsgroupData setCumulativeNumberOfReplies(int cumulativeNumberOfReplies) {
		dbObject.put("cumulativeNumberOfReplies", cumulativeNumberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}