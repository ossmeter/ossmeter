package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DayRequests extends Pongo {
	
	
	
	public DayRequests() { 
		super();
		NAME.setOwningType("org.ossmeter.metricprovider.trans.dailyrequestsreplies.model.DayRequests");
		NUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.trans.dailyrequestsreplies.model.DayRequests");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public DayRequests setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public DayRequests setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	
	
	
	
}