package org.ossmeter.platform.client.api;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.client.api.mixins.ProjectMixin;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectResource extends ServerResource {
	@Get
	public String represent() {
		String projectName = (String) getRequest().getAttributes().get("name");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(Project.class, ProjectMixin.class);
		
		
		Project p = projectRepo.getProjects().findById(projectName).iterator().next();
		
		try {
			return mapper.writeValueAsString(p);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{ \"request\": \"\", \"error\": \""+e.getMessage()+"\"}";
		}
	}
}
