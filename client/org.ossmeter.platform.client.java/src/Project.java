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
	@Type(value = Project.class, name="org.ossmeter.repository.model.Project"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends NamedElement {

	protected List<VcsRepository> vcsRepositories;
	protected List<CommunicationChannel> communicationChannels;
	protected List<BugTrackingSystem> bugTrackingSystems;
	protected List<Person> persons;
	protected List<License> licenses;
	protected List<MetricProvider> metricProviderData;
	protected String shortName;
	protected String description;
	protected int year;
	protected boolean active;
	protected String lastExecuted;
	protected Project parent;
	
	public String getShortName() {
		return shortName;
	}
	public String getDescription() {
		return description;
	}
	public int getYear() {
		return year;
	}
	public boolean getActive() {
		return active;
	}
	public String getLastExecuted() {
		return lastExecuted;
	}
	
	public List<VcsRepository> getVcsRepositories() {
		return vcsRepositories;
	}
	public List<CommunicationChannel> getCommunicationChannels() {
		return communicationChannels;
	}
	public List<BugTrackingSystem> getBugTrackingSystems() {
		return bugTrackingSystems;
	}
	public List<Person> getPersons() {
		return persons;
	}
	public List<License> getLicenses() {
		return licenses;
	}
	public List<MetricProvider> getMetricProviderData() {
		return metricProviderData;
	}
}
