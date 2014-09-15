//package org.ossmeter.repository.model.github;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubUser.class, name="GitHubUser"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUser extends Person {

	protected String login;
	protected String html_url;
	protected String url;
	protected String followers_url;
	
	public String getLogin() {
		return login;
	}
	public String getHtml_url() {
		return html_url;
	}
	public String getUrl() {
		return url;
	}
	public String getFollowers_url() {
		return followers_url;
	}
	
}
