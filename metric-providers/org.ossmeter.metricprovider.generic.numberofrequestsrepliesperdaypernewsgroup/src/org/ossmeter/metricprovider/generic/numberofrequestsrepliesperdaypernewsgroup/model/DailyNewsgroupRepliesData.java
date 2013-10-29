package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupRepliesData extends DailyNewsgroupData {
	
	
	
	public DailyNewsgroupRepliesData() { 
		super();
		super.setSuperTypes("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupData");
		URL_NAME.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupRepliesData");
		NUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupRepliesData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	
	
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public DailyNewsgroupRepliesData setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}