package org.ossmeter.metricprovider.rascal.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

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