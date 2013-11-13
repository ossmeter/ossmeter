package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class NOA extends PongoDB {
	
	public NOA() {}
	
	public NOA(DB db) {
		setDb(db);
	}
	
	protected NOADataCollection numberOfAttributes = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NOADataCollection getNumberOfAttributes() {
		return numberOfAttributes;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		numberOfAttributes = new NOADataCollection(db.getCollection("NOA.numberOfAttributes"));
		pongoCollections.add(numberOfAttributes);
	}
}