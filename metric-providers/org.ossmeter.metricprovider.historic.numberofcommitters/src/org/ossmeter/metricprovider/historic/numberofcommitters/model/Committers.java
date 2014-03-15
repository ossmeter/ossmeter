package org.ossmeter.metricprovider.historic.numberofcommitters.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Committers extends Pongo {
	
	
	
	public Committers() { 
		super();
		NUMBEROFCOMMITTERS.setOwningType("org.ossmeter.metricprovider.historic.numberofcommitters.model.Committers");
	}
	
	public static NumericalQueryProducer NUMBEROFCOMMITTERS = new NumericalQueryProducer("numberOfCommitters");
	
	
	public int getNumberOfCommitters() {
		return parseInteger(dbObject.get("numberOfCommitters")+"", 0);
	}
	
	public Committers setNumberOfCommitters(int numberOfCommitters) {
		dbObject.put("numberOfCommitters", numberOfCommitters);
		notifyChanged();
		return this;
	}
	
	
	
	
}