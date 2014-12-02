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
package org.ossmeter.repository.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = CommunicationChannel.class, name="org.ossmeter.repository.model.CommunicationChannel"), 	@Type(value = org.ossmeter.repository.model.eclipse.MailingList.class, name="org.ossmeter.repository.model.eclipse.MailingList"),
	@Type(value = org.ossmeter.repository.model.eclipse.Documentation.class, name="org.ossmeter.repository.model.eclipse.Documentation"),
	@Type(value = org.ossmeter.repository.model.cc.forum.Forum.class, name="org.ossmeter.repository.model.cc.forum.Forum"),
	@Type(value = org.ossmeter.repository.model.googlecode.GoogleWiki.class, name="org.ossmeter.repository.model.googlecode.GoogleWiki"),
	@Type(value = org.ossmeter.repository.model.googlecode.GoogleForum.class, name="org.ossmeter.repository.model.googlecode.GoogleForum"),
	@Type(value = org.ossmeter.repository.model.cc.nntp.NntpNewsGroup.class, name="org.ossmeter.repository.model.cc.nntp.NntpNewsGroup"),
	@Type(value = org.ossmeter.repository.model.redmine.RedmineWiki.class, name="org.ossmeter.repository.model.redmine.RedmineWiki"),
	@Type(value = org.ossmeter.repository.model.redmine.RedmineQueryManager.class, name="org.ossmeter.repository.model.redmine.RedmineQueryManager"),
	@Type(value = org.ossmeter.repository.model.sourceforge.MailingList.class, name="org.ossmeter.repository.model.sourceforge.MailingList"),
	@Type(value = org.ossmeter.repository.model.sourceforge.Discussion.class, name="org.ossmeter.repository.model.sourceforge.Discussion"),
	@Type(value = org.ossmeter.repository.model.cc.wiki.Wiki.class, name="org.ossmeter.repository.model.cc.wiki.Wiki"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CommunicationChannel extends Object {

	protected List<Person> persons;
	protected String url;
	protected boolean nonProcessable;
	
	public String getUrl() {
		return url;
	}
	public boolean getNonProcessable() {
		return nonProcessable;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
