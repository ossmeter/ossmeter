package org.ossmeter.platform.client.api;

import java.io.IOException;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class ProjectResource extends ServerResource {
	@Get("json")
	public Representation represent() {	
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
		
		String projectId = (String) getRequest().getAttributes().get("projectid");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		ObjectMapper mapper = new ObjectMapper();
		
		// FIXME: This exclusion list needs to be somewhere... 
		BasicDBObject ex = new BasicDBObject("executionInformation", 0);
		ex.put("storage", 0);
		ex.put("metricProviderData", 0);
		ex.put("_superTypes", 0);
		ex.put("_id", 0);
		
		BasicDBObject query = new BasicDBObject("shortName", projectId);
		
		System.out.println(query);
		DBObject p = projectRepo.getProjects().getDbCollection().findOne(query, ex);
		
		if (p == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, projectId), "No project was found with the requested name.");
		}
		
		try {
			StringRepresentation resp = new StringRepresentation(p.toString());
			resp.setMediaType(MediaType.APPLICATION_JSON);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			getResponse().setStatus(Status.SERVER_ERROR_INTERNAL);
			return Util.generateErrorMessageRepresentation(generateRequestJson(mapper, projectId), "An error occurred when converting the project to JSON: " + e.getMessage());
		}
	}
	
	/**
	 * This is an update to the existing project (identified by projectId field)
	 * @param entity
	 */
	@Put("json")
	public void updateProject(JacksonRepresentation<ObjectNode> entity) {
		
	}
	
	private JsonNode generateRequestJson(ObjectMapper mapper, String projectName) {
		ObjectNode n = mapper.createObjectNode();
		n.put("project", projectName);
		return n;
	}
	
}
