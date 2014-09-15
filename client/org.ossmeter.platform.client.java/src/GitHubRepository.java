//package org.ossmeter.repository.model.github;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubRepository.class, name="GitHubRepository"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository extends Project {

	protected List<Language> languages;
	protected List<GitHubContent> contents;
	protected List<GitHubDownload> downloads;
	protected List<GitHubRepository> forks;
	protected List<GitHubMilestone> milestones;
	protected String full_name;
	protected boolean _private;
	protected boolean fork;
	protected String html_url;
	protected String clone_url;
	protected String git_url;
	protected String ssh_url;
	protected String svn_url;
	protected String mirror_url;
	protected String homepage;
	protected int size;
	protected String master_branch;
	
	public String getFull_name() {
		return full_name;
	}
	public boolean get_private() {
		return _private;
	}
	public boolean getFork() {
		return fork;
	}
	public String getHtml_url() {
		return html_url;
	}
	public String getClone_url() {
		return clone_url;
	}
	public String getGit_url() {
		return git_url;
	}
	public String getSsh_url() {
		return ssh_url;
	}
	public String getSvn_url() {
		return svn_url;
	}
	public String getMirror_url() {
		return mirror_url;
	}
	public String getHomepage() {
		return homepage;
	}
	public int getSize() {
		return size;
	}
	public String getMaster_branch() {
		return master_branch;
	}
	
	public List<Language> getLanguages() {
		return languages;
	}
	public List<GitHubContent> getContents() {
		return contents;
	}
	public List<GitHubDownload> getDownloads() {
		return downloads;
	}
	public List<GitHubRepository> getForks() {
		return forks;
	}
	public List<GitHubMilestone> getMilestones() {
		return milestones;
	}
}
