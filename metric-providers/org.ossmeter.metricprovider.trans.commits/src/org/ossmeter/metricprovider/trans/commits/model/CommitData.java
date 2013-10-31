package org.ossmeter.metricprovider.trans.commits.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class CommitData extends Pongo {
	
	
	
	public CommitData() { 
		super();
		DATE.setOwningType("org.ossmeter.metricprovider.trans.commits.model.CommitData");
		TIME.setOwningType("org.ossmeter.metricprovider.trans.commits.model.CommitData");
	}
	
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	public static StringQueryProducer TIME = new StringQueryProducer("time"); 
	
	
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public CommitData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	public String getTime() {
		return parseString(dbObject.get("time")+"", "");
	}
	
	public CommitData setTime(String time) {
		dbObject.put("time", time);
		notifyChanged();
		return this;
	}
	
	
	
	
}