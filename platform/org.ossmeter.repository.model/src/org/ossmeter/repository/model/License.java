/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation,
 *    Juri Di Rocco - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.querying.*;


public class License extends NamedElement {
	
	
	
	public License() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.ossmeter.repository.model.License");
		URL.setOwningType("org.ossmeter.repository.model.License");
		CONTENT.setOwningType("org.ossmeter.repository.model.License");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer CONTENT = new StringQueryProducer("content"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public License setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getContent() {
		return parseString(dbObject.get("content")+"", "");
	}
	
	public License setContent(String content) {
		dbObject.put("content", content);
		notifyChanged();
		return this;
	}
	
	
	
	
}