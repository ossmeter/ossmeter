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
package org.ossmeter.repository.model.sourceforge;

import com.googlecode.pongo.runtime.querying.*;


public class SupportRequest extends Request {
	
	
	
	public SupportRequest() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.Request","org.ossmeter.repository.model.sourceforge.Tracker","org.ossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.ossmeter.repository.model.sourceforge.SupportRequest");
		STATUS.setOwningType("org.ossmeter.repository.model.sourceforge.SupportRequest");
		SUMMARY.setOwningType("org.ossmeter.repository.model.sourceforge.SupportRequest");
		CREATED_AT.setOwningType("org.ossmeter.repository.model.sourceforge.SupportRequest");
		UPDATED_AT.setOwningType("org.ossmeter.repository.model.sourceforge.SupportRequest");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	
	
	
	
}