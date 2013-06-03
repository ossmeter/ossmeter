package org.ossmeter.metricprovider.rascal.history.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class HistoricRascalMetrics extends Pongo {
	
	protected List<org.ossmeter.metricprovider.rascal.trans.model.Measurement> measurements = null;
	
	
	public HistoricRascalMetrics() { 
		super();
		dbObject.put("measurements", new BasicDBList());
	}
	
	
	
	public List<org.ossmeter.metricprovider.rascal.trans.model.Measurement> getMeasurements() {
		if (measurements == null) {
			measurements = new PongoList<org.ossmeter.metricprovider.rascal.trans.model.Measurement>(this, "measurements", true);
		}
		return measurements;
	}
	
	
}