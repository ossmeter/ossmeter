package org.ossmeter.repository.model.github;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GitHubDownload extends Pongo {
	
	
	
	public GitHubDownload() { 
		super();
	}
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubDownload setUrl(String url) {
		dbObject.put("url", url + "");
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitHubDownload setHtml_url(String html_url) {
		dbObject.put("html_url", html_url + "");
		notifyChanged();
		return this;
	}
	public String getName() {
		return parseString(dbObject.get("name")+"", "");
	}
	
	public GitHubDownload setName(String name) {
		dbObject.put("name", name + "");
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public GitHubDownload setDescription(String description) {
		dbObject.put("description", description + "");
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GitHubDownload setSize(int size) {
		dbObject.put("size", size + "");
		notifyChanged();
		return this;
	}
	public int getDownload_count() {
		return parseInteger(dbObject.get("download_count")+"", 0);
	}
	
	public GitHubDownload setDownload_count(int download_count) {
		dbObject.put("download_count", download_count + "");
		notifyChanged();
		return this;
	}
	public String getContent_type() {
		return parseString(dbObject.get("content_type")+"", "");
	}
	
	public GitHubDownload setContent_type(String content_type) {
		dbObject.put("content_type", content_type + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}