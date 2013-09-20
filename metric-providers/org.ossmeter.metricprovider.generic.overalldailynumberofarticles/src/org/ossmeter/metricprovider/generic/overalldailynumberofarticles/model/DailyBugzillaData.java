package org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model;

import com.googlecode.pongo.runtime.Pongo;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
	}
	
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}