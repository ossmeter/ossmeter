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

import org.ossmeter.repository.model.redmine.*;
import org.ossmeter.repository.model.vcs.svn.*;
import org.ossmeter.repository.model.cc.forum.*;
import org.ossmeter.repository.model.bts.bugzilla.*;
import org.ossmeter.repository.model.cc.nntp.*;
import org.ossmeter.repository.model.vcs.cvs.*;
import org.ossmeter.repository.model.eclipse.*;
import org.ossmeter.repository.model.googlecode.*;
import org.ossmeter.repository.model.vcs.git.*;
import org.ossmeter.repository.model.sourceforge.*;
import org.ossmeter.repository.model.github.*;
import org.ossmeter.repository.model.*;
import org.ossmeter.repository.model.cc.wiki.*;
import org.ossmeter.repository.model.metrics.*;
import org.ossmeter.platform.factoids.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GoogleIssue.class, name="org.ossmeter.repository.model.googlecode.GoogleIssue"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleIssue extends Object {

	protected List<GoogleIssueComment> comments;
	protected List<GoogleLabel> labels;
	protected GoogleUser owner;
	protected String created_at;
	protected String updated_at;
	protected String priority;
	protected String type;
	protected String component;
	protected String status;
	protected int stars;
	protected String summary;
	
	public String getCreated_at() {
		return created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getPriority() {
		return priority;
	}
	public String getType() {
		return type;
	}
	public String getComponent() {
		return component;
	}
	public String getStatus() {
		return status;
	}
	public int getStars() {
		return stars;
	}
	public String getSummary() {
		return summary;
	}
	
	public List<GoogleIssueComment> getComments() {
		return comments;
	}
	public List<GoogleLabel> getLabels() {
		return labels;
	}
}
