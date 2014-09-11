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
	@Type(value = License.class, name="org.ossmeter.repository.model.License"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class License extends NamedElement {

	protected String url;
	
	public String getUrl() {
		return url;
	}
	
}
