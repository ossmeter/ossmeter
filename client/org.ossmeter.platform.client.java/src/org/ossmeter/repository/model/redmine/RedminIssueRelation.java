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
package org.ossmeter.repository.model.redmine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = RedminIssueRelation.class, name="org.ossmeter.repository.model.redmine.RedminIssueRelation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedminIssueRelation extends Object {

	protected RedmineIssue relatedIssue;
	protected String type;
	
	public String getType() {
		return type;
	}
	
}
