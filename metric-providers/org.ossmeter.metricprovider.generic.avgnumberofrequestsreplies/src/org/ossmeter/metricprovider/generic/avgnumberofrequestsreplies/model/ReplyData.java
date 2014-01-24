package org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class ReplyData extends Pongo {
	
	
	
	public ReplyData() { 
		super();
		AVERAGEREPLIES.setOwningType("org.ossmeter.metricprovider.generic.avgnumberofrequestsreplies.model.ReplyData");
	}
	
	public static NumericalQueryProducer AVERAGEREPLIES = new NumericalQueryProducer("averageReplies");
	
	
	public float getAverageReplies() {
		return parseFloat(dbObject.get("averageReplies")+"", 0.0f);
	}
	
	public ReplyData setAverageReplies(float averageReplies) {
		dbObject.put("averageReplies", averageReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}