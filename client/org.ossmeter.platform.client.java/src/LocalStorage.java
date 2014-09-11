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
	@Type(value = LocalStorage.class, name="org.ossmeter.repository.model.LocalStorage"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocalStorage extends Object {

	protected String path;
	
	public String getPath() {
		return path;
	}
	
}
