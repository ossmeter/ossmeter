package org.ossmeter.metricprovider.historic.avgnumberofrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class AverageRR extends Pongo {
	
	
	
	public AverageRR() { 
		super();
		AVERAGEARTICLES.setOwningType("org.ossmeter.metricprovider.historic.avgnumberofrequestsreplies.model.AverageRR");
		AVERAGEREQUESTS.setOwningType("org.ossmeter.metricprovider.historic.avgnumberofrequestsreplies.model.AverageRR");
		AVERAGEREPLIES.setOwningType("org.ossmeter.metricprovider.historic.avgnumberofrequestsreplies.model.AverageRR");
	}
	
	public static NumericalQueryProducer AVERAGEARTICLES = new NumericalQueryProducer("averageArticles");
	public static NumericalQueryProducer AVERAGEREQUESTS = new NumericalQueryProducer("averageRequests");
	public static NumericalQueryProducer AVERAGEREPLIES = new NumericalQueryProducer("averageReplies");
	
	
	public float getAverageArticles() {
		return parseFloat(dbObject.get("averageArticles")+"", 0.0f);
	}
	
	public AverageRR setAverageArticles(float averageArticles) {
		dbObject.put("averageArticles", averageArticles);
		notifyChanged();
		return this;
	}
	public float getAverageRequests() {
		return parseFloat(dbObject.get("averageRequests")+"", 0.0f);
	}
	
	public AverageRR setAverageRequests(float averageRequests) {
		dbObject.put("averageRequests", averageRequests);
		notifyChanged();
		return this;
	}
	public float getAverageReplies() {
		return parseFloat(dbObject.get("averageReplies")+"", 0.0f);
	}
	
	public AverageRR setAverageReplies(float averageReplies) {
		dbObject.put("averageReplies", averageReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}