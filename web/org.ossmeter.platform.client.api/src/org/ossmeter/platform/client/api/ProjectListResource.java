package org.ossmeter.platform.client.api;

import java.io.IOException;
import java.util.Iterator;

import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.ProjectRepository;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.engine.header.Header;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

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
		
		 // Defaults
        int pageSize = 2;
        int page = 0;
        
        // Ready query params
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
            	DBObject p = cursor.next();
            	p.removeField("storage");
				p.removeField("metricProviderData");
				p.removeField("_superTypes");
				p.removeField("_id");
				
				// FIXME: Temporary solution
				p.removeField("licenses");
				p.removeField("persons");
				
				projects.add(mapper.readTree(p.toString()));
            } catch (Exception e) {
            	System.err.println("Error: " + e.getMessage());
				ObjectNode m = mapper.createObjectNode();
				m.put("apicall", "list-all-projects");
				return Util.generateErrorMessageRepresentation(m, e.getMessage());
            }
        }
        
        cursor.close();
        
        StringRepresentation resp = new StringRepresentation(projects.toString());
		resp.setMediaType(MediaType.APPLICATION_JSON);
		return resp;
		
		
//		// TODO
//		boolean paging = getRequest().getAttributes().containsKey("page");
//		
//		Platform platform = Platform.getInstance();
//		ProjectRepository projectRepo = platform.getProjectRepositoryManager().getProjectRepository();
//		
//		Iterator<Project> it = projectRepo.getProjects().iterator();
//	
//		ObjectMapper mapper = new ObjectMapper();
//		ArrayNode projects = mapper.createArrayNode();
//		
//		while (it.hasNext()) {
//			try {
//				Project project  = it.next();
//				DBObject p = project.getDbObject();
//				
//				p.removeField("storage");
//				p.removeField("metricProviderData");
//				p.removeField("_superTypes");
//				p.removeField("_id");
//				
//				// FIXME: Temporary solution
//				p.removeField("licenses");
//				p.removeField("persons");
//				
//				projects.add(mapper.readTree(p.toString())); //TODO: There must be a better way..
//				
//			} catch (Exception e) {
//				System.err.println("Error: " + e.getMessage());
//				ObjectNode m = mapper.createObjectNode();
//				m.put("apicall", "list-all-projects");
//				return Util.generateErrorMessageRepresentation(m, e.getMessage());
//			}			
//		}
//		StringRepresentation resp = new StringRepresentation(projects.toString());
//		resp.setMediaType(MediaType.APPLICATION_JSON);
//		return resp;
	}

	@Post("json")
	public Representation postProject(Representation entity) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode obj = (ObjectNode)mapper.readTree(entity.getText());
			System.out.println(obj);
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
			
			StringRepresentation rep = new StringRepresentation(obj.toString());
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.SUCCESS_CREATED);
			return rep;

		} catch (IOException e) {
			e.printStackTrace(); // TODO
			StringRepresentation rep = new StringRepresentation("");
			rep.setMediaType(MediaType.APPLICATION_JSON);
			getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return rep;
		}
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
