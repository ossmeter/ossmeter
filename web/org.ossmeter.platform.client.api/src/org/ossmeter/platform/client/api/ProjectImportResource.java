package org.ossmeter.platform.client.api;

import java.io.IOException;

import org.ossmeter.repository.model.Project;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ProjectImportResource extends ServerResource {

	@Post("json")
	public Representation importProject(Representation entity) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode obj = (ObjectNode)mapper.readTree(entity.getText());
			System.out.println(obj);
			String url = obj.get("url").toString();

			url = url.replace("\"", "");
			
			ProjectImporter importer = new ProjectImporter();
			Project p = importer.importProject(url);
			
			if (p == null) {
				ObjectNode msg = mapper.createObjectNode();
				msg.put("status", "error");
				msg.put("msg", "Unable to import project."); // FIXME inc reason
				StringRepresentation rep = new StringRepresentation(msg.toString());
				rep.setMediaType(MediaType.APPLICATION_JSON);
				getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return rep;
			}
			
//			String[] excludes = new String[]{"storage", "executionInformation","metricProviderData", "_superTypes","_id"};
//			FilterProvider fp = new SimpleFilterProvider().addFilter("foofilter", SimpleBeanPropertyFilter.serializeAllExcept(excludes));;
			
//			ObjectWriter writer = mapper.writer(fp);
//			String proj = writer.writeValueAsString(p.getDbObject());
			
			StringRepresentation rep = new StringRepresentation(p.getDbObject().toString());
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
	
	@JsonFilter("foofilter")
	class Foo {}
}
