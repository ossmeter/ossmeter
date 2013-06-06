package org.ossmeter.metricprovider.generic.numberofactivecommittersperdaypernewsgroup.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.generic.numberofactivecommittersperdaypernewsgroup.model.DailyNewsgroupData");
		NUMBEROFACTIVECOMMITTERS.setOwningType("org.ossmeter.metricprovider.generic.numberofactivecommittersperdaypernewsgroup.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFACTIVECOMMITTERS = new NumericalQueryProducer("numberOfActiveCommitters");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfActiveCommitters() {
		return parseInteger(dbObject.get("numberOfActiveCommitters")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfActiveCommitters(int numberOfActiveCommitters) {
		dbObject.put("numberOfActiveCommitters", numberOfActiveCommitters);
		notifyChanged();
		return this;
	}
	
	
	
	
}