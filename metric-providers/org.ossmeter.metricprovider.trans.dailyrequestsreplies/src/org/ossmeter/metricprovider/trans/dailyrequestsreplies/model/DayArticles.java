package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DayArticles extends Pongo {
	
	
	
	public DayArticles() { 
		super();
		NAME.setOwningType("org.ossmeter.metricprovider.trans.dailyrequestsreplies.model.DayArticles");
		NUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.trans.dailyrequestsreplies.model.DayArticles");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	
	
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
	
	
	
	
}