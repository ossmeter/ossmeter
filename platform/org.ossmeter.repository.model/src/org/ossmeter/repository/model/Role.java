/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Davide Di Ruscio - Implementation.
 *******************************************************************************/
package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.querying.*;


public class Role extends NamedElement {
	
	
	
	public Role() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.ossmeter.repository.model.Role");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	
	
	
	
	
	
}