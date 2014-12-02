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
package org.ossmeter.platform.factoids;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = Factoid.class, name="org.ossmeter.platform.factoids.Factoid"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Factoid extends Object {

	protected List<String> metricDependencies;
	protected String metricId;
	protected String factoid;
	protected StarRating stars;
	protected FactoidCategory category;
	
	public String getMetricId() {
		return metricId;
	}
	public String getFactoid() {
		return factoid;
	}
	public StarRating getStars() {
		return stars;
	}
	public FactoidCategory getCategory() {
		return category;
	}
	
	public List<String> getMetricDependencies() {
		return metricDependencies;
	}
}
