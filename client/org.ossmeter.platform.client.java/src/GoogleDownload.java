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
	@Type(value = GoogleDownload.class, name="GoogleDownload"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleDownload extends Object {

	protected List<GoogleLabel> labels;
	protected boolean starred;
	protected String fileName;
	protected String uploaded_at;
	protected String updated_at;
	protected String size;
	protected int downloadCounts;
	
	public boolean getStarred() {
		return starred;
	}
	public String getFileName() {
		return fileName;
	}
	public String getUploaded_at() {
		return uploaded_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getSize() {
		return size;
	}
	public int getDownloadCounts() {
		return downloadCounts;
	}
	
	public List<GoogleLabel> getLabels() {
		return labels;
	}
}
