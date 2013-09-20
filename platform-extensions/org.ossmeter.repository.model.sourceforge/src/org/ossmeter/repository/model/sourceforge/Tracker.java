package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Tracker extends org.ossmeter.repository.model.NamedElement {
	
	
	
	public Tracker() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.ossmeter.repository.model.sourceforge.Tracker");
		STATUS.setOwningType("org.ossmeter.repository.model.sourceforge.Tracker");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	
	
	public String getLocation() {
		return parseString(dbObject.get("location")+"", "");
	}
	
	public Tracker setLocation(String location) {
		dbObject.put("location", location);
		notifyChanged();
		return this;
	}
	public TrackerStatus getStatus() {
		TrackerStatus status = null;
		try {
			status = TrackerStatus.valueOf(dbObject.get("status")+"");
		}
		catch (Exception ex) {}
		return status;
	}
	
	public Tracker setStatus(TrackerStatus status) {
		dbObject.put("status", status.toString());
		notifyChanged();
		return this;
	}
	
	
	
	
}