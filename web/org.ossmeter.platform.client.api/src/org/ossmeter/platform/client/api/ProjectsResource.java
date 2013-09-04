package org.ossmeter.platform.client.api;

import java.io.StringWriter;
import java.util.Iterator;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.client.api.mixins.ProjectMixin;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectsResource extends ServerResource {

	@Get("json")
    public String represent() {
		boolean paging = getRequest().getAttributes().containsKey("page");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(Project.class, ProjectMixin.class);
		
		Iterator<Project> it = projectRepo.getProjects().iterator();
	
		String json = "{ \"projects\" : [ ";
		
		while (it.hasNext()) {
			try {
				Project project  = it.next();
				json += mapper.writeValueAsString(project);
				if (it.hasNext()) json+=",";
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				return "{ \"request\": \"\", \"error\": \""+e.getMessage()+"\"}";
			}			
		}
		
		json += " ] }";
		return json;
		
//		if (paging){
//			int page = Integer.valueOf((String)getRequest().getAttributes().get("page"));
//			return "{ page : " + page + " }";
//		} else {
//			return "{ page : 'all'}";
//		}
	}

	
}
