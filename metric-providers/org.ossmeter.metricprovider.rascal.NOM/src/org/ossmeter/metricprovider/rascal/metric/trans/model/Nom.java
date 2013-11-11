package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Nom extends PongoDB {
	
	public Nom() {}
	
	public Nom(DB db) {
		setDb(db);
	}
	
	protected NumberOfMethodsDataCollection totalNumberOfMethods = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NumberOfMethodsDataCollection getTotalNumberOfMethods() {
		return totalNumberOfMethods;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		totalNumberOfMethods = new NumberOfMethodsDataCollection(db.getCollection("Nom.totalNumberOfMethods"));
		pongoCollections.add(totalNumberOfMethods);
	}
}