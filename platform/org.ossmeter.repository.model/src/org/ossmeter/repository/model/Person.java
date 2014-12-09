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

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Person extends NamedElement {
	
	protected List<Role> roles = null;
	
	
	public Person() { 
		super();
		dbObject.put("roles", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.ossmeter.repository.model.Person");
		HOMEPAGE.setOwningType("org.ossmeter.repository.model.Person");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer HOMEPAGE = new StringQueryProducer("homePage"); 
	
	
	public String getHomePage() {
		return parseString(dbObject.get("homePage")+"", "");
	}
	
	public Person setHomePage(String homePage) {
		dbObject.put("homePage", homePage);
		notifyChanged();
		return this;
	}
	
	
	public List<Role> getRoles() {
		if (roles == null) {
			roles = new PongoList<Role>(this, "roles", false);
		}
		return roles;
	}
	
	
}