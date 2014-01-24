package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class AverageRequestsPerThreadData extends Pongo {
	
	
	
	public AverageRequestsPerThreadData() { 
		super();
		AVERAGEREQUESTSPERTHREAD.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread.model.AverageRequestsPerThreadData");
	}
	
	public static NumericalQueryProducer AVERAGEREQUESTSPERTHREAD = new NumericalQueryProducer("averageRequestsPerThread");
	
	
	public float getAverageRequestsPerThread() {
		return parseFloat(dbObject.get("averageRequestsPerThread")+"", 0.0f);
	}
	
	public AverageRequestsPerThreadData setAverageRequestsPerThread(float averageRequestsPerThread) {
		dbObject.put("averageRequestsPerThread", averageRequestsPerThread);
		notifyChanged();
		return this;
	}
	
	
	
	
}