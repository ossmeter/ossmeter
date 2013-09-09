package org.ossmeter.platform.client.api.mixins;

import org.ossmeter.repository.model.Project;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.googlecode.pongo.runtime.Pongo;

public class OssmeterMixins {

	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.addMixInAnnotations(Pongo.class, PongoMixin.class);
		mapper.addMixInAnnotations(Project.class, ProjectMixin.class);
		
		return mapper;
	}
} 