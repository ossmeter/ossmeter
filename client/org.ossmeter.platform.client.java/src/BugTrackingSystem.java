//package model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
})
public abstract class BugTrackingSystem extends Object {

	protected List<Person> persons;
	protected String url;
	
	public String getUrl() {
		return url;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
