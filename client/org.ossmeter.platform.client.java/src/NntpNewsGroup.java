//package org.ossmeter.repository.model.cc.nntp;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = NntpNewsGroup.class, name="NntpNewsGroup"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class NntpNewsGroup extends CommunicationChannel {

	protected boolean authenticationRequired;
	protected String username;
	protected String password;
	protected int port;
	protected String description;
	protected String name;
	protected int interval;
	protected String lastArticleChecked;
	
	public boolean getAuthenticationRequired() {
		return authenticationRequired;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getPort() {
		return port;
	}
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
	public int getInterval() {
		return interval;
	}
	public String getLastArticleChecked() {
		return lastArticleChecked;
	}
	
}
