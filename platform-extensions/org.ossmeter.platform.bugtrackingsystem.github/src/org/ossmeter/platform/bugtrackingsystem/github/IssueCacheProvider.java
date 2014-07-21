package org.ossmeter.platform.bugtrackingsystem.github;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.egit.github.core.Issue;
import org.joda.time.DateTime;
import org.ossmeter.platform.bugtrackingsystem.BugTrackerItemCache;
import org.ossmeter.platform.bugtrackingsystem.github.api.GitHubIssueQuery;
import org.ossmeter.platform.bugtrackingsystem.github.api.GitHubSession;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.github.GitHubBugTracker;

class IssueCacheProvider extends
		BugTrackerItemCache.Provider<GitHubIssue, String> {

	private class ItemIterator implements Iterator<GitHubIssue> {

		private Iterator<Issue> issues;
		// private Date after;
		private Date before;

		private Issue nextIssue;

		ItemIterator(GitHubBugTracker bugTracker, Date after, Date before)
				throws IOException {
			GitHubIssueQuery query = new GitHubIssueQuery(bugTracker.getUser(),
					bugTracker.getRepository());
			query = query.setAllState().setSince(new DateTime(after))
					.sortByUpdated().setAscendingDirection();

			GitHubSession github = GitHubManager.getSession(bugTracker);
			issues = new SimpleIterator<Issue>(github.getIssues(query));

			// this.after = after;
			this.before = before;
		}

		@Override
		public boolean hasNext() {
			while (issues.hasNext()) {
				Issue issue = issues.next();
				Date updated = issue.getUpdatedAt();

				if (null != before && updated.after(before)) {
					break;
				}

				nextIssue = issue;
				return true;
			}

			return false;
		}

		@Override
		public GitHubIssue next() {
			return processor.process(nextIssue);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final GitHubEntityManager processor;

	public IssueCacheProvider(GitHubEntityManager processor) {
		this.processor = processor;
	}

	@Override
	public Iterator<GitHubIssue> getItems(java.util.Date after,
			java.util.Date before, BugTrackingSystem bugTracker)
			throws Exception {

		return new ItemIterator((GitHubBugTracker) bugTracker, after, before);
	}

	@Override
	public void process(GitHubIssue item, BugTrackingSystem bugTracker) {
		item.setBugTrackingSystem(bugTracker); // Is this needed?

	}

	@Override
	public boolean changedOnDate(GitHubIssue item, java.util.Date date,
			BugTrackingSystem bugTracker) {
		return findMatchOnDate(date, item.getCreationTime(),
				item.getUpdatedTime());
	}

	@Override
	public boolean changedSinceDate(GitHubIssue item, java.util.Date date,
			BugTrackingSystem bugTracker) {
		return findMatchSinceDate(date, item.getCreationTime(),
				item.getUpdatedTime());
	}

	@Override
	public String getKey(GitHubIssue item) {
		return item.getBugId();
	}
}