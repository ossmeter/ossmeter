package org.ossmeter.metricprovider.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class HourArticles extends Pongo {
	
	
	
	public HourArticles() { 
		super();
		HOUR.setOwningType("org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourArticles");
		NUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourArticles");
	}
	
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	
	
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
	
	
	
	
}