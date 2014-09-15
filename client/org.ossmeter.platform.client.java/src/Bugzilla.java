//package org.ossmeter.repository.model.bts.bugzilla;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = Bugzilla.class, name="Bugzilla"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bugzilla extends BugTrackingSystem {

	protected String username;
	protected String password;
	protected String product;
	protected String component;
	protected String cgiQueryProgram;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getProduct() {
		return product;
	}
	public String getComponent() {
		return component;
	}
	public String getCgiQueryProgram() {
		return cgiQueryProgram;
	}
	
}
