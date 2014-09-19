package org.ossmeter.metricprovider.historic.numberofinvalidbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFINVALIDBUGS.setOwningType("org.ossmeter.metricprovider.historic.numberofinvalidbugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFINVALIDBUGS = new NumericalQueryProducer("numberOfInvalidBugs");
	
	
	public int getNumberOfInvalidBugs() {
		return parseInteger(dbObject.get("numberOfInvalidBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfInvalidBugs(int numberOfInvalidBugs) {
		dbObject.put("numberOfInvalidBugs", numberOfInvalidBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}