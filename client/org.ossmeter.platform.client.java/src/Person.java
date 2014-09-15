//package model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = Person.class, name="Person"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends NamedElement {

	protected List<Role> roles;
	protected String homePage;
	
	public String getHomePage() {
		return homePage;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
}
