package org.ossmeter.metricprovider.rascal.metric.trans.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Loc extends PongoDB {
	
	public Loc() {}
	
	public Loc(DB db) {
		setDb(db);
	}
	
	protected LinesOfCodeDataCollection linesOfCode = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public LinesOfCodeDataCollection getLinesOfCode() {
		return linesOfCode;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		linesOfCode = new LinesOfCodeDataCollection(db.getCollection("Loc.linesOfCode"));
		pongoCollections.add(linesOfCode);
	}
}