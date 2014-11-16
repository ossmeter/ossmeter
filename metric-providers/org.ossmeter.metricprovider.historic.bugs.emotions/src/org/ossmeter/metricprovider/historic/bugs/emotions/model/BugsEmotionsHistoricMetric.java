package org.ossmeter.metricprovider.historic.bugs.emotions.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class BugsEmotionsHistoricMetric extends Pongo {
	
	protected List<BugData> bugData = null;
	protected List<Dimension> dimensions = null;
	
	
	public BugsEmotionsHistoricMetric() { 
		super();
		dbObject.put("bugData", new BasicDBList());
		dbObject.put("dimensions", new BasicDBList());
	}
	
	
	
	
	
	public List<BugData> getBugData() {
		if (bugData == null) {
			bugData = new PongoList<BugData>(this, "bugData", true);
		}
		return bugData;
	}
	public List<Dimension> getDimensions() {
		if (dimensions == null) {
			dimensions = new PongoList<Dimension>(this, "dimensions", true);
		}
		return dimensions;
	}
	
	
}