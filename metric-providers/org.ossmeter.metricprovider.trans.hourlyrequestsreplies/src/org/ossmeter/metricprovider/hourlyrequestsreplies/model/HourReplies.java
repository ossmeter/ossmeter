package org.ossmeter.metricprovider.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class HourReplies extends Pongo {
	
	
	
	public HourReplies() { 
		super();
		HOUR.setOwningType("org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourReplies");
		NUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.hourlyrequestsreplies.model.HourReplies");
	}
	
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	
	
	public String getHour() {
		return parseString(dbObject.get("hour")+"", "");
	}
	
	public HourReplies setHour(String hour) {
		dbObject.put("hour", hour);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public HourReplies setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}