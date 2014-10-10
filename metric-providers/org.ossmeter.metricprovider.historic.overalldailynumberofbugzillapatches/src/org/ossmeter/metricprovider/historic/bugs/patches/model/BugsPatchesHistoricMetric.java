package org.ossmeter.metricprovider.historic.bugs.patches.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsPatchesHistoricMetric extends Pongo {
	
	protected List<DailyBugData> bugs = null;
	
	
	public BugsPatchesHistoricMetric() { 
		super();
		dbObject.put("bugs", new BasicDBList());
		NUMBEROFPATCHES.setOwningType("org.ossmeter.metricprovider.historic.bugs.patches.model.BugsPatchesHistoricMetric");
		CUMULATIVENUMBEROFPATCHES.setOwningType("org.ossmeter.metricprovider.historic.bugs.patches.model.BugsPatchesHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFPATCHES = new NumericalQueryProducer("numberOfPatches");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPATCHES = new NumericalQueryProducer("cumulativeNumberOfPatches");
	
	
	public int getNumberOfPatches() {
		return parseInteger(dbObject.get("numberOfPatches")+"", 0);
	}
	
	public BugsPatchesHistoricMetric setNumberOfPatches(int numberOfPatches) {
		dbObject.put("numberOfPatches", numberOfPatches);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPatches() {
		return parseInteger(dbObject.get("cumulativeNumberOfPatches")+"", 0);
	}
	
	public BugsPatchesHistoricMetric setCumulativeNumberOfPatches(int cumulativeNumberOfPatches) {
		dbObject.put("cumulativeNumberOfPatches", cumulativeNumberOfPatches);
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