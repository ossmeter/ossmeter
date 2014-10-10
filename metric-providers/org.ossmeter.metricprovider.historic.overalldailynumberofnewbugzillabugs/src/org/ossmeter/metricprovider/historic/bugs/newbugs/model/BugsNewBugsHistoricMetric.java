package org.ossmeter.metricprovider.historic.bugs.newbugs.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsNewBugsHistoricMetric extends Pongo {
	
	protected List<DailyBugData> bugs = null;
	
	
	public BugsNewBugsHistoricMetric() { 
		super();
		dbObject.put("bugs", new BasicDBList());
		NUMBEROFBUGS.setOwningType("org.ossmeter.metricprovider.historic.bugs.newbugs.model.BugsNewBugsHistoricMetric");
		CUMULATIVENUMBEROFBUGS.setOwningType("org.ossmeter.metricprovider.historic.bugs.newbugs.model.BugsNewBugsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer CUMULATIVENUMBEROFBUGS = new NumericalQueryProducer("cumulativeNumberOfBugs");
	
	
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugsNewBugsHistoricMetric setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfBugs() {
		return parseInteger(dbObject.get("cumulativeNumberOfBugs")+"", 0);
	}
	
	public BugsNewBugsHistoricMetric setCumulativeNumberOfBugs(int cumulativeNumberOfBugs) {
		dbObject.put("cumulativeNumberOfBugs", cumulativeNumberOfBugs);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyBugData> getBugs() {
		if (bugs == null) {
			bugs = new PongoList<DailyBugData>(this, "bugs", true);
		}
		return bugs;
	}
	
	
}