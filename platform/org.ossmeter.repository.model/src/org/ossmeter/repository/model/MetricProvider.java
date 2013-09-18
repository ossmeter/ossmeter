package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MetricProvider extends Pongo {
	
	
	
	public MetricProvider() { 
		super();
		METRICPROVIDERID.setOwningType("org.ossmeter.repository.model.MetricProvider");
		TYPE.setOwningType("org.ossmeter.repository.model.MetricProvider");
		LASTEXECUTED.setOwningType("org.ossmeter.repository.model.MetricProvider");
	}
	
	public static StringQueryProducer METRICPROVIDERID = new StringQueryProducer("metricProviderId"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer LASTEXECUTED = new StringQueryProducer("lastExecuted"); 
	
	
	public String getMetricProviderId() {
		return parseString(dbObject.get("metricProviderId")+"", "");
	}
	
	public MetricProvider setMetricProviderId(String metricProviderId) {
		dbObject.put("metricProviderId", metricProviderId);
		notifyChanged();
		return this;
	}
	public MetricProviderType getType() {
		MetricProviderType type = null;
		try {
			type = MetricProviderType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public MetricProvider setType(MetricProviderType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	public String getLastExecuted() {
		return parseString(dbObject.get("lastExecuted")+"", "-1");
	}
	
	public MetricProvider setLastExecuted(String lastExecuted) {
		dbObject.put("lastExecuted", lastExecuted);
		notifyChanged();
		return this;
	}
	
	
	
	
}