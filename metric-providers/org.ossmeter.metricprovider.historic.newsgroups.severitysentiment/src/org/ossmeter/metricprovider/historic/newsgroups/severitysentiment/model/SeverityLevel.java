package org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class SeverityLevel extends Pongo {
	
	
	
	public SeverityLevel() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel");
		SEVERITYLEVEL.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel");
		NUMBEROFTHREADS.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel");
		AVERAGESENTIMENT.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel");
		SENTIMENTATTHREADBEGGINING.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel");
		SENTIMENTATTHREADEND.setOwningType("org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer SEVERITYLEVEL = new StringQueryProducer("severityLevel"); 
	public static NumericalQueryProducer NUMBEROFTHREADS = new NumericalQueryProducer("numberOfThreads");
	public static NumericalQueryProducer AVERAGESENTIMENT = new NumericalQueryProducer("averageSentiment");
	public static NumericalQueryProducer SENTIMENTATTHREADBEGGINING = new NumericalQueryProducer("sentimentAtThreadBeggining");
	public static NumericalQueryProducer SENTIMENTATTHREADEND = new NumericalQueryProducer("sentimentAtThreadEnd");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public SeverityLevel setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getSeverityLevel() {
		return parseString(dbObject.get("severityLevel")+"", "");
	}
	
	public SeverityLevel setSeverityLevel(String severityLevel) {
		dbObject.put("severityLevel", severityLevel);
		notifyChanged();
		return this;
	}
	public int getNumberOfThreads() {
		return parseInteger(dbObject.get("numberOfThreads")+"", 0);
	}
	
	public SeverityLevel setNumberOfThreads(int numberOfThreads) {
		dbObject.put("numberOfThreads", numberOfThreads);
		notifyChanged();
		return this;
	}
	public float getAverageSentiment() {
		return parseFloat(dbObject.get("averageSentiment")+"", 0.0f);
	}
	
	public SeverityLevel setAverageSentiment(float averageSentiment) {
		dbObject.put("averageSentiment", averageSentiment);
		notifyChanged();
		return this;
	}
	public float getSentimentAtThreadBeggining() {
		return parseFloat(dbObject.get("sentimentAtThreadBeggining")+"", 0.0f);
	}
	
	public SeverityLevel setSentimentAtThreadBeggining(float sentimentAtThreadBeggining) {
		dbObject.put("sentimentAtThreadBeggining", sentimentAtThreadBeggining);
		notifyChanged();
		return this;
	}
	public float getSentimentAtThreadEnd() {
		return parseFloat(dbObject.get("sentimentAtThreadEnd")+"", 0.0f);
	}
	
	public SeverityLevel setSentimentAtThreadEnd(float sentimentAtThreadEnd) {
		dbObject.put("sentimentAtThreadEnd", sentimentAtThreadEnd);
		notifyChanged();
		return this;
	}
	
	
	
	
}