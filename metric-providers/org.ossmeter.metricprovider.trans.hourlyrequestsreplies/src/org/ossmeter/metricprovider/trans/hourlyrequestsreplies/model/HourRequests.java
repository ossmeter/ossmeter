package org.ossmeter.metricprovider.trans.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class HourRequests extends Pongo {
	
	
	
	public HourRequests() { 
		super();
		HOUR.setOwningType("org.ossmeter.metricprovider.trans.hourlyrequestsreplies.model.HourRequests");
		NUMBEROFREQUESTS.setOwningType("org.ossmeter.metricprovider.trans.hourlyrequestsreplies.model.HourRequests");
	}
	
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	
	
	public String getHour() {
		return parseString(dbObject.get("hour")+"", "");
	}
	
	public HourRequests setHour(String hour) {
		dbObject.put("hour", hour);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public HourRequests setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	
	
	
	
}