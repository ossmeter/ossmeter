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
package org.ossmeter.repository.model.cc.nntp;

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
	@Type(value = NntpNewsGroup.class, name="org.ossmeter.repository.model.cc.nntp.NntpNewsGroup"), 	@Type(value = org.ossmeter.repository.model.eclipse.EclipseNewsGroup.class, name="org.ossmeter.repository.model.eclipse.EclipseNewsGroup"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class NntpNewsGroup extends CommunicationChannel {

	protected boolean authenticationRequired;
	protected String username;
	protected String password;
	protected int port;
	protected String description;
	protected String name;
	protected int interval;
	protected String lastArticleChecked;
	
	public boolean getAuthenticationRequired() {
		return authenticationRequired;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getPort() {
		return port;
	}
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
	public int getInterval() {
		return interval;
	}
	public String getLastArticleChecked() {
		return lastArticleChecked;
	}
	
}
