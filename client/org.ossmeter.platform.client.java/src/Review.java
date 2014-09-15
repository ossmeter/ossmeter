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
	@Type(value = Review.class, name="Review"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Review extends Object {

	protected String type;
	protected String state;
	protected String endDate;
	
	public String getType() {
		return type;
	}
	public String getState() {
		return state;
	}
	public String getEndDate() {
		return endDate;
	}
	
}
