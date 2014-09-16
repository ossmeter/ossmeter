//package metrics;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = MetricVisualisation.class, name="MetricVisualisation"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetricVisualisation extends Object {

	protected String _id;
	protected String type;
	protected String description;
	protected String x;
	protected String y;
	protected Datatable datatable;
	
	public String get_id() {
		return _id;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public String getX() {
		return x;
	}
	public String getY() {
		return y;
	}
	
}
