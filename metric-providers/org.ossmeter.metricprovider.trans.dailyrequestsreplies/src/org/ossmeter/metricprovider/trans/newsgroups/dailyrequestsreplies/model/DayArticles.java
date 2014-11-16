package org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DayArticles extends Pongo {
	
	
	
	public DayArticles() { 
		super();
		NAME.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles");
		NUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles");
		NUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles");
		NUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles");
		PERCENTAGEOFARTICLES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles");
		PERCENTAGEOFREQUESTS.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles");
		PERCENTAGEOFREPLIES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	public static NumericalQueryProducer PERCENTAGEOFARTICLES = new NumericalQueryProducer("percentageOfArticles");
	public static NumericalQueryProducer PERCENTAGEOFREQUESTS = new NumericalQueryProducer("percentageOfRequests");
	public static NumericalQueryProducer PERCENTAGEOFREPLIES = new NumericalQueryProducer("percentageOfReplies");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public DayArticles setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public DayArticles setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public DayArticles setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public DayArticles setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	public float getPercentageOfArticles() {
		return parseFloat(dbObject.get("percentageOfArticles")+"", 0.0f);
	}
	
	public DayArticles setPercentageOfArticles(float percentageOfArticles) {
		dbObject.put("percentageOfArticles", percentageOfArticles);
		notifyChanged();
		return this;
	}
	public float getPercentageOfRequests() {
		return parseFloat(dbObject.get("percentageOfRequests")+"", 0.0f);
	}
	
	public DayArticles setPercentageOfRequests(float percentageOfRequests) {
		dbObject.put("percentageOfRequests", percentageOfRequests);
		notifyChanged();
		return this;
	}
	public float getPercentageOfReplies() {
		return parseFloat(dbObject.get("percentageOfReplies")+"", 0.0f);
	}
	
	public DayArticles setPercentageOfReplies(float percentageOfReplies) {
		dbObject.put("percentageOfReplies", percentageOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}