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
	@Type(value = Metric.class, name="Metric"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Metric extends Object {

	protected String _id;
	protected String name;
	protected String description;
	protected Datatable datatable;
	
	public String get_id() {
		return _id;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	
}
