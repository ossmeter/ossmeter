package org.ossmeter.metricprovider.trans.bugs.bugmetadata.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerData extends Pongo {
	
	
	
	public BugTrackerData() { 
		super();
		BUGTRACKERID.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		BUGID.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		STATUS.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		RESOLUTION.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		OPERATINGSYSTEM.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		PRIORITY.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		CREATIONTIME.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		LASTCLOSEDTIME.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		AVERAGESENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		STARTSENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
		ENDSENTIMENT.setOwningType("org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer RESOLUTION = new StringQueryProducer("resolution"); 
	public static StringQueryProducer OPERATINGSYSTEM = new StringQueryProducer("operatingSystem"); 
	public static StringQueryProducer PRIORITY = new StringQueryProducer("priority"); 
	public static StringQueryProducer CREATIONTIME = new StringQueryProducer("creationTime"); 
	public static StringQueryProducer LASTCLOSEDTIME = new StringQueryProducer("lastClosedTime"); 
	public static NumericalQueryProducer AVERAGESENTIMENT = new NumericalQueryProducer("averageSentiment");
	public static StringQueryProducer STARTSENTIMENT = new StringQueryProducer("startSentiment"); 
	public static StringQueryProducer ENDSENTIMENT = new StringQueryProducer("endSentiment"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public BugTrackerData setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public String getResolution() {
		return parseString(dbObject.get("resolution")+"", "");
	}
	
	public BugTrackerData setResolution(String resolution) {
		dbObject.put("resolution", resolution);
		notifyChanged();
		return this;
	}
	public String getOperatingSystem() {
		return parseString(dbObject.get("operatingSystem")+"", "");
	}
	
	public BugTrackerData setOperatingSystem(String operatingSystem) {
		dbObject.put("operatingSystem", operatingSystem);
		notifyChanged();
		return this;
	}
	public String getPriority() {
		return parseString(dbObject.get("priority")+"", "");
	}
	
	public BugTrackerData setPriority(String priority) {
		dbObject.put("priority", priority);
		notifyChanged();
		return this;
	}
	public String getCreationTime() {
		return parseString(dbObject.get("creationTime")+"", "");
	}
	
	public BugTrackerData setCreationTime(String creationTime) {
		dbObject.put("creationTime", creationTime);
		notifyChanged();
		return this;
	}
	public String getLastClosedTime() {
		return parseString(dbObject.get("lastClosedTime")+"", "");
	}
	
	public BugTrackerData setLastClosedTime(String lastClosedTime) {
		dbObject.put("lastClosedTime", lastClosedTime);
		notifyChanged();
		return this;
	}
	public float getAverageSentiment() {
		return parseFloat(dbObject.get("averageSentiment")+"", 0.0f);
	}
	
	public BugTrackerData setAverageSentiment(float averageSentiment) {
		dbObject.put("averageSentiment", averageSentiment);
		notifyChanged();
		return this;
	}
	public String getStartSentiment() {
		return parseString(dbObject.get("startSentiment")+"", "");
	}
	
	public BugTrackerData setStartSentiment(String startSentiment) {
		dbObject.put("startSentiment", startSentiment);
		notifyChanged();
		return this;
	}
	public String getEndSentiment() {
		return parseString(dbObject.get("endSentiment")+"", "");
	}
	
	public BugTrackerData setEndSentiment(String endSentiment) {
		dbObject.put("endSentiment", endSentiment);
		notifyChanged();
		return this;
	}
	
	
	
	
}