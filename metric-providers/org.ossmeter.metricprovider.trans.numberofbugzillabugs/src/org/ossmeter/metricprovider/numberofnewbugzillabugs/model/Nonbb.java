package org.ossmeter.metricprovider.numberofnewbugzillabugs.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Nonbb extends PongoDB {
	
	public Nonbb() {}
	
	public Nonbb(DB db) {
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
		bugzillas = new BugzillaDataCollection(db.getCollection("Nonbb.bugzillas"));
		pongoCollections.add(bugzillas);
	}
}