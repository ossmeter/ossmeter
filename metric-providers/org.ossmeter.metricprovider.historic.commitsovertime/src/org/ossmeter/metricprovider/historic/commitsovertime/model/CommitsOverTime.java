/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
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