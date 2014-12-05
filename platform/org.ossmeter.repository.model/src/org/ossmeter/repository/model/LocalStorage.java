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

// protected region custom-imports on begin
// protected region custom-imports end

public class LocalStorage extends Pongo {
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	public LocalStorage() { 
		super();
		PATH.setOwningType("org.ossmeter.repository.model.LocalStorage");
	}
	
	public static StringQueryProducer PATH = new StringQueryProducer("path"); 
	
	
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public LocalStorage setPath(String path) {
		dbObject.put("path", path);
		notifyChanged();
		return this;
	}
	
	
	
	
}