package org.ossmeter.metricprovider.historic.bugs.newusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyBugTrackerData extends Pongo {
	
	
	
	public DailyBugTrackerData() { 
		super();
		BUGTRACKERID.setOwningType("org.ossmeter.metricprovider.historic.bugs.newusers.model.DailyBugTrackerData");
		NUMBEROFNEWUSERS.setOwningType("org.ossmeter.metricprovider.historic.bugs.newusers.model.DailyBugTrackerData");
		CUMULATIVENUMBEROFNEWUSERS.setOwningType("org.ossmeter.metricprovider.historic.bugs.newusers.model.DailyBugTrackerData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFNEWUSERS = new NumericalQueryProducer("numberOfNewUsers");
	public static NumericalQueryProducer CUMULATIVENUMBEROFNEWUSERS = new NumericalQueryProducer("cumulativeNumberOfNewUsers");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfNewUsers() {
		return parseInteger(dbObject.get("numberOfNewUsers")+"", 0);
	}
	
	public DailyBugTrackerData setNumberOfNewUsers(int numberOfNewUsers) {
		dbObject.put("numberOfNewUsers", numberOfNewUsers);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfNewUsers() {
		return parseInteger(dbObject.get("cumulativeNumberOfNewUsers")+"", 0);
	}
	
	public DailyBugTrackerData setCumulativeNumberOfNewUsers(int cumulativeNumberOfNewUsers) {
		dbObject.put("cumulativeNumberOfNewUsers", cumulativeNumberOfNewUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}