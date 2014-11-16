package org.ossmeter.metricprovider.trans.newsgroups.sentiment.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ThreadStatistics extends Pongo {
	
	
	
	public ThreadStatistics() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics");
		THREADID.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics");
		AVERAGESENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics");
		STARTSENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics");
		ENDSENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static NumericalQueryProducer AVERAGESENTIMENT = new NumericalQueryProducer("averageSentiment");
	public static StringQueryProducer STARTSENTIMENT = new StringQueryProducer("startSentiment"); 
	public static StringQueryProducer ENDSENTIMENT = new StringQueryProducer("endSentiment"); 
	
	
	public String getUrl_name() {
		return parseString(dbObject.get("url_name")+"", "");
	}
	
	public ThreadStatistics setUrl_name(String url_name) {
		dbObject.put("url_name", url_name);
		notifyChanged();
		return this;
	}
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public ThreadStatistics setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public float getAverageSentiment() {
		return parseFloat(dbObject.get("averageSentiment")+"", 0.0f);
	}
	
	public ThreadStatistics setAverageSentiment(float averageSentiment) {
		dbObject.put("averageSentiment", averageSentiment);
		notifyChanged();
		return this;
	}
	public String getStartSentiment() {
		return parseString(dbObject.get("startSentiment")+"", "");
	}
	
	public ThreadStatistics setStartSentiment(String startSentiment) {
		dbObject.put("startSentiment", startSentiment);
		notifyChanged();
		return this;
	}
	public String getEndSentiment() {
		return parseString(dbObject.get("endSentiment")+"", "");
	}
	
	public ThreadStatistics setEndSentiment(String endSentiment) {
		dbObject.put("endSentiment", endSentiment);
		notifyChanged();
		return this;
	}
	
	
	
	
}