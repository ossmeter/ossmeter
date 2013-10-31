package org.ossmeter.metricprovider.historic.hourlycommits.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Hour extends Pongo {
	
	
	
	public Hour() { 
		super();
		HOUR.setOwningType("org.ossmeter.metricprovider.historic.hourlycommits.model.Hour");
		NUMBEROFCOMMITS.setOwningType("org.ossmeter.metricprovider.historic.hourlycommits.model.Hour");
	}
	
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFCOMMITS = new NumericalQueryProducer("numberOfCommits");
	
	
	public String getHour() {
		return parseString(dbObject.get("hour")+"", "");
	}
	
	public Hour setHour(String hour) {
		dbObject.put("hour", hour);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommits() {
		return parseInteger(dbObject.get("numberOfCommits")+"", 0);
	}
	
	public Hour setNumberOfCommits(int numberOfCommits) {
		dbObject.put("numberOfCommits", numberOfCommits);
		notifyChanged();
		return this;
	}
	
	
	
	
}