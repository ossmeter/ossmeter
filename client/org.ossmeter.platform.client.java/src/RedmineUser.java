//package org.ossmeter.repository.model.redmine;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = RedmineUser.class, name="RedmineUser"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedmineUser extends Person {

	protected String login;
	
	public String getLogin() {
		return login;
	}
	
}
