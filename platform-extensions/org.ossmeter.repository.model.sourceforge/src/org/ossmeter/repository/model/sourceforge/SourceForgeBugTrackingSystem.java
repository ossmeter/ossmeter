package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SourceForgeBugTrackingSystem extends org.ossmeter.repository.model.BugTrackingSystem {
	
	protected List<BugTS> bugsTS = null;
	
	
	public SourceForgeBugTrackingSystem() { 
		super();
		dbObject.put("bugsTS", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.BugTrackingSystem");
	}
	
	
	
	
	
	public List<BugTS> getBugsTS() {
		if (bugsTS == null) {
			bugsTS = new PongoList<BugTS>(this, "bugsTS", true);
		}
		return bugsTS;
	}
	
	
}