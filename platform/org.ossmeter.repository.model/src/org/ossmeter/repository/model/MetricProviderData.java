package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class MetricProviderData extends Pongo {
	
	
	
	public MetricProviderData() { 
		super();
	}
	
	public String getMetricProviderId() {
		return parseString(dbObject.get("metricProviderId")+"", "");
	}
	
	public MetricProviderData setMetricProviderId(String metricProviderId) {
		dbObject.put("metricProviderId", metricProviderId + "");
		notifyChanged();
		return this;
	}
	public String getLastExecuted() {
		return parseString(dbObject.get("lastExecuted")+"", "-1");
	}
	
	public MetricProviderData setLastExecuted(String lastExecuted) {
		dbObject.put("lastExecuted", lastExecuted + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}