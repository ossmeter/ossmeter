package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.util.Iterator;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBCursor;
import com.mongodb.util.JSON;

public class ProjectListResource extends ServerResource {

	
	
	@Get("json")
    public String represent() {
		Series<Header> responseHeaders = (Series<Header>) getResponse().getAttributes().get("org.restlet.http.headers");
		if (responseHeaders == null) {
		    responseHeaders = new Series(Header.class);
		    getResponse().getAttributes().put("org.restlet.http.headers", responseHeaders);
		}
		responseHeaders.add(new Header("Access-Control-Allow-Origin", "*"));
		responseHeaders.add(new Header("Access-Control-Allow-Methods", "GET"));
	
		// Defaults
		int pageSize = 2;
		int page = 0;
		
		// Ready query params
		// TODO: May not want to fix the size of pages
		String _page = getQueryValue("page");
		String _size = getQueryValue("size");
		if (_page != null && !"".equals(_page) && isInteger(_page)) {
				page = Integer.valueOf(_page); 
		}
		if (_size != null && !"".equals(_size) && isInteger(_size)) {
			pageSize = Integer.valueOf(_size); 
		}
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		DBCursor cursor = projectRepo.getProjects().getDbCollection().find().skip(page*pageSize).limit(pageSize);
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode projects = mapper.createArrayNode();
		
		
		while (cursor.hasNext()) {
			try {
				projects.add(mapper.readTree(cursor.next().toString()));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		cursor.close();
		
		return projects.toString();
	}

	protected boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
}
