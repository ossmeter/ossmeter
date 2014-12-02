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
	@Type(value = SchedulingInformation.class, name="org.ossmeter.repository.model.SchedulingInformation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class SchedulingInformation extends Object {

	protected List<String> currentLoad;
	protected String workerIdentifier;
	protected boolean isMaster;
	
	public String getWorkerIdentifier() {
		return workerIdentifier;
	}
	public boolean getIsMaster() {
		return isMaster;
	}
	
	public List<String> getCurrentLoad() {
		return currentLoad;
	}
}
