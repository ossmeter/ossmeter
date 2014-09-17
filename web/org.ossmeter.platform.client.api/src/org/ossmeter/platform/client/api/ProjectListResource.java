package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.util.Iterator;

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
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ProjectListResource extends ServerResource {

	@Get("json")
    public Representation represent() {
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
				return Util.generateErrorMessageRepresentation(m, e.getMessage());
			}			
		}
		StringRepresentation resp = new StringRepresentation(projects.toString());
		resp.setMediaType(MediaType.APPLICATION_JSON);
		return resp;
	}

	@Post
	public Representation postProject(JacksonRepresentation<ObjectNode> entity) {
		
		try {
			ObjectNode obj = entity.getObject();
			String name = obj.get("name").toString();
			ProjectRepository repo = Platform.getInstance().getProjectRepositoryManager().getProjectRepository();

			Project existing = repo.getProjects().findOneByName(name);
			if (existing != null) {
				StringRepresentation rep = new StringRepresentation(""); // TODO
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.CLIENT_ERROR_CONFLICT);
				return rep;
			}
			
			Project project = new Project();
			project.setName(name);
			project.setShortName(obj.get("name").toString());
			project.setDescription(obj.get("description").toString());
			
			repo.getProjects().add(project);
			repo.sync();
			
		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		}
		
		StringRepresentation rep = new StringRepresentation("");
		rep.setMediaType(MediaType.APPLICATION_JSON);
		getResponse().setStatus(Status.SUCCESS_CREATED);
		return rep;
	}
	
}
