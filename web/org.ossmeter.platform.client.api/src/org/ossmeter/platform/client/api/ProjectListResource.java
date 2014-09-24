package org.ossmeter.platform.client.api;

import java.util.Iterator;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.engine.header.Header;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
		
		// TODO
		boolean paging = getRequest().getAttributes().containsKey("page");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		Iterator<Project> it = projectRepo.getProjects().iterator();
	
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode projects = mapper.createArrayNode();
		
		while (it.hasNext()) {
			try {
				Project project  = it.next();
				
				ObjectNode p = mapper.createObjectNode();
				p.put("name", project.getName());
				p.put("description", project.getDescription());
				
				projects.add(p);
				
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				ObjectNode m = mapper.createObjectNode();
				m.put("apicall", "list-all-projects");
				return Util.generateErrorMessage(m, e.getMessage()).toString();
			}			
		}
		return projects.toString();
	}

	
}
