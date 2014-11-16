package org.ossmeter.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupThreadData extends Pongo {
	
	
	
	public NewsgroupThreadData() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
		THREADID.setOwningType("org.ossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
		SEVERITY.setOwningType("org.ossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static StringQueryProducer SEVERITY = new StringQueryProducer("severity"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public NewsgroupThreadData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public NewsgroupThreadData setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public String getSeverity() {
		return parseString(dbObject.get("severity")+"", "");
	}
	
	public NewsgroupThreadData setSeverity(String severity) {
		dbObject.put("severity", severity);
		notifyChanged();
		return this;
	}
	
	
	
	
}