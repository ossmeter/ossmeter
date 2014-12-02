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

import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class IssueEvent extends Pongo {
	
	
	
	public IssueEvent() { 
		super();
		EVENTKIND.setOwningType("org.ossmeter.platform.mining.msr14.model.IssueEvent");
		COUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.IssueEvent");
	}
	
	public static StringQueryProducer EVENTKIND = new StringQueryProducer("eventKind"); 
	public static NumericalQueryProducer COUNT = new NumericalQueryProducer("count");
	
	
	public IssueEventKind getEventKind() {
		IssueEventKind eventKind = null;
		try {
			eventKind = IssueEventKind.valueOf(dbObject.get("eventKind")+"");
		}
		catch (Exception ex) {}
		return eventKind;
	}
	
	public IssueEvent setEventKind(IssueEventKind eventKind) {
		dbObject.put("eventKind", eventKind.toString());
		notifyChanged();
		return this;
	}
	public int getCount() {
		return parseInteger(dbObject.get("count")+"", 0);
	}
	
	public IssueEvent setCount(int count) {
		dbObject.put("count", count);
		notifyChanged();
		return this;
	}
	
	
	
	
}