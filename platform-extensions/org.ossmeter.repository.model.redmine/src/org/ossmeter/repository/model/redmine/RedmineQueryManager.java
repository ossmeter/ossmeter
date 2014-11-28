/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model.redmine;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


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