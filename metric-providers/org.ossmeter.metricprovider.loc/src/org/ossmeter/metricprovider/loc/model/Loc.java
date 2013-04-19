package org.ossmeter.metricprovider.loc.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class Loc extends PongoDB {
	
	public Loc() {}
	
	public Loc(DB db) {
		setDb(db);
	}
	
	protected RepositoryDataCollection repositories = null;
	protected LinesOfCodeDataCollection linesOfCode = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public RepositoryDataCollection getRepositories() {
		return repositories;
	}
	
	public LinesOfCodeDataCollection getLinesOfCode() {
		return linesOfCode;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		repositories = new RepositoryDataCollection(db.getCollection("Loc.repositories"));
		pongoCollections.add(repositories);
		linesOfCode = new LinesOfCodeDataCollection(db.getCollection("Loc.linesOfCode"));
		pongoCollections.add(linesOfCode);
	}
}