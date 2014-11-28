package org.ossmeter.metricprovider.historic.bugs.bugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyBugTrackerData extends Pongo {
	
	
	
	public DailyBugTrackerData() { 
		super();
		BUGTRACKERID.setOwningType("org.ossmeter.metricprovider.historic.bugs.bugs.model.DailyBugTrackerData");
		NUMBEROFBUGS.setOwningType("org.ossmeter.metricprovider.historic.bugs.bugs.model.DailyBugTrackerData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public DailyBugTrackerData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}