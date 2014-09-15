//package org.ossmeter.repository.model.sourceforge;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = Tracker.class, name="Tracker"), 	@Type(value = BugTS.class, name="BugTS"),
	@Type(value = Request.class, name="Request"),
	@Type(value = Patch.class, name="Patch"),
	@Type(value = Bug.class, name="Bug"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Tracker extends NamedElement {

	protected String location;
	protected String status;
	
	public String getLocation() {
		return location;
	}
	public String getStatus() {
		return status;
	}
	
}
