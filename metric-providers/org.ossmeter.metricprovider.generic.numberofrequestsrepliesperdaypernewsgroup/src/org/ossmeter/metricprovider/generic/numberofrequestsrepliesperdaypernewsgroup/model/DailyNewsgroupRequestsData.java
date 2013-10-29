package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupRequestsData extends DailyNewsgroupData {
	
	
	
	public DailyNewsgroupRequestsData() { 
		super();
		super.setSuperTypes("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupData");
		URL_NAME.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupRequestsData");
		NUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupRequestsData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	
	
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public DailyNewsgroupRequestsData setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	
	
	
	
}