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
package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class SvnRepository extends org.ossmeter.repository.model.VcsRepository {
	
	
	
	public SvnRepository() { 
		super();
	}
	
	public String getBrowse() {
		return parseString(dbObject.get("browse")+"", "");
	}
	
	public SvnRepository setBrowse(String browse) {
		dbObject.put("browse", browse + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}