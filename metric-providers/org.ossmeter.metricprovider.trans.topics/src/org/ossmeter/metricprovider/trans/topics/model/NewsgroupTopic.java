package org.ossmeter.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupTopic extends Pongo {
	
	
	
	public NewsgroupTopic() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.topics.model.NewsgroupTopic");
		LABEL.setOwningType("org.ossmeter.metricprovider.trans.topics.model.NewsgroupTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.ossmeter.metricprovider.trans.topics.model.NewsgroupTopic");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public NewsgroupTopic setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public NewsgroupTopic setLabel(String label) {
		dbObject.put("label", label);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocuments() {
		return parseInteger(dbObject.get("numberOfDocuments")+"", 0);
	}
	
	public NewsgroupTopic setNumberOfDocuments(int numberOfDocuments) {
		dbObject.put("numberOfDocuments", numberOfDocuments);
		notifyChanged();
		return this;
	}
	
	
	
	
}