package org.ossmeter.platform.client.api.mixins.model;

import org.ossmeter.repository.model.LocalStorage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.NONE, 
				getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, 
				setterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public abstract class ProjectMixin {
	
	@JsonIgnore
	abstract LocalStorage getStorage();
	
}