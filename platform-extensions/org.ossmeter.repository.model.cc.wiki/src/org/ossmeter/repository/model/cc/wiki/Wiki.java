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
package org.ossmeter.repository.model.cc.wiki;

import com.googlecode.pongo.runtime.querying.*;


public class Wiki extends org.ossmeter.repository.model.CommunicationChannel {
	
	
	
	public Wiki() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.cc.wiki.CommunicationChannel");
		NAME.setOwningType("org.ossmeter.repository.model.cc.wiki.Wiki");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.cc.wiki.Wiki");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public Wiki setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public Wiki setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	
	
	
	
}