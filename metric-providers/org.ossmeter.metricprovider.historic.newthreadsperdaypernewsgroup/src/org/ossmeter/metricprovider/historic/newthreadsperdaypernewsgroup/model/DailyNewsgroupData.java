package org.ossmeter.metricprovider.historic.newthreadsperdaypernewsgroup.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.historic.newthreadsperdaypernewsgroup.model.DailyNewsgroupData");
		NUMBEROFNEWTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newthreadsperdaypernewsgroup.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFNEWTHREADS = new NumericalQueryProducer("numberOfNewThreads");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public DailyNewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfNewThreads() {
		return parseInteger(dbObject.get("numberOfNewThreads")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfNewThreads(int numberOfNewThreads) {
		dbObject.put("numberOfNewThreads", numberOfNewThreads);
		notifyChanged();
		return this;
	}
	
	
	
	
}