package com.googlecode.pongo.tests.svnloc.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class SVNMetricDB extends PongoDB {
	
	public SVNMetricDB(DB db) {
		super(db);
		svnRepositorymetrics = new SvnRepositoryMetricCollection(db.getCollection("svnRepositorymetrics"));
		pongoCollections.add(svnRepositorymetrics);
		linesOfCodeMetrics = new LinesOfCodeMetricCollection(db.getCollection("linesOfCodeMetrics"));
		pongoCollections.add(linesOfCodeMetrics);
	}
	
	protected SvnRepositoryMetricCollection svnRepositorymetrics = null;
	protected LinesOfCodeMetricCollection linesOfCodeMetrics = null;
	
	public SvnRepositoryMetricCollection getSvnRepositorymetrics() {
		return svnRepositorymetrics;
	}
	
	public LinesOfCodeMetricCollection getLinesOfCodeMetrics() {
		return linesOfCodeMetrics;
	}
	
	
}