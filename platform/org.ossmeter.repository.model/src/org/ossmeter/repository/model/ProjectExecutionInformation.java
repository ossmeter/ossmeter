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


public class ProjectExecutionInformation extends Pongo {
	
	protected List<MetricProviderExecution> metricProviderData = null;
	protected LocalStorage storage = null;
	
	
	public ProjectExecutionInformation() { 
		super();
		dbObject.put("storage", new LocalStorage().getDbObject());
		dbObject.put("metricProviderData", new BasicDBList());
		ANALYSED.setOwningType("org.ossmeter.repository.model.ProjectExecutionInformation");
		LASTEXECUTED.setOwningType("org.ossmeter.repository.model.ProjectExecutionInformation");
		MONITOR.setOwningType("org.ossmeter.repository.model.ProjectExecutionInformation");
		INERRORSTATE.setOwningType("org.ossmeter.repository.model.ProjectExecutionInformation");
	}
	
	public static StringQueryProducer ANALYSED = new StringQueryProducer("analysed"); 
	public static StringQueryProducer LASTEXECUTED = new StringQueryProducer("lastExecuted"); 
	public static StringQueryProducer MONITOR = new StringQueryProducer("monitor"); 
	public static StringQueryProducer INERRORSTATE = new StringQueryProducer("inErrorState"); 
	
	
	public boolean getAnalysed() {
		return parseBoolean(dbObject.get("analysed")+"", false);
	}
	
	public ProjectExecutionInformation setAnalysed(boolean analysed) {
		dbObject.put("analysed", analysed);
		notifyChanged();
		return this;
	}
	public String getLastExecuted() {
		return parseString(dbObject.get("lastExecuted")+"", "");
	}
	
	public ProjectExecutionInformation setLastExecuted(String lastExecuted) {
		dbObject.put("lastExecuted", lastExecuted);
		notifyChanged();
		return this;
	}
	public boolean getMonitor() {
		return parseBoolean(dbObject.get("monitor")+"", true);
	}
	
	public ProjectExecutionInformation setMonitor(boolean monitor) {
		dbObject.put("monitor", monitor);
		notifyChanged();
		return this;
	}
	public boolean getInErrorState() {
		return parseBoolean(dbObject.get("inErrorState")+"", false);
	}
	
	public ProjectExecutionInformation setInErrorState(boolean inErrorState) {
		dbObject.put("inErrorState", inErrorState);
		notifyChanged();
		return this;
	}
	
	
	public List<MetricProviderExecution> getMetricProviderData() {
		if (metricProviderData == null) {
			metricProviderData = new PongoList<MetricProviderExecution>(this, "metricProviderData", true);
		}
		return metricProviderData;
	}
	
	
	public LocalStorage getStorage() {
		if (storage == null && dbObject.containsField("storage")) {
			storage = (LocalStorage) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("storage"));
			storage.setContainer(this);
		}
		return storage;
	}
	
	public ProjectExecutionInformation setStorage(LocalStorage storage) {
		if (this.storage != storage) {
			if (storage == null) {
				dbObject.removeField("storage");
			}
			else {
				dbObject.put("storage", storage.getDbObject());
			}
			this.storage = storage;
			notifyChanged();
		}
		return this;
	}
}