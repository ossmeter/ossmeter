package org.ossmeter.metricprovider.historic.newsgroups.severityresponsetime.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsSeverityResponseTimeHistoricMetric extends Pongo {
	
	protected List<SeverityLevel> severityLevels = null;
	
	
	public BugsSeverityResponseTimeHistoricMetric() { 
		super();
		dbObject.put("severityLevels", new BasicDBList());
	}
	
	
	
	
	
	public List<SeverityLevel> getSeverityLevels() {
		if (severityLevels == null) {
			severityLevels = new PongoList<SeverityLevel>(this, "severityLevels", true);
		}
		return severityLevels;
	}
	
	
}