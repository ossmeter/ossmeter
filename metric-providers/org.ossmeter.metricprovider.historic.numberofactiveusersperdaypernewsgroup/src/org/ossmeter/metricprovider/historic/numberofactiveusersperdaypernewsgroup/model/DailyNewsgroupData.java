package org.ossmeter.metricprovider.historic.numberofactiveusersperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.historic.numberofactiveusersperdaypernewsgroup.model.DailyNewsgroupData");
		NUMBEROFACTIVEUSERS.setOwningType("org.ossmeter.metricprovider.historic.numberofactiveusersperdaypernewsgroup.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFACTIVEUSERS = new NumericalQueryProducer("numberOfActiveUsers");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfActiveUsers() {
		return parseInteger(dbObject.get("numberOfActiveUsers")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfActiveUsers(int numberOfActiveUsers) {
		dbObject.put("numberOfActiveUsers", numberOfActiveUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}