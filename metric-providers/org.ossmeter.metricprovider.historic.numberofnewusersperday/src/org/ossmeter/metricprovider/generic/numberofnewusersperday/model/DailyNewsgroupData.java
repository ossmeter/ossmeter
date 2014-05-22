package org.ossmeter.metricprovider.generic.numberofnewusersperday.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		NUMBEROFNEWUSERS.setOwningType("org.ossmeter.metricprovider.generic.numberofnewusersperday.model.DailyNewsgroupData");
	}
	
	public static NumericalQueryProducer NUMBEROFNEWUSERS = new NumericalQueryProducer("numberOfNewUsers");
	
	
	public int getNumberOfNewUsers() {
		return parseInteger(dbObject.get("numberOfNewUsers")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfNewUsers(int numberOfNewUsers) {
		dbObject.put("numberOfNewUsers", numberOfNewUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}