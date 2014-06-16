package org.ossmeter.metricprovider.rascal.historic.model;

import com.mongodb.*;
import java.util.*;

import org.ossmeter.metricprovider.rascal.trans.model.Measurement;

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RascalHistoricMetrics extends Pongo {
	
	protected List<Measurement> measurements = null;
	
	
	public RascalHistoricMetrics() { 
		super();
		dbObject.put("measurements", new BasicDBList());
	}
	
	
	
	
	
	public List<Measurement> getMeasurements() {
		if (measurements == null) {
			measurements = new PongoList<Measurement>(this, "measurements", true);
		}
		return measurements;
	}
	
	
}