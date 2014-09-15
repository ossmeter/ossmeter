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
	@Type(value = Request.class, name="Request"), 	@Type(value = FeatureRequest.class, name="FeatureRequest"),
	@Type(value = SupportRequest.class, name="SupportRequest"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Request extends Tracker {

	protected String summary;
	protected String created_at;
	protected String updated_at;
	protected Person creator;
	
	public String getSummary() {
		return summary;
	}
	public String getCreated_at() {
		return created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	
}
