package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class SourceForgeBugTrackingSystem extends org.ossmeter.repository.model.BugTrackingSystem {
	
	protected List<Bug> bugs = null;
	
	
	public SourceForgeBugTrackingSystem() { 
		super();
		dbObject.put("bugs", new BasicDBList());
	}
	
	
	
	public List<Bug> getBugs() {
		if (bugs == null) {
			bugs = new PongoList<Bug>(this, "bugs", true);
		}
		return bugs;
	}
	
	
}