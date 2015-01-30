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
package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.net.UnknownHostException;

import org.ossmeter.platform.Configuration;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class ProjectImportResource extends ServerResource {

	@Post("json")
	public Representation importProject(Representation entity) {
		
		Mongo mongo = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode obj = (ObjectNode)mapper.readTree(entity.getText());
			System.out.println(obj);
			String url = obj.get("url").toString();

			url = url.replace("\"", "");
			
			try {
				mongo = Configuration.getInstance().getMongoConnection();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
				return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, null), "The API was unable to connect to the database.");
			}
			Platform platform = new Platform(mongo);
			
			ProjectImporter importer = new ProjectImporter();
			
			Project p = importer.importProject(url, platform);
			
			if (p == null) {
				ObjectNode msg = mapper.createObjectNode();
				msg.put("status", "error");
				msg.put("msg", "Unable to import project."); // FIXME inc reason
				StringRepresentation rep = new StringRepresentation(msg.toString());
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return rep;
			}
			
			// Clean up the object
			DBObject project = p.getDbObject();
			project.removeField("storage");
			project.removeField("metricProviderData");
			project.removeField("_superTypes");
			project.removeField("_id");
			
			// FIXME: Temporary solution
			project.removeField("licenses");
			project.removeField("persons");
			
			StringRepresentation rep = new StringRepresentation(p.getDbObject().toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_CREATED);
			
			mongo.close();
			return rep;

		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		} finally {
			if (mongo != null) mongo.close();
		}
	}
	
	@JsonFilter("foofilter")
	class Foo {}
	
	protected JsonNode generateRequestJson(ObjectMapper mapper, String projectName) {
		ObjectNode n = mapper.createObjectNode();
		n.put("project", projectName);
		return n;
	}
}
