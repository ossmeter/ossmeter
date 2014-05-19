package org.ossmeter.metricprovider.historic.numberofresolvedclosedbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFRESOLVEDCLOSEDBUGS.setOwningType("org.ossmeter.metricprovider.historic.numberofresolvedclosedbugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("numberOfResolvedClosedBugs");
	
	
	public int getNumberOfResolvedClosedBugs() {
		return parseInteger(dbObject.get("numberOfResolvedClosedBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfResolvedClosedBugs(int numberOfResolvedClosedBugs) {
		dbObject.put("numberOfResolvedClosedBugs", numberOfResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}