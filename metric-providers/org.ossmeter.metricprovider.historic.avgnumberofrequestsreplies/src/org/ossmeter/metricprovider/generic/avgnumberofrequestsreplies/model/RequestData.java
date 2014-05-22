package org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class RequestData extends Pongo {
	
	
	
	public RequestData() { 
		super();
		AVERAGEREQUESTS.setOwningType("org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model.RequestData");
	}
	
	public static NumericalQueryProducer AVERAGEREQUESTS = new NumericalQueryProducer("averageRequests");
	
	
	public float getAverageRequests() {
		return parseFloat(dbObject.get("averageRequests")+"", 0.0f);
	}
	
	public RequestData setAverageRequests(float averageRequests) {
		dbObject.put("averageRequests", averageRequests);
		notifyChanged();
		return this;
	}
	
	
	
	
}