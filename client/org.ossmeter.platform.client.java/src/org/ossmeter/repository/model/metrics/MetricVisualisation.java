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
package org.ossmeter.repository.model.metrics;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = MetricVisualisation.class, name="org.ossmeter.repository.model.metrics.MetricVisualisation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricVisualisation extends Object {

	protected String _id;
	protected String type;
	protected String description;
	protected String x;
	protected String y;
	protected Datatable datatable;
	
	public String get_id() {
		return _id;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public String getX() {
		return x;
	}
	public String getY() {
		return y;
	}
	
}
