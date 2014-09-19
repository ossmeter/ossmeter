package org.ossmeter.metricprovider.historic.numberoffixedbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFFIXEDBUGS.setOwningType("org.ossmeter.metricprovider.historic.numberoffixedbugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFFIXEDBUGS = new NumericalQueryProducer("numberOfFixedBugs");
	
	
	public int getNumberOfFixedBugs() {
		return parseInteger(dbObject.get("numberOfFixedBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfFixedBugs(int numberOfFixedBugs) {
		dbObject.put("numberOfFixedBugs", numberOfFixedBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}