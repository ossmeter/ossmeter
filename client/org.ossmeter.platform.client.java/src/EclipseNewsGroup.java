//package org.ossmeter.repository.model.eclipse;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = EclipseNewsGroup.class, name="EclipseNewsGroup"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class EclipseNewsGroup extends NntpNewsGroup {

	protected String type;
	
	public String getType() {
		return type;
	}
	
}
