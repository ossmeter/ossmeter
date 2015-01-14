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
	@Type(value = VcsRepository.class, name="org.ossmeter.repository.model.VcsRepository"), 	@Type(value = org.ossmeter.repository.model.vcs.cvs.CvsRepository.class, name="org.ossmeter.repository.model.vcs.cvs.CvsRepository"),
	@Type(value = org.ossmeter.repository.model.vcs.git.GitRepository.class, name="org.ossmeter.repository.model.vcs.git.GitRepository"),
	@Type(value = org.ossmeter.repository.model.vcs.svn.SvnRepository.class, name="org.ossmeter.repository.model.vcs.svn.SvnRepository"),
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

	public void setUrl(String url) {
		this.url = url;
	}
}
