package org.ossmeter.metricprovider.activecommitters.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupData extends Pongo {
	
	protected List<Committer> committers = null;
	
	
	public NewsgroupData() { 
		super();
		dbObject.put("committers", new BasicDBList());
		URL_NAME.setOwningType("org.ossmeter.metricprovider.activecommitters.model.NewsgroupData");
		NUMBEROFACTIVECOMMITERS.setOwningType("org.ossmeter.metricprovider.activecommitters.model.NewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFACTIVECOMMITERS = new NumericalQueryProducer("numberOfActiveCommiters");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public NewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfActiveCommiters() {
		return parseInteger(dbObject.get("numberOfActiveCommiters")+"", 0);
	}
	
	public NewsgroupData setNumberOfActiveCommiters(int numberOfActiveCommiters) {
		dbObject.put("numberOfActiveCommiters", numberOfActiveCommiters);
		notifyChanged();
		return this;
	}
	
	
	public List<Committer> getCommitters() {
		if (committers == null) {
			committers = new PongoList<Committer>(this, "committers", true);
		}
		return committers;
	}
	
	
}