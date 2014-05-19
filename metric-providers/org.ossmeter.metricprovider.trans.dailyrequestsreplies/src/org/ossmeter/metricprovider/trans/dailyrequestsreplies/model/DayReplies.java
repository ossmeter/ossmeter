package org.ossmeter.metricprovider.trans.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DayReplies extends Pongo {
	
	
	
	public DayReplies() { 
		super();
		NAME.setOwningType("org.ossmeter.metricprovider.trans.dailyrequestsreplies.model.DayReplies");
		NUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.trans.dailyrequestsreplies.model.DayReplies");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public DayReplies setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public DayReplies setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}