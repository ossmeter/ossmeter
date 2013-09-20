package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model;

import com.googlecode.pongo.runtime.Pongo;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
	}
	
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}