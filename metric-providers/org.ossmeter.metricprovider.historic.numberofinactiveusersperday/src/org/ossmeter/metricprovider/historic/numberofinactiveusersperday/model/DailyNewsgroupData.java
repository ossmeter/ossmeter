package org.ossmeter.metricprovider.historic.numberofinactiveusersperday.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		NUMBEROFINACTIVEUSERS.setOwningType("org.ossmeter.metricprovider.historic.numberofinactiveusersperday.model.DailyNewsgroupData");
	}
	
	public static NumericalQueryProducer NUMBEROFINACTIVEUSERS = new NumericalQueryProducer("numberOfInactiveUsers");
	
	
	public int getNumberOfInactiveUsers() {
		return parseInteger(dbObject.get("numberOfInactiveUsers")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfInactiveUsers(int numberOfInactiveUsers) {
		dbObject.put("numberOfInactiveUsers", numberOfInactiveUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}