package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class AverageRequestsPerUserData extends Pongo {
	
	
	
	public AverageRequestsPerUserData() { 
		super();
		AVERAGEREQUESTSPERUSER.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model.AverageRequestsPerUserData");
	}
	
	public static NumericalQueryProducer AVERAGEREQUESTSPERUSER = new NumericalQueryProducer("averageRequestsPerUser");
	
	
	public float getAverageRequestsPerUser() {
		return parseFloat(dbObject.get("averageRequestsPerUser")+"", 0.0f);
	}
	
	public AverageRequestsPerUserData setAverageRequestsPerUser(float averageRequestsPerUser) {
		dbObject.put("averageRequestsPerUser", averageRequestsPerUser);
		notifyChanged();
		return this;
	}
	
	
	
	
}