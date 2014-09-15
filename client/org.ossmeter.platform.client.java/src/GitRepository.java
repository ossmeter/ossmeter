//package org.ossmeter.repository.model.vcs.git;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitRepository.class, name="GitRepository"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitRepository extends VcsRepository {

	protected String username;
	protected String password;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
