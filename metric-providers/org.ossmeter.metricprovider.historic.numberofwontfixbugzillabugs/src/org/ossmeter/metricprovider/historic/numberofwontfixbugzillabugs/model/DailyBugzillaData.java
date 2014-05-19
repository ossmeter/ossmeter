package org.ossmeter.metricprovider.historic.numberofwontfixbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFWONTFIXBUGS.setOwningType("org.ossmeter.metricprovider.historic.numberofwontfixbugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFWONTFIXBUGS = new NumericalQueryProducer("numberOfWontFixBugs");
	
	
	public int getNumberOfWontFixBugs() {
		return parseInteger(dbObject.get("numberOfWontFixBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfWontFixBugs(int numberOfWontFixBugs) {
		dbObject.put("numberOfWontFixBugs", numberOfWontFixBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}