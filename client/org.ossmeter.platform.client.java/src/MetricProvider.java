//package model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = MetricProvider.class, name="org.ossmeter.repository.model.MetricProvider"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricProvider extends NamedElement {

	protected String metricProviderId;
	protected MetricProviderType type;
	protected MetricProviderCategory category;
	
	public String getMetricProviderId() {
		return metricProviderId;
	}
	public MetricProviderType getType() {
		return type;
	}
	public MetricProviderCategory getCategory() {
		return category;
	}
	
}
