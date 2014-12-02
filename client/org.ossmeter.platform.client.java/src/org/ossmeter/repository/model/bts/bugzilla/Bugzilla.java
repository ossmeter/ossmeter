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
package org.ossmeter.repository.model.bts.bugzilla;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.ossmeter.repository.model.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = Bugzilla.class, name="org.ossmeter.repository.model.bts.bugzilla.Bugzilla"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bugzilla extends BugTrackingSystem {

	protected String username;
	protected String password;
	protected String product;
	protected String component;
	protected String cgiQueryProgram;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getProduct() {
		return product;
	}
	public String getComponent() {
		return component;
	}
	public String getCgiQueryProgram() {
		return cgiQueryProgram;
	}
	
}
