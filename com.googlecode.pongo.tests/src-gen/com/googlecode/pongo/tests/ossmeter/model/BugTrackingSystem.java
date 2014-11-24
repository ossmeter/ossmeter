package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class BugTrackingSystem extends Tracker {
	
	protected List<Bug> bugs = null;
	
	
	public BugTrackingSystem() { 
		super();
		dbObject.put("bugs", new BasicDBList());
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.Tracker","com.googlecode.pongo.tests.ossmeter.model.NamedElement");
		NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.BugTrackingSystem");
		LOCATION.setOwningType("com.googlecode.pongo.tests.ossmeter.model.BugTrackingSystem");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	
	
	
	
	public List<Bug> getBugs() {
		if (bugs == null) {
			bugs = new PongoList<Bug>(this, "bugs", true);
		}
		return bugs;
	}
	
	
}