package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

//protected region custom-imports on begin
//protected region custom-imports end


public class SourceForgeBugTrackingSystem extends org.ossmeter.repository.model.BugTrackingSystem {
	
	protected List<BugTS> bugsTS = null;
	
	// protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "sourceforge";
    }

    @Override
    public String getInstanceId() {
        return getUrl();
    }

    // protected region custom-fields-and-methods end
	
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