package org.ossmeter.metricprovider.historic.bugs.opentime.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugsOpenTimeHistoricMetric extends Pongo {
	
	
	
	public BugsOpenTimeHistoricMetric() { 
		super();
		AVGBUGOPENTIME.setOwningType("org.ossmeter.metricprovider.historic.bugs.opentime.model.BugsOpenTimeHistoricMetric");
		BUGSCONSIDERED.setOwningType("org.ossmeter.metricprovider.historic.bugs.opentime.model.BugsOpenTimeHistoricMetric");
	}
	
	public static StringQueryProducer AVGBUGOPENTIME = new StringQueryProducer("avgBugOpenTime"); 
	public static NumericalQueryProducer BUGSCONSIDERED = new NumericalQueryProducer("bugsConsidered");
	
	
	public String getAvgBugOpenTime() {
		return parseString(dbObject.get("avgBugOpenTime")+"", "");
	}
	
	public BugsOpenTimeHistoricMetric setAvgBugOpenTime(String avgBugOpenTime) {
		dbObject.put("avgBugOpenTime", avgBugOpenTime);
		notifyChanged();
		return this;
	}
	public int getBugsConsidered() {
		return parseInteger(dbObject.get("bugsConsidered")+"", 0);
	}
	
	public BugsOpenTimeHistoricMetric setBugsConsidered(int bugsConsidered) {
		dbObject.put("bugsConsidered", bugsConsidered);
		notifyChanged();
		return this;
	}
	
	
	
	
}