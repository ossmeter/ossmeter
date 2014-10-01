package org.ossmeter.repository.model.github;

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
import org.ossmeter.repository.model.metrics.*;
import org.ossmeter.platform.factoids.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubIssue.class, name="org.ossmeter.repository.model.github.GitHubIssue"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubIssue extends Object {

	protected List<GitHubUser> subscribed_users;
	protected List<GitHubUser> mentioned_users;
	protected int number;
	protected String state;
	protected String title;
	protected String body;
	protected GitHubUser creator;
	protected GitHubUser assignee;
	protected String created_at;
	protected String updated_at;
	protected String closed_at;
	
	public int getNumber() {
		return number;
	}
	public String getState() {
		return state;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public String getCreated_at() {
		return created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getClosed_at() {
		return closed_at;
	}
	
	public List<GitHubUser> getSubscribed_users() {
		return subscribed_users;
	}
	public List<GitHubUser> getMentioned_users() {
		return mentioned_users;
	}
}
