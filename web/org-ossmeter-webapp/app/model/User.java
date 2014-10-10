package model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
import be.objectify.deadbolt.core.models.Subject;

public class User extends Pongo implements Subject {
// protected region custom-imports end

	protected List<Role> roles = null;
	protected List<Permission> permissions = null;
	protected List<LinkedAccount> linkedAccounts = null;
	protected List<Project> watching = null;
	protected List<Project> owns = null;
	protected List<Notification> notifications = null;
	protected List<Event> events = null;
	protected List<GridEntry> grid = null;
	
	// protected region custom-fields-and-methods on begin
	public Date getLastLogin() {
		Object d = dbObject.get("lastLogin");
		if (d == null) return null;
		else return (Date)d;
	}
	public User setLastLogin(Date lastLogin) {
		dbObject.put("lastLogin", lastLogin);
		notifyChanged();
		return this;
	}
	// protected region custom-fields-and-methods end
	
	public User() { 
		super();
		dbObject.put("roles", new BasicDBList());
		dbObject.put("permissions", new BasicDBList());
		dbObject.put("linkedAccounts", new BasicDBList());
		dbObject.put("watching", new BasicDBList());
		dbObject.put("owns", new BasicDBList());
		dbObject.put("notifications", new BasicDBList());
		dbObject.put("events", new BasicDBList());
		dbObject.put("grid", new BasicDBList());
		IDENTIFIER.setOwningType("model.User");
		PASSWORD.setOwningType("model.User");
		NAME.setOwningType("model.User");
		EMAIL.setOwningType("model.User");
		EMAILVALIDATED.setOwningType("model.User");
		LASTLOGIN.setOwningType("model.User");
	}
	
	public static StringQueryProducer IDENTIFIER = new StringQueryProducer("identifier"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer EMAIL = new StringQueryProducer("email"); 
	public static StringQueryProducer EMAILVALIDATED = new StringQueryProducer("emailValidated"); 
	public static StringQueryProducer LASTLOGIN = new StringQueryProducer("lastLogin"); 
	
	
	public String getIdentifier() {
		return parseString(dbObject.get("identifier")+"", "");
	}
	
	public User setIdentifier(String identifier) {
		dbObject.put("identifier", identifier);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public User setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public User setName(String name) {
		dbObject.put("name", name);
		notifyChanged();
		return this;
	}
	public String getEmail() {
		return parseString(dbObject.get("email")+"", "");
	}
	
	public User setEmail(String email) {
		dbObject.put("email", email);
		notifyChanged();
		return this;
	}
	public boolean getEmailValidated() {
		return parseBoolean(dbObject.get("emailValidated")+"", false);
	}
	
	public User setEmailValidated(boolean emailValidated) {
		dbObject.put("emailValidated", emailValidated);
		notifyChanged();
		return this;
	}	
	
	public List<Role> getRoles() {
		if (roles == null) {
			roles = new PongoList<Role>(this, "roles", true);
		}
		return roles;
	}
	public List<Permission> getPermissions() {
		if (permissions == null) {
			permissions = new PongoList<Permission>(this, "permissions", true);
		}
		return permissions;
	}
	public List<LinkedAccount> getLinkedAccounts() {
		if (linkedAccounts == null) {
			linkedAccounts = new PongoList<LinkedAccount>(this, "linkedAccounts", true);
		}
		return linkedAccounts;
	}
	public List<Project> getWatching() {
		if (watching == null) {
			watching = new PongoList<Project>(this, "watching", true);
		}
		return watching;
	}
	public List<Project> getOwns() {
		if (owns == null) {
			owns = new PongoList<Project>(this, "owns", true);
		}
		return owns;
	}
	public List<Notification> getNotifications() {
		if (notifications == null) {
			notifications = new PongoList<Notification>(this, "notifications", true);
		}
		return notifications;
	}
	public List<Event> getEvents() {
		if (events == null) {
			events = new PongoList<Event>(this, "events", true);
		}
		return events;
	}
	public List<GridEntry> getGrid() {
		if (grid == null) {
			grid = new PongoList<GridEntry>(this, "grid", true);
		}
		return grid;
	}
	
	
}