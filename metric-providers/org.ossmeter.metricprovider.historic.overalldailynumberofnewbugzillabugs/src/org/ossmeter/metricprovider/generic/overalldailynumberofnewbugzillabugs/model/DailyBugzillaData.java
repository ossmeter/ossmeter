package org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFBUGS.setOwningType("org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	
	
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}