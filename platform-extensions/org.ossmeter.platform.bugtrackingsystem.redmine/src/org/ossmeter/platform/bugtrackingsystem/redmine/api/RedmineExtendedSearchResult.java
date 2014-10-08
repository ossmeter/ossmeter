package org.ossmeter.platform.bugtrackingsystem.redmine.api;

import java.util.ArrayList;
import java.util.List;


public class RedmineExtendedSearchResult extends RedmineSearchResult {
	private List<RedmineIssue> issues = new ArrayList<RedmineIssue>();

	public List<RedmineIssue> getIssues() {
		return issues;
	}
}
