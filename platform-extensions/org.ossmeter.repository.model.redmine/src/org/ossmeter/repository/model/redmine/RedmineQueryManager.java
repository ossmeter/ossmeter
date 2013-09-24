package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RedmineQueryManager extends org.ossmeter.repository.model.CommunicationChannel {
	
	protected List<RedmineQuery> queries = null;
	
	
	public RedmineQueryManager() { 
		super();
		dbObject.put("queries", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.redmine.CommunicationChannel");
	}
	
	
	
	
	
	public List<RedmineQuery> getQueries() {
		if (queries == null) {
			queries = new PongoList<RedmineQuery>(this, "queries", true);
		}
		return queries;
	}
	
	
}