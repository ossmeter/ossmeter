package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class WMC extends PongoDB {
	
	public WMC() {}
	
	public WMC(DB db) {
		setDb(db);
	}
	
	protected WMCDataCollection weightedMethodCount = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public WMCDataCollection getWeightedMethodCount() {
		return weightedMethodCount;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		weightedMethodCount = new WMCDataCollection(db.getCollection("WMC.weightedMethodCount"));
		pongoCollections.add(weightedMethodCount);
	}
}