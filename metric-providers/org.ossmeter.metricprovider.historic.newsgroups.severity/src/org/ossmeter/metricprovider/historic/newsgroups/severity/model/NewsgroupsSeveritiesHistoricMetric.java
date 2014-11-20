package org.ossmeter.metricprovider.historic.newsgroups.severity.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsSeveritiesHistoricMetric extends Pongo {
	
	protected List<NewsgroupData> newsgroupData = null;
	protected List<SeverityLevel> severityLevels = null;
	
	
	public NewsgroupsSeveritiesHistoricMetric() { 
		super();
		dbObject.put("newsgroupData", new BasicDBList());
		dbObject.put("severityLevels", new BasicDBList());
	}
	
	
	
	
	
	public List<NewsgroupData> getNewsgroupData() {
		if (newsgroupData == null) {
			newsgroupData = new PongoList<NewsgroupData>(this, "newsgroupData", true);
		}
		return newsgroupData;
	}
	public List<SeverityLevel> getSeverityLevels() {
		if (severityLevels == null) {
			severityLevels = new PongoList<SeverityLevel>(this, "severityLevels", true);
		}
		return severityLevels;
	}
	
	
}