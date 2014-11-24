package org.ossmeter.metricprovider.historic.newsgroups.severityresponsetime.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupsSeverityResponseTimeHistoricMetric extends Pongo {
	
	protected List<SeverityLevel> severityLevels = null;
	
	
	public NewsgroupsSeverityResponseTimeHistoricMetric() { 
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