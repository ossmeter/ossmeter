package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public abstract class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperdaypernewsgroup.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	
	
	
	
}