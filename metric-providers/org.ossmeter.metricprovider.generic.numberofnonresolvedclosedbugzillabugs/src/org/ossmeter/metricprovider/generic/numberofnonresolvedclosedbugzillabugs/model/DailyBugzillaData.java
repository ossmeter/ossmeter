package org.ossmeter.metricprovider.generic.numberofnonresolvedclosedbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFNONRESOLVEDCLOSEDBUGS.setOwningType("org.ossmeter.metricprovider.generic.numberofnonresolvedclosedbugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFNONRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("numberOfNonResolvedClosedBugs");
	
	
	public int getNumberOfNonResolvedClosedBugs() {
		return parseInteger(dbObject.get("numberOfNonResolvedClosedBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfNonResolvedClosedBugs(int numberOfNonResolvedClosedBugs) {
		dbObject.put("numberOfNonResolvedClosedBugs", numberOfNonResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}