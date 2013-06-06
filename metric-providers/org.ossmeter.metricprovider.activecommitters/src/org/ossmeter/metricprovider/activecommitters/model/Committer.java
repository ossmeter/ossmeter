package org.ossmeter.metricprovider.activecommitters.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Committer extends Pongo {
	
	
	
	public Committer() { 
		super();
		COMMITTERID.setOwningType("org.ossmeter.metricprovider.activecommitters.model.Committer");
		LASTACTIVITYDATE.setOwningType("org.ossmeter.metricprovider.activecommitters.model.Committer");
	}
	
	public static StringQueryProducer COMMITTERID = new StringQueryProducer("committerId"); 
	public static StringQueryProducer LASTACTIVITYDATE = new StringQueryProducer("lastActivityDate"); 
	
	
	public String getCommitterId() {
		return parseString(dbObject.get("committerId")+"", "");
	}
	
	public Committer setCommitterId(String committerId) {
		dbObject.put("committerId", committerId);
		notifyChanged();
		return this;
	}
	public String getLastActivityDate() {
		return parseString(dbObject.get("lastActivityDate")+"", "");
	}
	
	public Committer setLastActivityDate(String lastActivityDate) {
		dbObject.put("lastActivityDate", lastActivityDate);
		notifyChanged();
		return this;
	}
	
	
	
	
}