package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class PlatformInternal extends Pongo {
	
	protected List<MetricProvider> metricProviderData = null;
	protected LocalStorage storage = null;
	
	
	public PlatformInternal() { 
		super();
		dbObject.put("metricProviderData", new BasicDBList());
		MONITOR.setOwningType("org.ossmeter.repository.model.PlatformInternal");
		INERRORSTATE.setOwningType("org.ossmeter.repository.model.PlatformInternal");
		LASTEXECUTED.setOwningType("org.ossmeter.repository.model.PlatformInternal");
	}
	
	public static StringQueryProducer MONITOR = new StringQueryProducer("monitor"); 
	public static StringQueryProducer INERRORSTATE = new StringQueryProducer("inErrorState"); 
	public static StringQueryProducer LASTEXECUTED = new StringQueryProducer("lastExecuted"); 
	
	
	public boolean getMonitor() {
		return parseBoolean(dbObject.get("monitor")+"", true);
	}
	
	public PlatformInternal setMonitor(boolean monitor) {
		dbObject.put("monitor", monitor);
		notifyChanged();
		return this;
	}
	public boolean getInErrorState() {
		return parseBoolean(dbObject.get("inErrorState")+"", false);
	}
	
	public PlatformInternal setInErrorState(boolean inErrorState) {
		dbObject.put("inErrorState", inErrorState);
		notifyChanged();
		return this;
	}
	public String getLastExecuted() {
		return parseString(dbObject.get("lastExecuted")+"", "");
	}
	
	public PlatformInternal setLastExecuted(String lastExecuted) {
		dbObject.put("lastExecuted", lastExecuted);
		notifyChanged();
		return this;
	}
	
	
	public List<MetricProvider> getMetricProviderData() {
		if (metricProviderData == null) {
			metricProviderData = new PongoList<MetricProvider>(this, "metricProviderData", true);
		}
		return metricProviderData;
	}
	
	
	public LocalStorage getStorage() {
		if (storage == null && dbObject.containsField("storage")) {
			storage = (LocalStorage) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("storage"));
		}
		return storage;
	}
	
	public PlatformInternal setStorage(LocalStorage storage) {
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