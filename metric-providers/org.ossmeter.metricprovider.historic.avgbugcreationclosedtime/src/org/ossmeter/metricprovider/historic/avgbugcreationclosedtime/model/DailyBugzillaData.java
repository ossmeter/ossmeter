package org.ossmeter.metricprovider.historic.avgbugcreationclosedtime.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyBugzillaData extends Pongo {
	
	
	
	public DailyBugzillaData() { 
		super();
		AVGBUGOPENTIME.setOwningType("org.ossmeter.metricprovider.historic.avgbugcreationclosedtime.model.DailyBugzillaData");
		BUGSCONSIDERED.setOwningType("org.ossmeter.metricprovider.historic.avgbugcreationclosedtime.model.DailyBugzillaData");
	}
	
	public static StringQueryProducer AVGBUGOPENTIME = new StringQueryProducer("avgBugOpenTime"); 
	public static NumericalQueryProducer BUGSCONSIDERED = new NumericalQueryProducer("bugsConsidered");
	
	
	public String getAvgBugOpenTime() {
		return parseString(dbObject.get("avgBugOpenTime")+"", "");
	}
	
	public DailyBugzillaData setAvgBugOpenTime(String avgBugOpenTime) {
		dbObject.put("avgBugOpenTime", avgBugOpenTime);
		notifyChanged();
		return this;
	}
	public int getBugsConsidered() {
		return parseInteger(dbObject.get("bugsConsidered")+"", 0);
	}
	
	public DailyBugzillaData setBugsConsidered(int bugsConsidered) {
		dbObject.put("bugsConsidered", bugsConsidered);
		notifyChanged();
		return this;
	}
	
	
	
	
}