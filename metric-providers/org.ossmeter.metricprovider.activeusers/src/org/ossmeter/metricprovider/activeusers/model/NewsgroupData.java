package org.ossmeter.metricprovider.activeusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.activeusers.model.NewsgroupData");
		NUMBEROFACTIVEUSERS.setOwningType("org.ossmeter.metricprovider.activeusers.model.NewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFACTIVEUSERS = new NumericalQueryProducer("numberOfActiveUsers");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public NewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfActiveUsers() {
		return parseInteger(dbObject.get("numberOfActiveUsers")+"", 0);
	}
	
	public NewsgroupData setNumberOfActiveUsers(int numberOfActiveUsers) {
		dbObject.put("numberOfActiveUsers", numberOfActiveUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}