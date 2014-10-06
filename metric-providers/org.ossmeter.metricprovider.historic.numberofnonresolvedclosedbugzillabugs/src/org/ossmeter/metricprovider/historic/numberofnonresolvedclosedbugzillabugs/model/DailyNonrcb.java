package org.ossmeter.metricprovider.historic.numberofnonresolvedclosedbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyNonrcb extends Pongo {
	
	
	
	public DailyNonrcb() { 
		super();
		NUMBEROFNONRESOLVEDCLOSEDBUGS.setOwningType("org.ossmeter.metricprovider.historic.numberofnonresolvedclosedbugzillabugs.model.DailyNonrcb");
	}
	
	public static NumericalQueryProducer NUMBEROFNONRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("numberOfNonResolvedClosedBugs");
	
	
	public int getNumberOfNonResolvedClosedBugs() {
		return parseInteger(dbObject.get("numberOfNonResolvedClosedBugs")+"", 0);
	}
	
	public DailyNonrcb setNumberOfNonResolvedClosedBugs(int numberOfNonResolvedClosedBugs) {
		dbObject.put("numberOfNonResolvedClosedBugs", numberOfNonResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}