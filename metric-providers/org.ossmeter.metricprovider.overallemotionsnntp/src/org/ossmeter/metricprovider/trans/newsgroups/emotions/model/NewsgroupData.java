package org.ossmeter.metricprovider.trans.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.emotions.model.NewsgroupData");
		NUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.emotions.model.NewsgroupData");
		CUMULATIVENUMBEROFARTICLES.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.emotions.model.NewsgroupData");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer CUMULATIVENUMBEROFARTICLES = new NumericalQueryProducer("cumulativeNumberOfArticles");
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public NewsgroupData setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public NewsgroupData setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfArticles() {
		return parseInteger(dbObject.get("cumulativeNumberOfArticles")+"", 0);
	}
	
	public NewsgroupData setCumulativeNumberOfArticles(int cumulativeNumberOfArticles) {
		dbObject.put("cumulativeNumberOfArticles", cumulativeNumberOfArticles);
		notifyChanged();
		return this;
	}
	
	
	
	
}