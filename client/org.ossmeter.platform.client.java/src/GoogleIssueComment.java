//package org.ossmeter.repository.model.googlecode;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GoogleIssueComment.class, name="GoogleIssueComment"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleIssueComment extends Object {

	protected String text;
	protected String date;
	
	public String getText() {
		return text;
	}
	public String getDate() {
		return date;
	}
	
}
