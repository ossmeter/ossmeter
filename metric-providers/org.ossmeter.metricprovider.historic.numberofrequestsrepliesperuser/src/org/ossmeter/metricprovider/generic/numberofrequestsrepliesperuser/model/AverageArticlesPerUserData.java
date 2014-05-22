package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class AverageArticlesPerUserData extends Pongo {
	
	
	
	public AverageArticlesPerUserData() { 
		super();
		AVERAGEARTICLESPERUSER.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model.AverageArticlesPerUserData");
	}
	
	public static NumericalQueryProducer AVERAGEARTICLESPERUSER = new NumericalQueryProducer("averageArticlesPerUser");
	
	
	public float getAverageArticlesPerUser() {
		return parseFloat(dbObject.get("averageArticlesPerUser")+"", 0.0f);
	}
	
	public AverageArticlesPerUserData setAverageArticlesPerUser(float averageArticlesPerUser) {
		dbObject.put("averageArticlesPerUser", averageArticlesPerUser);
		notifyChanged();
		return this;
	}
	
	
	
	
}