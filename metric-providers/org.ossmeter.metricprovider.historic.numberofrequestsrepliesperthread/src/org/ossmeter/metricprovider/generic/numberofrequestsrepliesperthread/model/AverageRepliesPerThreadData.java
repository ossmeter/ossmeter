package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class AverageRepliesPerThreadData extends Pongo {
	
	
	
	public AverageRepliesPerThreadData() { 
		super();
		AVERAGEREPLIESPERTHREAD.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperthread.model.AverageRepliesPerThreadData");
	}
	
	public static NumericalQueryProducer AVERAGEREPLIESPERTHREAD = new NumericalQueryProducer("averageRepliesPerThread");
	
	
	public float getAverageRepliesPerThread() {
		return parseFloat(dbObject.get("averageRepliesPerThread")+"", 0.0f);
	}
	
	public AverageRepliesPerThreadData setAverageRepliesPerThread(float averageRepliesPerThread) {
		dbObject.put("averageRepliesPerThread", averageRepliesPerThread);
		notifyChanged();
		return this;
	}
	
	
	
	
}