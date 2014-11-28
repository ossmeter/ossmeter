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

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleIssueComment extends Pongo {
	
	
	
	public GoogleIssueComment() { 
		super();
		TEXT.setOwningType("org.ossmeter.repository.model.googlecode.GoogleIssueComment");
		DATE.setOwningType("org.ossmeter.repository.model.googlecode.GoogleIssueComment");
	}
	
	public static StringQueryProducer TEXT = new StringQueryProducer("text"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getText() {
		return parseString(dbObject.get("text")+"", "");
	}
	
	public GoogleIssueComment setText(String text) {
		dbObject.put("text", text);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public GoogleIssueComment setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}