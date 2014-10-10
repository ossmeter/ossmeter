package org.ossmeter.platform.bugtrackingsystem.redmine.api;

import java.util.ArrayList;
import java.util.List;


public class RedmineBasicSearchResult extends RedmineSearchResult {
	private List<Integer> issueIds = new ArrayList<Integer>();

	public List<Integer> getIssueIds() {
		return issueIds;
	}
}
