package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class CC extends PongoDB {
	
	public CC() {}
	
	public CC(DB db) {
		setDb(db);
	}
	
	protected CCDataCollection methodCC = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public CCDataCollection getMethodCC() {
		return methodCC;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		methodCC = new CCDataCollection(db.getCollection("CC.methodCC"));
		pongoCollections.add(methodCC);
	}
}