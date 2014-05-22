package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyNewsgroupRequestsData extends Pongo {
	
	
	
	public DailyNewsgroupRequestsData() { 
		super();
		NUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.model.DailyNewsgroupRequestsData");
	}
	
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