package org.ossmeter.metricprovider.historic.bugs.unansweredbugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class BugsUnansweredBugsHistoricMetric extends Pongo {
	
	
	
	public BugsUnansweredBugsHistoricMetric() { 
		super();
		NUMBEROFUNANSWEREDBUGS.setOwningType("org.ossmeter.metricprovider.historic.bugs.unansweredbugs.model.BugsUnansweredBugsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFUNANSWEREDBUGS = new NumericalQueryProducer("numberOfUnansweredBugs");
	
	
	public int getNumberOfUnansweredBugs() {
		return parseInteger(dbObject.get("numberOfUnansweredBugs")+"", 0);
	}
	
	public BugsUnansweredBugsHistoricMetric setNumberOfUnansweredBugs(int numberOfUnansweredBugs) {
		dbObject.put("numberOfUnansweredBugs", numberOfUnansweredBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}