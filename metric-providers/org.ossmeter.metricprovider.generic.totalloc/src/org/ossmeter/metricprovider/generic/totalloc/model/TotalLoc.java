package org.ossmeter.metricprovider.generic.totalloc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class TotalLoc extends Pongo {
	
	protected List<TLocRepositoryData> repositories = null;
	
	
	public TotalLoc() { 
		super();
		dbObject.put("repositories", new BasicDBList());
	}
	
	
	
	public List<TLocRepositoryData> getRepositories() {
		if (repositories == null) {
			repositories = new PongoList<TLocRepositoryData>(this, "repositories", true);
		}
		return repositories;
	}
	
	
}