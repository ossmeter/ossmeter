package org.ossmeter.metricprovider.historic.overalldailynumberofbugzillapatches.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFPATCHES.setOwningType("org.ossmeter.metricprovider.historic.overalldailynumberofbugzillapatches.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFPATCHES = new NumericalQueryProducer("numberOfPatches");
	
	
	public int getNumberOfPatches() {
		return parseInteger(dbObject.get("numberOfPatches")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfPatches(int numberOfPatches) {
		dbObject.put("numberOfPatches", numberOfPatches);
		notifyChanged();
		return this;
	}
	
	
	
	
}