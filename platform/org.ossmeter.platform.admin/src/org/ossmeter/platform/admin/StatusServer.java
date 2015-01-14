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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class StatusServer extends AbstractApiResource {

	@Override
	public Representation doRepresent() {
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
	}
}
