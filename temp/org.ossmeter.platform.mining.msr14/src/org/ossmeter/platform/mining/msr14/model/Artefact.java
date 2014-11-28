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
package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Artefact extends Pongo {
	
	
	
	public Artefact() { 
		super();
		EXTENSION.setOwningType("org.ossmeter.platform.mining.msr14.model.Artefact");
		COUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Artefact");
	}
	
	public static StringQueryProducer EXTENSION = new StringQueryProducer("extension"); 
	public static NumericalQueryProducer COUNT = new NumericalQueryProducer("count");
	
	
	public String getExtension() {
		return parseString(dbObject.get("extension")+"", "");
	}
	
	public Artefact setExtension(String extension) {
		dbObject.put("extension", extension);
		notifyChanged();
		return this;
	}
	public int getCount() {
		return parseInteger(dbObject.get("count")+"", 0);
	}
	
	public Artefact setCount(int count) {
		dbObject.put("count", count);
		notifyChanged();
		return this;
	}
	
	
	
	
}