//package org.ossmeter.repository.model.redmine;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = RedmineQueryManager.class, name="RedmineQueryManager"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedmineQueryManager extends CommunicationChannel {

	protected List<RedmineQuery> queries;
	
	
	public List<RedmineQuery> getQueries() {
		return queries;
	}
}
