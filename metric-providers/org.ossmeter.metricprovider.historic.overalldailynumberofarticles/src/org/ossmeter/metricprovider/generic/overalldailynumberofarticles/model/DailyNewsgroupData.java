package org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		NUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model.DailyNewsgroupData");
	}
	
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	
	
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	
	
	
	
}