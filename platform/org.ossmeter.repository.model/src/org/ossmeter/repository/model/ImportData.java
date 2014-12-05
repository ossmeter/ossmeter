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

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ImportData extends Pongo {
	
	
	
	public ImportData() { 
		super();
		FORGE.setOwningType("org.ossmeter.repository.model.ImportData");
		LASTIMPORTEDPROJECT.setOwningType("org.ossmeter.repository.model.ImportData");
	}
	
	public static StringQueryProducer FORGE = new StringQueryProducer("forge"); 
	public static StringQueryProducer LASTIMPORTEDPROJECT = new StringQueryProducer("lastImportedProject"); 
	
	
	public String getForge() {
		return parseString(dbObject.get("forge")+"", "");
	}
	
	public ImportData setForge(String forge) {
		dbObject.put("forge", forge);
		notifyChanged();
		return this;
	}
	public String getLastImportedProject() {
		return parseString(dbObject.get("lastImportedProject")+"", "");
	}
	
	public ImportData setLastImportedProject(String lastImportedProject) {
		dbObject.put("lastImportedProject", lastImportedProject);
		notifyChanged();
		return this;
	}
	
	
	
	
}