package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GITRepository extends VCSRepository {
	
	protected List<GITUser> stargazers = null;
	protected List<GITUser> collaborators = null;
	protected List<GITComment> comments = null;
	protected List<GITCommit> commits = null;
	protected List<GITContent> contens = null;
	protected List<GITDownload> downloads = null;
	protected List<GITRepository> forks = null;
	protected List<GITUser> watchers = null;
	protected List<GITIssue> open_issues = null;
	protected GITUser owner = null;
	
	
	public GITRepository() { 
		super();
		dbObject.put("owner", new BasicDBObject());
		dbObject.put("stargazers", new BasicDBList());
		dbObject.put("collaborators", new BasicDBList());
		dbObject.put("comments", new BasicDBList());
		dbObject.put("commits", new BasicDBList());
		dbObject.put("contens", new BasicDBList());
		dbObject.put("downloads", new BasicDBList());
		dbObject.put("forks", new BasicDBList());
		dbObject.put("watchers", new BasicDBList());
		dbObject.put("open_issues", new BasicDBList());
		super.setSuperTypes("com.googlecode.pongo.tests.ossmeter.model.VCSRepository");
		CREATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		UPDATED_AT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		FULL_NAME.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		_PRIVATE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		FORK.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		HTML_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		CLONE_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		GIT_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		SSH_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		SVN_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		MIRROR_URL.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		HOMEPAGE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		LANGUAGE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		SIZE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
		MASTER_BRANCH.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GITRepository");
	}
	
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer FULL_NAME = new StringQueryProducer("full_name"); 
	public static StringQueryProducer _PRIVATE = new StringQueryProducer("_private"); 
	public static StringQueryProducer FORK = new StringQueryProducer("fork"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url"); 
	public static StringQueryProducer CLONE_URL = new StringQueryProducer("clone_url"); 
	public static StringQueryProducer GIT_URL = new StringQueryProducer("git_url"); 
	public static StringQueryProducer SSH_URL = new StringQueryProducer("ssh_url"); 
	public static StringQueryProducer SVN_URL = new StringQueryProducer("svn_url"); 
	public static StringQueryProducer MIRROR_URL = new StringQueryProducer("mirror_url"); 
	public static StringQueryProducer HOMEPAGE = new StringQueryProducer("homepage"); 
	public static StringQueryProducer LANGUAGE = new StringQueryProducer("language"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static StringQueryProducer MASTER_BRANCH = new StringQueryProducer("master_branch"); 
	
	
	public String getFull_name() {
		return parseString(dbObject.get("full_name")+"", "");
	}
	
	public GITRepository setFull_name(String full_name) {
		dbObject.put("full_name", full_name);
		notifyChanged();
		return this;
	}
	public boolean get_private() {
		return parseBoolean(dbObject.get("_private")+"", false);
	}
	
	public GITRepository set_private(boolean _private) {
		dbObject.put("_private", _private);
		notifyChanged();
		return this;
	}
	public boolean getFork() {
		return parseBoolean(dbObject.get("fork")+"", false);
	}
	
	public GITRepository setFork(boolean fork) {
		dbObject.put("fork", fork);
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GITRepository setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
		notifyChanged();
		return this;
	}
	public String getClone_url() {
		return parseString(dbObject.get("clone_url")+"", "");
	}
	
	public GITRepository setClone_url(String clone_url) {
		dbObject.put("clone_url", clone_url);
		notifyChanged();
		return this;
	}
	public String getGit_url() {
		return parseString(dbObject.get("git_url")+"", "");
	}
	
	public GITRepository setGit_url(String git_url) {
		dbObject.put("git_url", git_url);
		notifyChanged();
		return this;
	}
	public String getSsh_url() {
		return parseString(dbObject.get("ssh_url")+"", "");
	}
	
	public GITRepository setSsh_url(String ssh_url) {
		dbObject.put("ssh_url", ssh_url);
		notifyChanged();
		return this;
	}
	public String getSvn_url() {
		return parseString(dbObject.get("svn_url")+"", "");
	}
	
	public GITRepository setSvn_url(String svn_url) {
		dbObject.put("svn_url", svn_url);
		notifyChanged();
		return this;
	}
	public String getMirror_url() {
		return parseString(dbObject.get("mirror_url")+"", "");
	}
	
	public GITRepository setMirror_url(String mirror_url) {
		dbObject.put("mirror_url", mirror_url);
		notifyChanged();
		return this;
	}
	public String getHomepage() {
		return parseString(dbObject.get("homepage")+"", "");
	}
	
	public GITRepository setHomepage(String homepage) {
		dbObject.put("homepage", homepage);
		notifyChanged();
		return this;
	}
	public String getLanguage() {
		return parseString(dbObject.get("language")+"", "");
	}
	
	public GITRepository setLanguage(String language) {
		dbObject.put("language", language);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GITRepository setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public String getMaster_branch() {
		return parseString(dbObject.get("master_branch")+"", "");
	}
	
	public GITRepository setMaster_branch(String master_branch) {
		dbObject.put("master_branch", master_branch);
		notifyChanged();
		return this;
	}
	
	
	public List<GITUser> getStargazers() {
		if (stargazers == null) {
			stargazers = new PongoList<GITUser>(this, "stargazers", true);
		}
		return stargazers;
	}
	public List<GITUser> getCollaborators() {
		if (collaborators == null) {
			collaborators = new PongoList<GITUser>(this, "collaborators", true);
		}
		return collaborators;
	}
	public List<GITComment> getComments() {
		if (comments == null) {
			comments = new PongoList<GITComment>(this, "comments", true);
		}
		return comments;
	}
	public List<GITCommit> getCommits() {
		if (commits == null) {
			commits = new PongoList<GITCommit>(this, "commits", true);
		}
		return commits;
	}
	public List<GITContent> getContens() {
		if (contens == null) {
			contens = new PongoList<GITContent>(this, "contens", true);
		}
		return contens;
	}
	public List<GITDownload> getDownloads() {
		if (downloads == null) {
			downloads = new PongoList<GITDownload>(this, "downloads", true);
		}
		return downloads;
	}
	public List<GITRepository> getForks() {
		if (forks == null) {
			forks = new PongoList<GITRepository>(this, "forks", true);
		}
		return forks;
	}
	public List<GITUser> getWatchers() {
		if (watchers == null) {
			watchers = new PongoList<GITUser>(this, "watchers", true);
		}
		return watchers;
	}
	public List<GITIssue> getOpen_issues() {
		if (open_issues == null) {
			open_issues = new PongoList<GITIssue>(this, "open_issues", true);
		}
		return open_issues;
	}
	
	
	public GITUser getOwner() {
		if (owner == null && dbObject.containsField("owner")) {
			owner = (GITUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("owner"));
			owner.setContainer(this);
		}
		return owner;
	}
	
	public GITRepository setOwner(GITUser owner) {
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