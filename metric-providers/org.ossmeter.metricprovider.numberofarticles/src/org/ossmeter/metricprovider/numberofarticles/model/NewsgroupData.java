package org.ossmeter.metricprovider.numberofarticles.model;

import com.googlecode.pongo.runtime.Pongo;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
	}
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public NewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name + "");
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public NewsgroupData setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}