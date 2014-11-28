package org.ossmeter.repository.model;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.ossmeter.repository.model.redmine.*;
import org.ossmeter.repository.model.vcs.svn.*;
import org.ossmeter.repository.model.cc.forum.*;
import org.ossmeter.repository.model.bts.bugzilla.*;
import org.ossmeter.repository.model.cc.nntp.*;
import org.ossmeter.repository.model.vcs.cvs.*;
import org.ossmeter.repository.model.eclipse.*;
import org.ossmeter.repository.model.googlecode.*;
import org.ossmeter.repository.model.vcs.git.*;
import org.ossmeter.repository.model.sourceforge.*;
import org.ossmeter.repository.model.github.*;
import org.ossmeter.repository.model.*;
import org.ossmeter.repository.model.cc.wiki.*;
import org.ossmeter.repository.model.metrics.*;
import org.ossmeter.platform.factoids.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = BugTrackingSystem.class, name="org.ossmeter.repository.model.BugTrackingSystem"), 	@Type(value = org.ossmeter.repository.model.bts.bugzilla.Bugzilla.class, name="org.ossmeter.repository.model.bts.bugzilla.Bugzilla"),
	@Type(value = org.ossmeter.repository.model.github.GitHubBugTracker.class, name="org.ossmeter.repository.model.github.GitHubBugTracker"),
	@Type(value = org.ossmeter.repository.model.googlecode.GoogleIssueTracker.class, name="org.ossmeter.repository.model.googlecode.GoogleIssueTracker"),
	@Type(value = org.ossmeter.repository.model.redmine.RedmineBugIssueTracker.class, name="org.ossmeter.repository.model.redmine.RedmineBugIssueTracker"),
	@Type(value = org.ossmeter.repository.model.sourceforge.SourceForgeBugTrackingSystem.class, name="org.ossmeter.repository.model.sourceforge.SourceForgeBugTrackingSystem"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
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
