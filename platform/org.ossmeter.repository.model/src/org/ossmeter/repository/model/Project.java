package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Project extends NamedElement {
	
	protected List<VcsRepository> vcsRepositories = null;
	protected List<CommunicationChannel> communicationChannels = null;
	protected List<BugTrackingSystem> bugTrackingSystems = null;
	protected List<Person> persons = null;
	protected List<License> licenses = null;
	protected List<MetricProvider> metricProviderData = null;
	protected LocalStorage storage = null;
	
	
	public Project() { 
		super();
		dbObject.put("vcsRepositories", new BasicDBList());
		dbObject.put("communicationChannels", new BasicDBList());
		dbObject.put("bugTrackingSystems", new BasicDBList());
		dbObject.put("persons", new BasicDBList());
		dbObject.put("licenses", new BasicDBList());
		dbObject.put("metricProviderData", new BasicDBList());
		super.setSuperTypes("org.ossmeter.repository.model.NamedElement");
		NAME.setOwningType("org.ossmeter.repository.model.Project");
		SHORTNAME.setOwningType("org.ossmeter.repository.model.Project");
		DESCRIPTION.setOwningType("org.ossmeter.repository.model.Project");
		YEAR.setOwningType("org.ossmeter.repository.model.Project");
		ACTIVE.setOwningType("org.ossmeter.repository.model.Project");
		LASTEXECUTED.setOwningType("org.ossmeter.repository.model.Project");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer SHORTNAME = new StringQueryProducer("shortName"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static NumericalQueryProducer YEAR = new NumericalQueryProducer("year");
	public static StringQueryProducer ACTIVE = new StringQueryProducer("active"); 
	public static StringQueryProducer LASTEXECUTED = new StringQueryProducer("lastExecuted"); 
	
	
	public String getShortName() {
		return parseString(dbObject.get("shortName")+"", "");
	}
	
	public Project setShortName(String shortName) {
		dbObject.put("shortName", shortName);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public Project setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public int getYear() {
		return parseInteger(dbObject.get("year")+"", 0);
	}
	
	public Project setYear(int year) {
		dbObject.put("year", year);
		notifyChanged();
		return this;
	}
	public boolean getActive() {
		return parseBoolean(dbObject.get("active")+"", false);
	}
	
	public Project setActive(boolean active) {
		dbObject.put("active", active);
		notifyChanged();
		return this;
	}
	public String getLastExecuted() {
		return parseString(dbObject.get("lastExecuted")+"", "");
	}
	
	public Project setLastExecuted(String lastExecuted) {
		dbObject.put("lastExecuted", lastExecuted);
		notifyChanged();
		return this;
	}
	
	
	public List<VcsRepository> getVcsRepositories() {
		if (vcsRepositories == null) {
			vcsRepositories = new PongoList<VcsRepository>(this, "vcsRepositories", true);
		}
		return vcsRepositories;
	}
	public List<CommunicationChannel> getCommunicationChannels() {
		if (communicationChannels == null) {
			communicationChannels = new PongoList<CommunicationChannel>(this, "communicationChannels", true);
		}
		return communicationChannels;
	}
	public List<BugTrackingSystem> getBugTrackingSystems() {
		if (bugTrackingSystems == null) {
			bugTrackingSystems = new PongoList<BugTrackingSystem>(this, "bugTrackingSystems", true);
		}
		return bugTrackingSystems;
	}
	public List<Person> getPersons() {
		if (persons == null) {
			persons = new PongoList<Person>(this, "persons", true);
		}
		return persons;
	}
	public List<License> getLicenses() {
		if (licenses == null) {
			licenses = new PongoList<License>(this, "licenses", true);
		}
		return licenses;
	}
	public List<MetricProvider> getMetricProviderData() {
		if (metricProviderData == null) {
			metricProviderData = new PongoList<MetricProvider>(this, "metricProviderData", true);
		}
		return metricProviderData;
	}
	
	
	public LocalStorage getStorage() {
		if (storage == null && dbObject.containsField("storage")) {
			storage = (LocalStorage) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("storage"));
		}
		return storage;
	}
	
	public Project setStorage(LocalStorage storage) {
		if (this.storage != storage) {
			if (storage == null) {
				dbObject.removeField("storage");
			}
			else {
				dbObject.put("storage", storage.getDbObject());
			}
			this.storage = storage;
			notifyChanged();
		}
		return this;
	}
}