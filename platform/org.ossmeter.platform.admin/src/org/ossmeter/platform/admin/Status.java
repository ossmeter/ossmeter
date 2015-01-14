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

import org.restlet.representation.EmptyRepresentation;
import org.restlet.representation.Representation;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.ReplicaSetStatus;
import com.mongodb.ServerAddress;

public class Status extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
		String what = (String) getRequest().getAttributes().get("what");
		
		System.out.println(what);
		
		if ("server".equals(what)) {
			DBCollection col = mongo.getDB("ossmeter").getCollection("schedulingInformation");
			DBCursor cursor = col.find();

			ArrayNode res = mapper.createArrayNode();
			
			while(cursor.hasNext()) {
				DBObject obj = cursor.next();
				ObjectNode node = mapper.createObjectNode();
				
				node.put("worker", obj.get("workerIdentifier").toString());
				ArrayNode load = mapper.createArrayNode();
				BasicDBList list = (BasicDBList)obj.get("currentLoad");
				
				for (Object o : list) {
					load.add(o.toString());
				}
				
				node.put("load", load);
				node.put("heartbeat", (long)obj.get("heartbeat"));
				
				res.add(node);
			}
			
			return Util.createJsonRepresentation(res);
		} else if("projects".equals(what)) {
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
		} else if ("database".equals(what)) {
			ArrayNode res = mapper.createArrayNode();
			ReplicaSetStatus rss = mongo.getReplicaSetStatus();
			for (ServerAddress sa : mongo.getAllAddress()) {
				ObjectNode node = mapper.createObjectNode();
				node.put("host", sa.getHost());
				node.put("port", sa.getPort());
				if (rss != null) node.put("isMaster", rss.isMaster(sa));
				res.add(node);
			}
			return Util.createJsonRepresentation(res);
		}
		
		return new EmptyRepresentation();
	}
}
