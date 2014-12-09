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


public class SchedulingInformation extends Pongo {
	
	protected List<String> currentLoad = null;
	
	
	public SchedulingInformation() { 
		super();
		dbObject.put("currentLoad", new BasicDBList());
		WORKERIDENTIFIER.setOwningType("org.ossmeter.repository.model.SchedulingInformation");
		HEARTBEAT.setOwningType("org.ossmeter.repository.model.SchedulingInformation");
		ISMASTER.setOwningType("org.ossmeter.repository.model.SchedulingInformation");
		CURRENTLOAD.setOwningType("org.ossmeter.repository.model.SchedulingInformation");
	}
	
	public static StringQueryProducer WORKERIDENTIFIER = new StringQueryProducer("workerIdentifier"); 
	public static NumericalQueryProducer HEARTBEAT = new NumericalQueryProducer("heartbeat");
	public static StringQueryProducer ISMASTER = new StringQueryProducer("isMaster"); 
	public static ArrayQueryProducer CURRENTLOAD = new ArrayQueryProducer("currentLoad");
	
	
	public String getWorkerIdentifier() {
		return parseString(dbObject.get("workerIdentifier")+"", "");
	}
	
	public SchedulingInformation setWorkerIdentifier(String workerIdentifier) {
		dbObject.put("workerIdentifier", workerIdentifier);
		notifyChanged();
		return this;
	}
	public long getHeartbeat() {
		return parseLong(dbObject.get("heartbeat")+"", 0);
	}
	
	public SchedulingInformation setHeartbeat(long heartbeat) {
		dbObject.put("heartbeat", heartbeat);
		notifyChanged();
		return this;
	}
	public boolean getIsMaster() {
		return parseBoolean(dbObject.get("isMaster")+"", false);
	}
	
	public SchedulingInformation setIsMaster(boolean isMaster) {
		dbObject.put("isMaster", isMaster);
		notifyChanged();
		return this;
	}
	
	public List<String> getCurrentLoad() {
		if (currentLoad == null) {
			currentLoad = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("currentLoad"));
		}
		return currentLoad;
	}
	
	
	
}