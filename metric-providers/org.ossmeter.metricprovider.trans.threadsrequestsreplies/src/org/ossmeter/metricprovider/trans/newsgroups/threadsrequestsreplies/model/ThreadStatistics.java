package org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class ThreadStatistics extends Pongo {
	
	
	
	public ThreadStatistics() { 
		super();
		URL_NAME.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		THREADID.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		FIRSTREQUEST.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		ANSWERED.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		RESPONSEDURATIONSEC.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		RESPONSEDATE.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		AVERAGESENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		STARTSENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
		ENDSENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics");
	}
	
	public static StringQueryProducer URL_NAME = new StringQueryProducer("url_name"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer FIRSTREQUEST = new StringQueryProducer("firstRequest"); 
	public static StringQueryProducer ANSWERED = new StringQueryProducer("answered"); 
	public static NumericalQueryProducer RESPONSEDURATIONSEC = new NumericalQueryProducer("responseDurationSec");
	public static StringQueryProducer RESPONSEDATE = new StringQueryProducer("responseDate"); 
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
	public boolean getFirstRequest() {
		return parseBoolean(dbObject.get("firstRequest")+"", false);
	}
	
	public ThreadStatistics setFirstRequest(boolean firstRequest) {
		dbObject.put("firstRequest", firstRequest);
		notifyChanged();
		return this;
	}
	public boolean getAnswered() {
		return parseBoolean(dbObject.get("answered")+"", false);
	}
	
	public ThreadStatistics setAnswered(boolean answered) {
		dbObject.put("answered", answered);
		notifyChanged();
		return this;
	}
	public long getResponseDurationSec() {
		return parseLong(dbObject.get("responseDurationSec")+"", 0);
	}
	
	public ThreadStatistics setResponseDurationSec(long responseDurationSec) {
		dbObject.put("responseDurationSec", responseDurationSec);
		notifyChanged();
		return this;
	}
	public String getResponseDate() {
		return parseString(dbObject.get("responseDate")+"", "");
	}
	
	public ThreadStatistics setResponseDate(String responseDate) {
		dbObject.put("responseDate", responseDate);
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