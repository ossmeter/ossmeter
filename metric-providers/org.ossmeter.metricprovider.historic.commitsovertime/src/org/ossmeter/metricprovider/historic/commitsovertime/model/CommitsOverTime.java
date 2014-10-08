package org.ossmeter.metricprovider.historic.commitsovertime.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


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