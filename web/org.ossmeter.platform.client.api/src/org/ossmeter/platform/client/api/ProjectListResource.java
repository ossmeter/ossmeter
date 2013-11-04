package org.ossmeter.platform.client.api;

import java.util.Iterator;

import org.ossmeter.platform.Platform;
import org.ossmeter.platform.client.api.mixins.NamedElementMixin;
import org.ossmeter.platform.client.api.mixins.PongoMixin;
import org.ossmeter.platform.client.api.mixins.ProjectOverviewMixin;
import org.ossmeter.repository.model.NamedElement;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.pongo.runtime.Pongo;

public class ProjectListResource extends ServerResource {

	@Get("json")
    public String represent() {
		boolean paging = getRequest().getAttributes().containsKey("page");
		
		Platform platform = Platform.getInstance();
		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.addMixInAnnotations(Pongo.class, PongoMixin.class);
		mapper.addMixInAnnotations(NamedElement.class, NamedElementMixin.class);
		//FIXME: this mixin isn't auto-generated. What shall we do about that?
		// (It's not necessarily a bad thing)
		mapper.addMixInAnnotations(Project.class, ProjectOverviewMixin.class);
		
		Iterator<Project> it = projectRepo.getProjects().iterator();
	
		String json = "{ \"projects\" : [ ";
		
		while (it.hasNext()) {
			try {
				Project project  = it.next();
				json += mapper.writeValueAsString(project);
				if (it.hasNext()) json+=",";
			} catch (Exception e) {
				System.err.println("Error: " + e.getMessage());
				e.printStackTrace();
				return Util.generateErrorMessage("list-all-projects", e.getMessage());
			}			
		}
		
		
		
		json += " ] }";
		return json;
		
//		TODO:		
//		if (paging){
//			int page = Integer.valueOf((String)getRequest().getAttributes().get("page"));
//			return "{ page : " + page + " }";
//		} else {
//			return "{ page : 'all'}";
//		}
	}

	
}
