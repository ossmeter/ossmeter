package org.ossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class HourArticles extends Pongo {
	
	
	
	public HourArticles() { 
		super();
		HOUR.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		NUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		NUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		NUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
	}
	
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	
	
	public String getHour() {
		return parseString(dbObject.get("hour")+"", "");
	}
	
	public HourArticles setHour(String hour) {
		dbObject.put("hour", hour);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public HourArticles setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public HourArticles setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public HourArticles setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}