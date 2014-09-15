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
	@Type(value = Donation.class, name="Donation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Donation extends Object {

	protected List<String> charities;
	protected String comment;
	protected String status;
	
	public String getComment() {
		return comment;
	}
	public String getStatus() {
		return status;
	}
	
	public List<String> getCharities() {
		return charities;
	}
}
