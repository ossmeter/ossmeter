package org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class AverageRepliesPerUserData extends Pongo {
	
	
	
	public AverageRepliesPerUserData() { 
		super();
		AVERAGEREPLIESPERUSER.setOwningType("org.ossmeter.metricprovider.generic.numberofrequestsrepliesperuser.model.AverageRepliesPerUserData");
	}
	
	public static NumericalQueryProducer AVERAGEREPLIESPERUSER = new NumericalQueryProducer("averageRepliesPerUser");
	
	
	public float getAverageRepliesPerUser() {
		return parseFloat(dbObject.get("averageRepliesPerUser")+"", 0.0f);
	}
	
	public AverageRepliesPerUserData setAverageRepliesPerUser(float averageRepliesPerUser) {
		dbObject.put("averageRepliesPerUser", averageRepliesPerUser);
		notifyChanged();
		return this;
	}
	
	
	
	
}