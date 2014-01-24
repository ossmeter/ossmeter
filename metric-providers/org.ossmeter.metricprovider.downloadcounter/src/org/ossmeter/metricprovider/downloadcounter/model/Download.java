package org.ossmeter.metricprovider.downloadcounter.model;

import com.googlecode.pongo.runtime.Pongo;


public class Download extends Pongo {
	
	
	
	public Download() { 
		super();
	}
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public Download setDate(String date) {
		dbObject.put("date", date + "");
		notifyChanged();
		return this;
	}
	public int getCounter() {
		return parseInteger(dbObject.get("counter")+"", 0);
	}
	
	public Download setCounter(int counter) {
		dbObject.put("counter", counter + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}