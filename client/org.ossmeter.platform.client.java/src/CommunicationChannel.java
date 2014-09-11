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
	@Type(value = CommunicationChannel.class, name="org.ossmeter.repository.model.CommunicationChannel"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CommunicationChannel extends Object {

	protected List<Person> persons;
	protected String url;
	protected boolean nonProcessable;
	
	public String getUrl() {
		return url;
	}
	public boolean getNonProcessable() {
		return nonProcessable;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
