package org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
	}
	
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}