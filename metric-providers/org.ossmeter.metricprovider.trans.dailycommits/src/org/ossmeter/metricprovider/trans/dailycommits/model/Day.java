package org.ossmeter.metricprovider.trans.dailycommits.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Day extends Pongo {
	
	
	
	public Day() { 
		super();
		NAME.setOwningType("org.ossmeter.metricprovider.trans.dailycommits.model.Day");
		NUMBEROFCOMMITS.setOwningType("org.ossmeter.metricprovider.trans.dailycommits.model.Day");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer NUMBEROFCOMMITS = new NumericalQueryProducer("numberOfCommits");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Day setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommits() {
		return parseInteger(dbObject.get("numberOfCommits")+"", 0);
	}
	
	public Day setNumberOfCommits(int numberOfCommits) {
		dbObject.put("numberOfCommits", numberOfCommits);
		notifyChanged();
		return this;
	}
	
	
	
	
}