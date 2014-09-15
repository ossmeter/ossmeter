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
	@Type(value = GoogleCodeProject.class, name="GoogleCodeProject"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleCodeProject extends Project {

	protected List<GoogleDownload> downloads;
	protected GoogleWiki wiki;
	protected GoogleForum forum;
	protected GoogleIssueTracker issueTracker;
	protected int stars;
	
	public int getStars() {
		return stars;
	}
	
	public List<GoogleDownload> getDownloads() {
		return downloads;
	}
}
