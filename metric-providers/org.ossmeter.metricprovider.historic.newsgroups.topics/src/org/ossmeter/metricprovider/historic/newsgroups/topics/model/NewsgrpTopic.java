package org.ossmeter.metricprovider.historic.newsgroups.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgrpTopic extends Pongo {
	
	
	
	public NewsgrpTopic() { 
		super();
		NEWSGROUPNAME.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic");
		LABEL.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgrpTopic setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public NewsgrpTopic setLabel(String label) {
		dbObject.put("label", label);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocuments() {
		return parseInteger(dbObject.get("numberOfDocuments")+"", 0);
	}
	
	public NewsgrpTopic setNumberOfDocuments(int numberOfDocuments) {
		dbObject.put("numberOfDocuments", numberOfDocuments);
		notifyChanged();
		return this;
	}
	
	
	
	
}