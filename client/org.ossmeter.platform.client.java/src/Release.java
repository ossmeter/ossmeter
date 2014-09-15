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
	@Type(value = Release.class, name="Release"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Release extends NamedElement {

	protected String type;
	protected String date;
	protected String link;
	
	public String getType() {
		return type;
	}
	public String getDate() {
		return date;
	}
	public String getLink() {
		return link;
	}
	
}
