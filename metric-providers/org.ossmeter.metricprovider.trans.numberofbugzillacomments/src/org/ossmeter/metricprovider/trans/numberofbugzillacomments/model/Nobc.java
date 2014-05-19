package org.ossmeter.metricprovider.trans.numberofbugzillacomments.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Nobc extends PongoDB {
	
	public Nobc() {}
	
	public Nobc(DB db) {
		setDb(db);
	}
	
	protected BugzillaDataCollection bugzillas = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugzillaDataCollection getBugzillas() {
		return bugzillas;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugzillas = new BugzillaDataCollection(db.getCollection("Nobc.bugzillas"));
		pongoCollections.add(bugzillas);
	}
}