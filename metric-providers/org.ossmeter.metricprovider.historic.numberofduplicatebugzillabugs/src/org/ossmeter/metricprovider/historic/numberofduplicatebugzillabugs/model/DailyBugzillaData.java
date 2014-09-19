package org.ossmeter.metricprovider.historic.numberofduplicatebugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFDUPLICATEBUGS.setOwningType("org.ossmeter.metricprovider.historic.numberofduplicatebugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFDUPLICATEBUGS = new NumericalQueryProducer("numberOfDuplicateBugs");
	
	
	public int getNumberOfDuplicateBugs() {
		return parseInteger(dbObject.get("numberOfDuplicateBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfDuplicateBugs(int numberOfDuplicateBugs) {
		dbObject.put("numberOfDuplicateBugs", numberOfDuplicateBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}