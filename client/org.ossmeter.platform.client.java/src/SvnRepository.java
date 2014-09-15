//package org.ossmeter.repository.model.vcs.svn;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = SvnRepository.class, name="SvnRepository"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class SvnRepository extends VcsRepository {

	protected String browse;
	protected String username;
	protected String password;
	
	public String getBrowse() {
		return browse;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
