package org.ossmeter.metricprovider.historic.numberofworksformebugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFWORKSFORMEBUGS.setOwningType("org.ossmeter.metricprovider.historic.numberofworksformebugzillabugs.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFWORKSFORMEBUGS = new NumericalQueryProducer("numberOfWorksForMeBugs");
	
	
	public int getNumberOfWorksForMeBugs() {
		return parseInteger(dbObject.get("numberOfWorksForMeBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfWorksForMeBugs(int numberOfWorksForMeBugs) {
		dbObject.put("numberOfWorksForMeBugs", numberOfWorksForMeBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}