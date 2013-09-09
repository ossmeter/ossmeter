package org.ossmeter.platform.client.api.mixins;

import java.util.List;
import org.ossmeter.repository.model.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;

@JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.NONE, 
				getterVisibility = JsonAutoDetect.Visibility.NONE, 
				setterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class ProjectOverviewMixin {

	@JsonGetter("shortName")
	abstract String getShortName();
	
	@JsonGetter("description")
	abstract String getDescription();
	
	@JsonGetter("year")
	abstract int getYear();
	
	@JsonGetter("active")
	abstract boolean getActive();
	
}