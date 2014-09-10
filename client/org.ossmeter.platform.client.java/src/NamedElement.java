//package model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
		@Type(value = Project.class, name="Project"),
		@Type(value = MetricProvider.class, name="MetricProvider"),
		@Type(value = VcsRepository.class, name="VcsRepository"),
		@Type(value = Person.class, name="Person"),
		@Type(value = Role.class, name="Role"),
		@Type(value = License.class, name="License"),
})
public abstract class NamedElement extends Object {

	protected String name;
	
	public String getName() {
		return name;
	}
	
}
