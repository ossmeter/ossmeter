package org.ossmeter.metricprovider.historic.numberofactiveusersperday.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyActiveUsers extends Pongo {
	
	
	
	public DailyActiveUsers() { 
		super();
		NUMBEROFACTIVEUSERS.setOwningType("org.ossmeter.metricprovider.historic.numberofactiveusersperday.model.DailyActiveUsers");
	}
	
	public static NumericalQueryProducer NUMBEROFACTIVEUSERS = new NumericalQueryProducer("numberOfActiveUsers");
	
	
	public int getNumberOfActiveUsers() {
		return parseInteger(dbObject.get("numberOfActiveUsers")+"", 0);
	}
	
	public DailyActiveUsers setNumberOfActiveUsers(int numberOfActiveUsers) {
		dbObject.put("numberOfActiveUsers", numberOfActiveUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}