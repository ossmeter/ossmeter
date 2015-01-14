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
package org.ossmeter.platform.admin;

import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class StatusProjects extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		DBCollection col = mongo.getDB("ossmeter").getCollection("projects");
		
		BasicDBObject analysedQuery = new BasicDBObject("executionInformation.analysed", true);
		BasicDBObject inErrorQuery = new BasicDBObject("executionInformation.inErrorState", true);
		
		long numProjects = col.count();
		long numAnalysed = col.count(analysedQuery);
		long numError = col.count(inErrorQuery);
		
		ObjectNode res = mapper.createObjectNode();
		res.put("Number of projects", numProjects);
		res.put("Number of projects analysed", numAnalysed);
		res.put("Number of projects in error", numError);
		
		return Util.createJsonRepresentation(res);
	}
}
