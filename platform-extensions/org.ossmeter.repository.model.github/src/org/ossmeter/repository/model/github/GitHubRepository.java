package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubRepository extends org.ossmeter.repository.model.VcsRepository {
	
	protected List<GitHubUser> stargazers = null;
	protected List<GitHubUser> collaborators = null;
	protected List<GitHubCommit> commits = null;
	protected List<GitHubContent> contents = null;
	protected List<GitHubDownload> downloads = null;
	protected List<GitHubRepository> forks = null;
	protected List<GitHubUser> watchers = null;
	protected List<GitHubMilestone> milestones = null;
	protected GitHubUser owner = null;
	
	
	public GitHubRepository() { 
		super();
		dbObject.put("stargazers", new BasicDBList());
		dbObject.put("collaborators", new BasicDBList());
		dbObject.put("commits", new BasicDBList());
		dbObject.put("contents", new BasicDBList());
		dbObject.put("downloads", new BasicDBList());
		dbObject.put("forks", new BasicDBList());
		dbObject.put("watchers", new BasicDBList());
		dbObject.put("milestones", new BasicDBList());
	}
	
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GitHubRepository setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	public boolean get_private() {
		return parseBoolean(dbObject.get("_private")+"", false);
	}
	
	public GitHubRepository set_private(boolean _private) {
		dbObject.put("_private", _private + "");
		notifyChanged();
		return this;
	}
	public boolean getFork() {
		return parseBoolean(dbObject.get("fork")+"", false);
	}
	
	public GitHubRepository setFork(boolean fork) {
		dbObject.put("fork", fork + "");
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitHubRepository setHtml_url(String html_url) {
		dbObject.put("html_url", html_url + "");
		notifyChanged();
		return this;
	}
	public String getClone_url() {
		return parseString(dbObject.get("clone_url")+"", "");
	}
	
	public GitHubRepository setClone_url(String clone_url) {
		dbObject.put("clone_url", clone_url + "");
		notifyChanged();
		return this;
	}
	public String getGitHub_url() {
		return parseString(dbObject.get("GitHub_url")+"", "");
	}
	
	public GitHubRepository setGitHub_url(String GitHub_url) {
		dbObject.put("GitHub_url", GitHub_url + "");
		notifyChanged();
		return this;
	}
	public String getSsh_url() {
		return parseString(dbObject.get("ssh_url")+"", "");
	}
	
	public GitHubRepository setSsh_url(String ssh_url) {
		dbObject.put("ssh_url", ssh_url + "");
		notifyChanged();
		return this;
	}
	public String getSvn_url() {
		return parseString(dbObject.get("svn_url")+"", "");
	}
	
	public GitHubRepository setSvn_url(String svn_url) {
		dbObject.put("svn_url", svn_url + "");
		notifyChanged();
		return this;
	}
	public String getMirror_url() {
		return parseString(dbObject.get("mirror_url")+"", "");
	}
	
	public GitHubRepository setMirror_url(String mirror_url) {
		dbObject.put("mirror_url", mirror_url + "");
		notifyChanged();
		return this;
	}
	public String getHomepage() {
		return parseString(dbObject.get("homepage")+"", "");
	}
	
	public GitHubRepository setHomepage(String homepage) {
		dbObject.put("homepage", homepage + "");
		notifyChanged();
		return this;
	}
	public String getLanguage() {
		return parseString(dbObject.get("language")+"", "");
	}
	
	public GitHubRepository setLanguage(String language) {
		dbObject.put("language", language + "");
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GitHubRepository setSize(int size) {
		dbObject.put("size", size + "");
		notifyChanged();
		return this;
	}
	public String getMaster_branch() {
		return parseString(dbObject.get("master_branch")+"", "");
	}
	
	public GitHubRepository setMaster_branch(String master_branch) {
		dbObject.put("master_branch", master_branch + "");
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubUser> getStargazers() {
		if (stargazers == null) {
			stargazers = new PongoList<GitHubUser>(this, "stargazers", true);
		}
		return stargazers;
	}
	public List<GitHubUser> getCollaborators() {
		if (collaborators == null) {
			collaborators = new PongoList<GitHubUser>(this, "collaborators", true);
		}
		return collaborators;
	}
	public List<GitHubCommit> getCommits() {
		if (commits == null) {
			commits = new PongoList<GitHubCommit>(this, "commits", true);
		}
		return commits;
	}
	public List<GitHubContent> getContents() {
		if (contents == null) {
			contents = new PongoList<GitHubContent>(this, "contents", true);
		}
		return contents;
	}
	public List<GitHubDownload> getDownloads() {
		if (downloads == null) {
			downloads = new PongoList<GitHubDownload>(this, "downloads", true);
		}
		return downloads;
	}
	public List<GitHubRepository> getForks() {
		if (forks == null) {
			forks = new PongoList<GitHubRepository>(this, "forks", true);
		}
		return forks;
	}
	public List<GitHubUser> getWatchers() {
		if (watchers == null) {
			watchers = new PongoList<GitHubUser>(this, "watchers", true);
		}
		return watchers;
	}
	public List<GitHubMilestone> getMilestones() {
		if (milestones == null) {
			milestones = new PongoList<GitHubMilestone>(this, "milestones", true);
		}
		return milestones;
	}
	
	
	public GitHubUser getOwner() {
		if (owner == null && dbObject.containsField("owner")) {
			owner = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("owner"));
		}
		return owner;
	}
	
	public GitHubRepository setOwner(GitHubUser owner) {
		if (this.owner != owner) {
			if (owner == null) {
				dbObject.removeField("owner");
			}
			else {
				dbObject.put("owner", owner.getDbObject());
			}
			this.owner = owner;
			notifyChanged();
		}
		return this;
	}
}