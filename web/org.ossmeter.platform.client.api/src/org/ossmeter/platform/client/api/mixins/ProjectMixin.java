package org.ossmeter.platform.client.api.mixins;

import java.util.List;
import org.ossmeter.repository.model.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.NONE, 
				getterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY, 
				setterVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY)
public abstract class ProjectMixin extends PongoMixin {

	@JsonIgnore
	abstract LocalStorage getStorage();
	
}