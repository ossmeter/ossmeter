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

import org.ossmeter.repository.model.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GoogleCodeProject.class, name="org.ossmeter.repository.model.googlecode.GoogleCodeProject"), })
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
