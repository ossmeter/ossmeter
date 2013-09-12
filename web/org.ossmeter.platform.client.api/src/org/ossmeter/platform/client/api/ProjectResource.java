package org.ossmeter.platform.client.api;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.client.api.mixins.OssmeterMixins;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ProjectResource extends ServerResource {
	@Get
	public String represent() {
		String projectName = (String) getRequest().getAttributes().get("name");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		ObjectMapper mapper = OssmeterMixins.getObjectMapper();
		
		Project p = projectRepo.getProjects().findOneByShortName(projectName);
		
		if (p == null) {
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return Util.generateErrorMessage(generateRequestJson(projectName), "No project was found with the requested name.");
		}
		
		try {
			return p.getDbObject().toString();//mapper.writeValueAsString(p);//
		} catch (Exception e) {
			e.printStackTrace();
			return Util.generateErrorMessage(generateRequestJson(projectName), "An error occurred when converting the project to JSON: " + e.getMessage());
		}
	}
	
	private String generateRequestJson(String projectName) {
		return "{\"project\" : \"" + projectName + "\" }";
	}
	
}
