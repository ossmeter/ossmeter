package org.ossmeter.metricprovider.rascal.trans.model;

import java.util.List;

import com.googlecode.pongo.runtime.PongoList;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class SetMeasurement extends Measurement {
	
	protected List<Measurement> value = null;
	
	
	public SetMeasurement() { 
		super();
		dbObject.put("value", new BasicDBList());
		super.setSuperTypes("org.ossmeter.metricprovider.rascal.trans.model.Measurement");
		URI.setOwningType("org.ossmeter.metricprovider.rascal.trans.model.SetMeasurement");
	}
	
	public static StringQueryProducer URI = new StringQueryProducer("uri"); 
	
	
	
	
	public List<Measurement> getValue() {
		if (value == null) {
			value = new PongoList<Measurement>(this, "value", true);
		}
		return value;
	}
	
	
}