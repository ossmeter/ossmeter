package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubContent extends Pongo {
	
	
	
	public GitHubContent() { 
		super();
	}
	
	public GitHubContentType getType() {
		GitHubContentType type = null;
		try {
			type = GitHubContentType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public GitHubContent setType(GitHubContentType type) {
		dbObject.put("type", type + "");
		notifyChanged();
		return this;
	}
	public String getEnvoding() {
		return parseString(dbObject.get("envoding")+"", "");
	}
	
	public GitHubContent setEnvoding(String envoding) {
		dbObject.put("envoding", envoding + "");
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GitHubContent setSize(int size) {
		dbObject.put("size", size + "");
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GitHubContent setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public GitHubContent setPath(String path) {
		dbObject.put("path", path + "");
		notifyChanged();
		return this;
	}
	public String getSha() {
		return parseString(dbObject.get("sha")+"", "");
	}
	
	public GitHubContent setSha(String sha) {
		dbObject.put("sha", sha + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}