package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class URIMeasurement extends PongoDB {
	
	public URIMeasurement() {}
	
	public URIMeasurement(DB db) {
		setDb(db);
	}
	
	
	
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
	}
}