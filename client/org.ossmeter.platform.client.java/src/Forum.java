//package org.ossmeter.repository.model.cc.forum;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = Forum.class, name="Forum"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Forum extends CommunicationChannel {

	protected String name;
	protected String description;
	
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
}
