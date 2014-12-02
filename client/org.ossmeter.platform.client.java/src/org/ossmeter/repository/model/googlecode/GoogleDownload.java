/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model.googlecode;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GoogleDownload.class, name="org.ossmeter.repository.model.googlecode.GoogleDownload"), })
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
