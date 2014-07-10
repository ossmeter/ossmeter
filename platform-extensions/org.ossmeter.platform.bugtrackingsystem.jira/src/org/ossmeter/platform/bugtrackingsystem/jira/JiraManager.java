package org.ossmeter.platform.bugtrackingsystem.jira;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.bugtrackingsystem.BugTrackerItemCache;
import org.ossmeter.platform.bugtrackingsystem.BugTrackerItemCaches;
import org.ossmeter.platform.bugtrackingsystem.jira.api.JiraComment;
import org.ossmeter.platform.bugtrackingsystem.jira.api.JiraIssue;
import org.ossmeter.platform.bugtrackingsystem.jira.api.JiraRestClient;
import org.ossmeter.platform.bugtrackingsystem.jira.api.JiraSearchResult;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.jira.JiraBugTrackingSystem;

public class JiraManager implements
        IBugTrackingSystemManager<JiraBugTrackingSystem> {

    private BugTrackerItemCaches<JiraIssue, String> issueCaches = new BugTrackerItemCaches<JiraIssue, String>(
            new IssueCacheProvider());

    private static class IssueCacheProvider extends
            BugTrackerItemCache.Provider<JiraIssue, String> {

        @Override
        public Iterable<JiraIssue> getItems(java.util.Date after,
                java.util.Date before, BugTrackingSystem bugTracker)
                throws Exception {
            JiraBugTrackingSystem bts = (JiraBugTrackingSystem) bugTracker;
            JiraRestClient jira = getJiraRestClient(bts);
            return jira.getIssues(bts.getProject(), after, before);
        }

        @Override
        public boolean changedOnDate(JiraIssue item, java.util.Date date,
                BugTrackingSystem bugTracker) {
            return findMatchOnDate(date, item.getCreationTime(),
                    item.getUpdateDate());
        }

        @Override
        public boolean changedSinceDate(JiraIssue item, java.util.Date date,
                BugTrackingSystem bugTracker) {
            return findMatchSinceDate(date, item.getCreationTime(),
                    item.getUpdateDate());
        }

        @Override
        public String getKey(JiraIssue item) {
            return item.getBugId();
        }

    }

    @Override
    public boolean appliesTo(BugTrackingSystem bts) {
        return bts instanceof JiraBugTrackingSystem;
    }

    @Override
    public BugTrackingSystemDelta getDelta(JiraBugTrackingSystem bugTracker,
            Date date) throws Exception {

        java.util.Date day = date.toJavaDate();

        BugTrackerItemCache<JiraIssue, String> issueCache = issueCaches
                .getCache(bugTracker, true);
        Iterable<JiraIssue> issues = issueCache.getItemsAfterDate(day);

        JiraBugTrackingSystemDelta delta = new JiraBugTrackingSystemDelta();
        for (JiraIssue issue : issues) {

            if (DateUtils.isSameDay(issue.getUpdateDate(), day)) {
                delta.getUpdatedBugs().add(issue);
            } else if (DateUtils.isSameDay(issue.getCreationTime(), day)) {
                delta.getNewBugs().add(issue);
            }

            // Store updated comments in delta
            for (BugTrackingSystemComment comment : issue.getComments()) {
                JiraComment jiraComment = (JiraComment) comment;

                java.util.Date updated = jiraComment.getUpdateDate();
                java.util.Date created = jiraComment.getCreationTime();

                if (DateUtils.isSameDay(created, day)) {
                    delta.getComments().add(comment);
                    comment.setBugTrackingSystem(bugTracker);  // TODO is this needed?
                } else if (updated != null && DateUtils.isSameDay(updated, day)) {
                    delta.getComments().add(comment);
                    comment.setBugTrackingSystem(bugTracker);  // TODO is this needed? 
                }
            }

            // TODO is this needed?
            issue.setBugTrackingSystem(bugTracker);         
        }

        return delta;
    }

    @Override
    public Date getFirstDate(JiraBugTrackingSystem bts) throws Exception {
        JiraRestClient jira = getJiraRestClient(bts);

        // Only interested in created field
        JiraSearchResult result = jira.search("project = \"" + bts.getProject()
                + "\" order by created asc", 0, 1, "created");

        if (!result.getIssues().isEmpty()) {
            JiraIssue issue = result.getIssues().get(0);
            return new Date(issue.getCreationTime());
        }

        return null;
    }

    @Override
    public String getContents(JiraBugTrackingSystem bts,
            BugTrackingSystemBug bug) throws Exception {

        JiraRestClient jira = getJiraRestClient(bts);
        // Request only the description field in the rest response
        JiraIssue issue = jira.getIssue(bug.getBugId(), "description");
        if (null != issue) {
            return issue.getDescription();
        }

        return null;
    }

    @Override
    public String getContents(JiraBugTrackingSystem bts,
            BugTrackingSystemComment bugComment) throws Exception {

        JiraRestClient jira = getJiraRestClient(bts);
        JiraComment comment = jira.getComment(bugComment.getBugId(),
                bugComment.getCommentId());
        if (null != comment) {
            return comment.getText();
        }
        return null;
    }

    protected static JiraRestClient getJiraRestClient(JiraBugTrackingSystem bts) {
        // TODO bts.getUser() and bts.getPassword() don't return null if they
        // don't have a value, but a string value of "null". Why? Can this be
        // fixed?
        return new JiraRestClient(bts.getUrl());
        // return new JiraRestClient(bts.getUrl(), bts.getUser(),
        // bts.getPassword())
    }

    public static void main(String[] args) throws Exception {

        JiraManager jm = new JiraManager();

        JiraBugTrackingSystem bts = new JiraBugTrackingSystem();
        bts.setUrl("http://jira.codehaus.org/");
        bts.setProject("MNG");

        JiraBugTrackingSystemDelta delta = (JiraBugTrackingSystemDelta) jm
                .getDelta(bts,
                        new Date(new DateTime(2014, 6, 26, 0, 0).toDate()));

        System.out.println(delta.getNewBugs().size());
        System.out.println(delta.getUpdatedBugs().size());
        for (BugTrackingSystemBug bug : delta.getNewBugs()) {
            System.out.println(bug.getBugId() + " " + bug.getCreationTime());
        }
        System.out.println("===UPDATED===");
        for (BugTrackingSystemBug bug : delta.getUpdatedBugs()) {
            JiraIssue issue = (JiraIssue) bug;
            System.out.println(bug.getBugId() + " " + issue.getUpdateDate());
        }
        System.out.println("===COMMENTS===");
        for (BugTrackingSystemComment comment : delta.getComments()) {
            JiraComment jiraComment = (JiraComment) comment;
            System.out.println(comment.getCommentId() + " "
                    + jiraComment.getUpdateDate());
        }
    }

}
