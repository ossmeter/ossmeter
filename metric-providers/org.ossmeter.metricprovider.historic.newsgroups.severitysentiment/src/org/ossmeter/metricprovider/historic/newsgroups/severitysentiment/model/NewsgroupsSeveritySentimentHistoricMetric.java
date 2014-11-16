package org.ossmeter.metricprovider.historic.newsgroups.severitysentiment.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupsSeveritySentimentHistoricMetric extends Pongo {
	
	protected List<SeverityLevel> severityLevels = null;
	
	
	public NewsgroupsSeveritySentimentHistoricMetric() { 
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