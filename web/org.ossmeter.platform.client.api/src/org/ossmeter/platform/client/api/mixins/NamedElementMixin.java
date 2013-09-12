package org.ossmeter.platform.client.api.mixins;

import java.util.List;
import org.ossmeter.repository.model.*;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect( fieldVisibility = JsonAutoDetect.Visibility.NONE, 
				getterVisibility = JsonAutoDetect.Visibility.NONE, 
				setterVisibility = JsonAutoDetect.Visibility.NONE)
public abstract class NamedElementMixin  {

	@JsonGetter("name")
	abstract String getName();

}