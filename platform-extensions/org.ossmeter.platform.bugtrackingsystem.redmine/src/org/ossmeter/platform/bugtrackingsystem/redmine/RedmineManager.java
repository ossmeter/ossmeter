package org.ossmeter.platform.bugtrackingsystem.redmine;

import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.bugtrackingsystem.BugTrackerItemCache;
import org.ossmeter.platform.bugtrackingsystem.BugTrackerItemCaches;
import org.ossmeter.platform.bugtrackingsystem.redmine.api.RedmineComment;
import org.ossmeter.platform.bugtrackingsystem.redmine.api.RedmineIssue;
import org.ossmeter.platform.bugtrackingsystem.redmine.api.RedmineRestClient;
import org.ossmeter.platform.bugtrackingsystem.redmine.api.RedmineExtendedSearchResult;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.redmine.RedmineBugIssueTracker;

import com.google.common.collect.ImmutableMap;

public class RedmineManager implements
		IBugTrackingSystemManager<RedmineBugIssueTracker> {

	private BugTrackerItemCaches<RedmineIssue, String> issueCaches = new BugTrackerItemCaches<RedmineIssue, String>(
			new IssueCacheProvider());

	private static class IssueCacheProvider extends
			BugTrackerItemCache.Provider<RedmineIssue, String> {

		@Override
		public Iterator<RedmineIssue> getItems(java.util.Date after,
				java.util.Date before, BugTrackingSystem bugTracker)
				throws Exception {
			RedmineBugIssueTracker bts = (RedmineBugIssueTracker) bugTracker;
			RedmineRestClient redmine = getRedmineRestClient(bts);
			return redmine.getIssues(bts.getProject(), after, before);
		}

		@Override
		public boolean changedOnDate(RedmineIssue item, java.util.Date date,
				BugTrackingSystem bugTracker) {
			return findMatchOnDate(date, item.getCreationTime(),
					item.getUpdateDate());
		}

		@Override
		public boolean changedSinceDate(RedmineIssue item, java.util.Date date,
				BugTrackingSystem bugTracker) {
			return findMatchSinceDate(date, item.getCreationTime(),
					item.getUpdateDate());
		}

		@Override
		public String getKey(RedmineIssue item) {
			return item.getBugId();
		}

		@Override
		public void process(RedmineIssue item, BugTrackingSystem bugTracker) {
			item.setBugTrackingSystem(bugTracker); // Is this needed?
			item.setDescription(null); // remove content field
			for (BugTrackingSystemComment comment : item.getComments()) {
				comment.setBugTrackingSystem(bugTracker); // Is this needed?
				comment.setText(null); // remove content field
			}
		}

	}
	
	public void shutdown() {
		try {
			RedmineRestClient.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean appliesTo(BugTrackingSystem bugTracker) {
		return bugTracker instanceof RedmineBugIssueTracker;
	}

	@Override
	public BugTrackingSystemDelta getDelta(RedmineBugIssueTracker bugTracker,
			Date date) throws Exception {
		java.util.Date day = date.toJavaDate();

		BugTrackerItemCache<RedmineIssue, String> issueCache = issueCaches
				.getCache(bugTracker, true);
		Iterable<RedmineIssue> issues = issueCache.getItemsAfterDate(day);

		RedmineBugTrackingSystemDelta delta = new RedmineBugTrackingSystemDelta();
		for (RedmineIssue issue : issues) {

			if (DateUtils.isSameDay(issue.getCreationTime(), day)) {
				delta.getNewBugs().add(issue);
			} else if (DateUtils.isSameDay(issue.getUpdateDate(), day)) {
				delta.getUpdatedBugs().add(issue);
			} 

			// Store updated comments in delta
			for (BugTrackingSystemComment comment : issue.getComments()) {
				RedmineComment redmineComment = (RedmineComment) comment;
				java.util.Date created = redmineComment.getCreationTime();

				if (DateUtils.isSameDay(created, day)) {
					delta.getComments().add(comment);
				}
			}
		}

		return delta;
	}

	@Override
	public Date getFirstDate(RedmineBugIssueTracker bugTracker)
			throws Exception {
		RedmineRestClient redmine = getRedmineRestClient(bugTracker);
		RedmineExtendedSearchResult result = redmine.extendedSearch(
				ImmutableMap.of("project_id", bugTracker.getProject(), "sort",
						"created_on:asc", "status_id","*"), 0, 1);
		if (result.getTotalCount() > 0) {
			return new Date(result.getIssues().get(0).getCreationTime());
		}

		return null;
	}

	@Override
	public String getContents(RedmineBugIssueTracker bugTracker,
			BugTrackingSystemBug bug) throws Exception {
		RedmineRestClient redmine = getRedmineRestClient(bugTracker);
		int bugId = Integer.parseInt(bug.getBugId());
		RedmineIssue issue = redmine.getIssue(bugId);
		if (null != issue) {
			return issue.getDescription();
		}

		return null;
	}

	@Override
	public String getContents(RedmineBugIssueTracker bugTracker,
			BugTrackingSystemComment comment) throws Exception {
		RedmineRestClient redmine = getRedmineRestClient(bugTracker);
		int bugId = Integer.parseInt(comment.getBugId());
		int commentId = Integer.parseInt(comment.getCommentId());
		RedmineComment redmineComment = redmine.getComment(bugId, commentId);
		if (null != redmineComment) {
			return redmineComment.getText();
		}
		return null;
	}

	protected static RedmineRestClient getRedmineRestClient(
			RedmineBugIssueTracker bugTracker) {
		RedmineRestClient client =  new RedmineRestClient(bugTracker.getUrl());
		String login = bugTracker.getLogin();
		if ( login != null && login.trim().length() > 0 && !"null".equals(login)) {
			client.setCredentials(login, bugTracker.getPassword());
		}
		return client;
	}

	public static void main(String[] args) throws Exception {
		RedmineManager manager = new RedmineManager();
		RedmineBugIssueTracker bugTracker = new RedmineBugIssueTracker();
		bugTracker.setUrl("http://www.redmine.org/");
		bugTracker.setProject("redmine");
		Date firstDate = manager.getFirstDate(bugTracker);
		System.out.println("FIRST DATE: " + firstDate); // Should be 2007-02-24
		System.out.println();

		RedmineIssue issue = new RedmineIssue();
		issue.setBugId("285");
		System.out.println(manager.getContents(bugTracker, issue));
		System.out.println();

		RedmineComment comment = new RedmineComment();
		comment.setBugId("277");
		comment.setCommentId("597");
		System.out.println(manager.getContents(bugTracker, comment));
		System.out.println();

		BugTrackingSystemDelta delta = manager.getDelta(bugTracker, new Date(
				"20140714"));
		System.out.println(delta.getUpdatedBugs().size());

		manager.shutdown();
	}

}
