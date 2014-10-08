package org.ossmeter.metricprovider.trans.newsgroups.contentclasses.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class ContentClass extends Pongo {
	
	
	
	public ContentClass() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
		CLASSLABEL.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
		NUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
		PERCENTAGE.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static StringQueryProducer CLASSLABEL = new StringQueryProducer("classLabel"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public ContentClass setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public String getClassLabel() {
		return parseString(dbObject.get("classLabel")+"", "");
	}
	
	public ContentClass setClassLabel(String classLabel) {
		dbObject.put("classLabel", classLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public ContentClass setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public ContentClass setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}