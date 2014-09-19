package org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class DailyNewsgroupRepliesData extends Pongo {
	
	
	
	public DailyNewsgroupRepliesData() { 
		super();
		NUMBEROFREPLIES.setOwningType("org.ossmeter.metricprovider.historic.numberofrequestsrepliesperday.model.DailyNewsgroupRepliesData");
	}
	
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	
	
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public DailyNewsgroupRepliesData setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}