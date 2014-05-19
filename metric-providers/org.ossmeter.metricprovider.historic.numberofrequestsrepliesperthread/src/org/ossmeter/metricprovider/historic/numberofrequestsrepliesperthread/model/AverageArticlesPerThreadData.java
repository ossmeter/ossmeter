package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperthread.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class AverageArticlesPerThreadData extends Pongo {
	
	
	
	public AverageArticlesPerThreadData() { 
		super();
		AVERAGEARTICLESPERTHREAD.setOwningType("org.ossmeter.metricprovider.historic.numberofrequestsrepliesperthread.model.AverageArticlesPerThreadData");
	}
	
	public static NumericalQueryProducer AVERAGEARTICLESPERTHREAD = new NumericalQueryProducer("averageArticlesPerThread");
	
	
	public float getAverageArticlesPerThread() {
		return parseFloat(dbObject.get("averageArticlesPerThread")+"", 0.0f);
	}
	
	public AverageArticlesPerThreadData setAverageArticlesPerThread(float averageArticlesPerThread) {
		dbObject.put("averageArticlesPerThread", averageArticlesPerThread);
		notifyChanged();
		return this;
	}
	
	
	
	
}