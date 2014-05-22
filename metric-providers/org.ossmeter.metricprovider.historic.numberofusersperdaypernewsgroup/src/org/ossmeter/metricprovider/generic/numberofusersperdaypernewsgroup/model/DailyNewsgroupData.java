package org.ossmeter.metricprovider.generic.numberofusersperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.generic.numberofusersperdaypernewsgroup.model.DailyNewsgroupData");
		NUMBEROFUSERS.setOwningType("org.ossmeter.metricprovider.generic.numberofusersperdaypernewsgroup.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFUSERS = new NumericalQueryProducer("numberOfUsers");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfUsers() {
		return parseInteger(dbObject.get("numberOfUsers")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfUsers(int numberOfUsers) {
		dbObject.put("numberOfUsers", numberOfUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}