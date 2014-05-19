package org.ossmeter.metricprovider.historic.numberofusersperday.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		NUMBEROFUSERS.setOwningType("org.ossmeter.metricprovider.historic.numberofusersperday.model.DailyNewsgroupData");
	}
	
	public static NumericalQueryProducer NUMBEROFUSERS = new NumericalQueryProducer("numberOfUsers");
	
	
	public int getNumberOfUsers() {
		return parseInteger(dbObject.get("numberOfUsers")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfUsers(int numberOfUsers) {
		dbObject.put("numberOfUsers", numberOfUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}