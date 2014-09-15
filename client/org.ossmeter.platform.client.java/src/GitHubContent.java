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
	@Type(value = GitHubContent.class, name="GitHubContent"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubContent extends Object {

	protected String type;
	protected String envoding;
	protected int size;
	protected String name;
	protected String path;
	protected String sha;
	
	public String getType() {
		return type;
	}
	public String getEnvoding() {
		return envoding;
	}
	public int getSize() {
		return size;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public String getSha() {
		return sha;
	}
	
}
