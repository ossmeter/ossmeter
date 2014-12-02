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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = MetricProvider.class, name="org.ossmeter.repository.model.MetricProvider"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricProvider extends NamedElement {

	protected String metricProviderId;
	protected MetricProviderType type;
	protected MetricProviderCategory category;
	
	public String getMetricProviderId() {
		return metricProviderId;
	}
	public MetricProviderType getType() {
		return type;
	}
	public MetricProviderCategory getCategory() {
		return category;
	}
	
}
