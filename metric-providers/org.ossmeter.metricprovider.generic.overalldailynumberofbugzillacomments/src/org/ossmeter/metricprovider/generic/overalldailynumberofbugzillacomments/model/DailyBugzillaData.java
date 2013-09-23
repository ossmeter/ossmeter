package org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		NUMBEROFCOMMENTS.setOwningType("org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model.DailyBugzillaData");
	}
	
	public static NumericalQueryProducer NUMBEROFCOMMENTS = new NumericalQueryProducer("numberOfComments");
	
	
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public DailyBugzillaData setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments);
		notifyChanged();
		return this;
	}
	
	
	
	
}