package org.ossmeter.metricprovider.rascal.historic.model;

import org.ossmeter.metricprovider.rascal.trans.model.MeasurementCollection;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;
// protected region custom-imports on begin
// protected region custom-imports end

public class RascalHistoricMetrics extends PongoDB {
	private final String collectionName;
	
	public RascalHistoricMetrics(DB db, String collectionName) {
		this.collectionName = collectionName;
		setDb(db);
	}
	
	protected MeasurementCollection measurements = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public MeasurementCollection getMeasurements() {
		return measurements;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		measurements = new MeasurementCollection(db.getCollection(collectionName));
		pongoCollections.add(measurements);
	}
}