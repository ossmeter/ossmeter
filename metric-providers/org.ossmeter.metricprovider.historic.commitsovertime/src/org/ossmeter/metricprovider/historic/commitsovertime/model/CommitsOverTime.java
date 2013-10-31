package org.ossmeter.metricprovider.historic.commitsovertime.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class CommitsOverTime extends Pongo {
	
	protected List<RepositoryData> repositories = null;
	
	
	public CommitsOverTime() { 
		super();
		dbObject.put("repositories", new BasicDBList());
	}
	
	
	
	
	
	public List<RepositoryData> getRepositories() {
		if (repositories == null) {
			repositories = new PongoList<RepositoryData>(this, "repositories", true);
		}
		return repositories;
	}
	
	
}