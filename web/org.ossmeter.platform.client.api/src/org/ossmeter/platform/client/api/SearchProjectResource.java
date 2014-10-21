package org.ossmeter.platform.client.api;

import java.util.Iterator;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class SearchProjectResource extends ServerResource {
	
	/**
	 * Query: 
	 * 		/search?q=epsilon
	 * TODO: Implement soundex?
	 * @return
	 */
	@Get
	public Representation represent() {
		ObjectMapper mapper = new ObjectMapper();
		String query = getQueryValue("q");
		
		if (query == null || "".equals(query)){
			ObjectNode obj = mapper.createObjectNode();
			obj.put("msg", "No search term specified.");
			return Util.createJsonRepresentation(obj);
		}
		// TODO: need to escape the query
		query = "/^" + query + "$/i";
		
		ProjectRepository repo = Platform.getInstance().getProjectRepositoryManager().getProjectRepository();
		Iterator<Project> it = repo.getProjects().findByName(query).iterator();
		
		if (it.hasNext()) {
			ArrayNode arr = mapper.createArrayNode();
			while (it.hasNext()) {
				Project p = it.next();
				ObjectNode obj = mapper.createObjectNode();
				obj.put("id", p.getShortName()); // TODO
				obj.put("name", p.getName());
				obj.put("description", p.getDescription());
				arr.add(obj);
			}
			return Util.createJsonRepresentation(arr);
		} else {
			ObjectNode obj = mapper.createObjectNode();
			obj.put("msg", "No projects matched that query.");
			return Util.createJsonRepresentation(obj);
		}
	}
}