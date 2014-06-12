package org.ossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ProjectRepository extends PongoDB {
	
	public ProjectRepository() {}
	
	public ProjectRepository(DB db) {
		setDb(db);
	}
	
	protected ProjectCollection projects = null;
	protected MetricProviderCollection metricProviders = null;
	protected RoleCollection roles = null;
	protected PersonCollection persons = null;
	protected LicenseCollection licenses = null;
	protected ImportDataCollection importData = null;
	protected SchedulingInformationCollection schedulingInformation = null;
	
	
	
	public ProjectCollection getProjects() {
		return projects;
	}
	
	public MetricProviderCollection getMetricProviders() {
		return metricProviders;
	}
	
	public RoleCollection getRoles() {
		return roles;
	}
	
	public PersonCollection getPersons() {
		return persons;
	}
	
	public LicenseCollection getLicenses() {
		return licenses;
	}
	
	public ImportDataCollection getImportData() {
		return importData;
	}
	
	public SchedulingInformationCollection getSchedulingInformation() {
		return schedulingInformation;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		projects = new ProjectCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		metricProviders = new MetricProviderCollection(db.getCollection("metricProviders"));
		pongoCollections.add(metricProviders);
		roles = new RoleCollection(db.getCollection("roles"));
		pongoCollections.add(roles);
		persons = new PersonCollection(db.getCollection("persons"));
		pongoCollections.add(persons);
		licenses = new LicenseCollection(db.getCollection("licenses"));
		pongoCollections.add(licenses);
		importData = new ImportDataCollection(db.getCollection("importData"));
		pongoCollections.add(importData);
		schedulingInformation = new SchedulingInformationCollection(db.getCollection("schedulingInformation"));
		pongoCollections.add(schedulingInformation);
	}
}