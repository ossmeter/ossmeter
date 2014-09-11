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
	@Type(value = VcsRepository.class, name="org.ossmeter.repository.model.VcsRepository"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class VcsRepository extends NamedElement {

	protected List<Person> persons;
	protected String created_at;
	protected String updated_at;
	protected String url;
	
	public String getCreated_at() {
		return created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getUrl() {
		return url;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
