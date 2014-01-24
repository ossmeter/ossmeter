package org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class ArticleData extends Pongo {
	
	
	
	public ArticleData() { 
		super();
		AVERAGEARTICLES.setOwningType("org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model.ArticleData");
	}
	
	public static NumericalQueryProducer AVERAGEARTICLES = new NumericalQueryProducer("averageArticles");
	
	
	public float getAverageArticles() {
		return parseFloat(dbObject.get("averageArticles")+"", 0.0f);
	}
	
	public ArticleData setAverageArticles(float averageArticles) {
		dbObject.put("averageArticles", averageArticles);
		notifyChanged();
		return this;
	}
	
	
	
	
}