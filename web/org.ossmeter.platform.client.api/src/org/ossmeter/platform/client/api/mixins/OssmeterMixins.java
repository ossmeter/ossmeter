package org.ossmeter.platform.client.api.mixins;

import org.ossmeter.platform.client.api.mixins.model.ProjectMixin;
import org.ossmeter.repository.model.NamedElement;
import org.ossmeter.repository.model.Project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.pongo.runtime.Pongo;

public class OssmeterMixins {

	private static ObjectMapper instance;
	
	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.addMixInAnnotations(Pongo.class, PongoMixin.class);
		mapper.addMixInAnnotations(NamedElement.class, NamedElementMixin.class);
		mapper.addMixInAnnotations(Project.class, ProjectMixin.class);
//		mapper.addMixInAnnotations(EclipseForgeProject.class, ProjectMixin.class);
		
		return mapper;
	}
} 