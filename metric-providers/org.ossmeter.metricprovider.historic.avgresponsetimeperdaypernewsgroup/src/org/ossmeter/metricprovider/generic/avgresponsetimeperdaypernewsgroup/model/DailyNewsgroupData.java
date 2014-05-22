package org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model.DailyNewsgroupData");
		AVGRESPONSETIME.setOwningType("org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model.DailyNewsgroupData");
		THREADSCONSIDERED.setOwningType("org.ossmeter.metricprovider.generic.avgresponsetimeperdaypernewsgroup.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static StringQueryProducer AVGRESPONSETIME = new StringQueryProducer("avgResponseTime"); 
	public static NumericalQueryProducer THREADSCONSIDERED = new NumericalQueryProducer("threadsConsidered");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public String getAvgResponseTime() {
		return parseString(dbObject.get("avgResponseTime")+"", "");
	}
	
	public DailyNewsgroupData setAvgResponseTime(String avgResponseTime) {
		dbObject.put("avgResponseTime", avgResponseTime);
		notifyChanged();
		return this;
	}
	public int getThreadsConsidered() {
		return parseInteger(dbObject.get("threadsConsidered")+"", 0);
	}
	
	public DailyNewsgroupData setThreadsConsidered(int threadsConsidered) {
		dbObject.put("threadsConsidered", threadsConsidered);
		notifyChanged();
		return this;
	}
	
	
	
	
}