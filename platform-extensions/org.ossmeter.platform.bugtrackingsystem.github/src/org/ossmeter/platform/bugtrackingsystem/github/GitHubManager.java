package org.ossmeter.platform.bugtrackingsystem.github;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.egit.github.core.Comment;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.PullRequestMarker;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.client.PageIterator;
import org.joda.time.DateTime;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.bugtrackingsystem.github.api.ExtendedComment;
import org.ossmeter.platform.bugtrackingsystem.github.api.GitHubIssueQuery;
import org.ossmeter.platform.bugtrackingsystem.github.api.GitHubSession;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.github.GitHubBugTracker;

public class GitHubManager implements
        IBugTrackingSystemManager<GitHubBugTracker> {

    @Override
    public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
        return bugTrackingSystem instanceof GitHubBugTracker;
    }

    @Override
    public BugTrackingSystemDelta getDelta(GitHubBugTracker bts, Date date)
            throws Exception {
        GitHubBugTrackingSystemDelta delta = new GitHubBugTrackingSystemDelta();
        delta.setBugTrackingSystem(bts);

        GitHubSession github = getSession(bts);

        getIssues(bts, date, github, delta);
        getComments(bts, date, github, delta);
        getPullRequests(bts, date, github, delta);
        // NOTE: GitHub does not allow any file attachments to issues

        return delta;
    }

    @Override
    public Date getFirstDate(GitHubBugTracker bts) throws Exception {
        return getEarliestIssueDate(bts);
    }

    @Override
    public String getContents(GitHubBugTracker bts, BugTrackingSystemBug bug)
            throws Exception {
        GitHubSession github = getSession(bts);
        Issue issue = github.getIssue(bts.getUser(), bts.getRepository(),
                bug.getBugId());
        if (null != issue) {
            return issue.getBodyText();
        }
        return null;
    }

    @Override
    public String getContents(GitHubBugTracker bts,
            BugTrackingSystemComment comment) throws Exception {
        GitHubSession github = getSession(bts);

        long commentId = Long.parseLong(comment.getCommentId());
        Comment ghComment = github.getComment(bts.getUser(),
                bts.getRepository(), commentId);
        if (null != ghComment) {
            return ghComment.getBodyText();
        }
        return null;
    }

    private static GitHubSession getSession(GitHubBugTracker bts) {
        GitHubSession session = new GitHubSession();
        if (bts.getLogin() != null && bts.getPassword() != null) {
            session.login(bts.getLogin(), bts.getPassword());
        }
        return session;
    }

    private static void getPullRequests(GitHubBugTracker bts, Date since,
            GitHubSession github, GitHubBugTrackingSystemDelta delta) {
        DateTime date = new DateTime(since.toJavaDate());
        PageIterator<PullRequest> it = github.getPullRequests(bts.getUser(),
                bts.getRepository(), date);
        while (it.hasNext()) {
            Collection<PullRequest> pullRequests = it.next();
            for (PullRequest pr : pullRequests) {
                GitHubPullRequest pullRequest = new GitHubPullRequest();

                pullRequest.setAdditions(pr.getAdditions());
                pullRequest.setBody(pr.getBody());
                pullRequest.setBodyHtml(pr.getBodyHtml());
                pullRequest.setBodyText(pr.getBodyText());
                pullRequest.setChangedFiles(pr.getChangedFiles());
                pullRequest.setClosedAt(pr.getClosedAt());
                pullRequest.setComments(pr.getComments());
                pullRequest.setCommits(pr.getCommits());
                pullRequest.setCreatedAt(pr.getCreatedAt());
                pullRequest.setDeletions(pr.getDeletions());
                pullRequest.setDiffUrl(pr.getDiffUrl());
                pullRequest.setHtmlUrl(pr.getHtmlUrl());
                pullRequest.setId(pr.getId());
                pullRequest.setIssueUrl(pr.getIssueUrl());
                pullRequest.setMergeable(pr.isMergeable());
                pullRequest.setMerged(pr.isMerged());
                pullRequest.setMergedAt(pr.getMergedAt());
                pullRequest.setNumber(pr.getNumber());
                pullRequest.setPatchUrl(pr.getPatchUrl());
                pullRequest.setState(pr.getState());
                pullRequest.setTitle(pr.getTitle());
                pullRequest.setUpdatedAt(pr.getUpdatedAt());
                pullRequest.setUrl(pr.getUrl());

                pullRequest.setAssignee(processUser(pr.getAssignee(), delta));
                pullRequest.setMergedBy(processUser(pr.getMergedBy(), delta));
                pullRequest.setUser(processUser(pr.getUser(), delta));

                pullRequest.setMilestone(processMilestone(pr.getMilestone(),
                        delta));

                pullRequest.setBase(processPullRequestMarker(pr.getBase(),
                        delta));
                pullRequest.setHead(processPullRequestMarker(pr.getHead(),
                        delta));

                delta.addGitHubPullRequest(pullRequest);
            }
        }
    }

    private static GitHubPullRequestMarker processPullRequestMarker(
            PullRequestMarker prm, GitHubBugTrackingSystemDelta delta) {
        if (prm == null) {
            return null;
        }

        GitHubPullRequestMarker marker = new GitHubPullRequestMarker();
        marker.setLabel(prm.getLabel());
        marker.setRef(prm.getRef());
        marker.setSha(prm.getSha());
        marker.setUser(processUser(prm.getUser(), delta));

        marker.setRepository(processRepository(prm.getRepo(), delta));

        return marker;

    }

    private static Long processRepository(Repository repo,
            GitHubBugTrackingSystemDelta delta) {
        if (null == repo) {
            return null;
        }

        long id = repo.getId();
        if (!delta.hasRepository(id)) {
            GitHubRepository repository = new GitHubRepository();
            repository.setId(id);
            repository.setName(repo.getName());
            repository.setOwner(processUser(repo.getOwner(), delta));

            delta.addRepository(repository);
        }

        return id;
    }

    private static Integer processMilestone(Milestone milestone,
            GitHubBugTrackingSystemDelta delta) {
        if (null == milestone) {
            return null;
        }

        int number = milestone.getNumber();
        if (!delta.hasMilestone(number)) {
            GitHubMilestone ghMilestone = new GitHubMilestone(
                    milestone.getNumber());
            ghMilestone.setClosedIssues(milestone.getClosedIssues());
            ghMilestone.setCreatedAt(milestone.getCreatedAt());
            ghMilestone.setCreator(processUser(milestone.getCreator(), delta));
            ghMilestone.setDescription(milestone.getDescription());
            ghMilestone.setDueOn(milestone.getDueOn());
            ghMilestone.setOpenIssues(milestone.getOpenIssues());
            ghMilestone.setStatus(milestone.getState());
            ghMilestone.setTitle(milestone.getTitle());
            ghMilestone.setUrl(milestone.getUrl());

            delta.addMilestone(ghMilestone);

        }

        return number;
    }

    private static String processUser(User user,
            GitHubBugTrackingSystemDelta delta) {
        if (null == user) {
            return null;
        }

        String id = Integer.toString(user.getId());
        if (!delta.hasUser(id)) {
            GitHubUser ghUser = new GitHubUser();
            ghUser.setId(id);
            ghUser.setLogin(user.getLogin());
            ghUser.setName(user.getName());
            ghUser.setType(user.getType());
            ghUser.setUrl(user.getUrl());

            // TODO complete GitHubUser object!!
            delta.addUser(ghUser);
        }

        return id;
    }

    private static Date getEarliestIssueDate(GitHubBugTracker bts)
            throws IOException {
        GitHubSession github = getSession(bts);
        GitHubIssueQuery query = new GitHubIssueQuery(bts.getUser(),
                bts.getRepository());
        query.sortByCreated();
        query.setAscendingDirection();
        query.setPageSize(1);
        PageIterator<Issue> pages = github.getIssues(query);
        if (pages.hasNext()) {
            Collection<Issue> issues = pages.next();
            Iterator<Issue> it = issues.iterator();
            if (it.hasNext()) {
                return new Date(it.next().getCreatedAt());
            }
        }

        return null;
    }

    /**
     * 
     * Gets issues that have either been updated or created since the given date
     * 
     * @param bugTracker
     * @param since
     * @param github
     * @param delta
     * @throws IOException
     */
    private static void getIssues(GitHubBugTracker bugTracker, Date on,
            GitHubSession github, GitHubBugTrackingSystemDelta delta)
            throws IOException {

        List<BugTrackingSystemBug> newBugs = delta.getNewBugs();
        List<BugTrackingSystemBug> updatedBugs = delta.getUpdatedBugs();

        java.util.Date javaDate = on.toJavaDate();

        // First deal with bugs that were created on the given date
        GitHubIssueQuery query = new GitHubIssueQuery(bugTracker.getUser(),
                bugTracker.getRepository());
        query.setSince(new DateTime(javaDate)).sortByCreated()
                .setAscendingDirection();
        PageIterator<Issue> issues = github.getIssues(query);

        boolean finished = false;
        while (issues.hasNext() && !finished) {
            for (Issue issue : issues.next()) {
                if (!DateUtils.isSameDay(javaDate, issue.getUpdatedAt())) {
                    // Stop if we have gone past current date
                    finished = true;
                    break;
                }

                storeIssue(issue, newBugs, bugTracker, delta);
            }
        }

        // Secondly, deal with bugs that were updated on the given date
        query.sortByUpdated().setAscendingDirection();
        issues = github.getIssues(query);
        finished = false;
        while (issues.hasNext() && !finished) {
            for (Issue issue : issues.next()) {
                if (!DateUtils.isSameDay(javaDate, issue.getUpdatedAt())) {
                    // Stop if we have gone past current date
                    finished = true;
                    break;
                } else if (DateUtils.isSameDay(javaDate, issue.getCreatedAt())) {
                    // Issue was created on this day, so already in the new bugs
                    // list. So ignore it.
                    continue;
                }

                storeIssue(issue, updatedBugs, bugTracker, delta);
            }
        }
    }

    /**
     * 
     * Gets comments that have either been updated or created since the given
     * date
     * 
     * @param bugTracker
     * @param since
     * @param github
     * @param delta
     * @throws IOException
     */

    private static void getComments(GitHubBugTracker bugTracker, Date since,
            GitHubSession github, GitHubBugTrackingSystemDelta delta)
            throws IOException {

        java.util.Date javaDate = since.toJavaDate();

        PageIterator<ExtendedComment> comments = github.getComments(bugTracker
                .getUser(), bugTracker.getRepository(), new DateTime(javaDate), GitHubSession.SORT_CREATED);

        List<BugTrackingSystemComment> deltaComments = delta.getComments();
        
        boolean finished = false;
        while (comments.hasNext() && !finished) {
            for (ExtendedComment comment : comments.next()) {
                if (!DateUtils.isSameDay(comment.getCreatedAt(), javaDate)) {
                    finished = true;
                    break;
                }
                storeComment(comment, deltaComments, bugTracker, delta);
            }
  
        }
        
        comments = github.getComments(bugTracker
                .getUser(), bugTracker.getRepository(), new DateTime(javaDate), GitHubSession.SORT_UPDATED);
        finished = false;
        while (comments.hasNext() && !finished) {
            for (ExtendedComment comment : comments.next()) {
                if (!DateUtils.isSameDay(javaDate, comment.getUpdatedAt())) {
                    finished = true;
                    break;
                } else if (DateUtils.isSameDay(javaDate, comment.getCreatedAt())) {
                    continue;
                }
                
                storeComment(comment, deltaComments, bugTracker, delta);
            }
        }

    }

    protected static void storeIssue(Issue issue,
            List<BugTrackingSystemBug> bugs, BugTrackingSystem bts,
            GitHubBugTrackingSystemDelta delta) {
        GitHubIssue ghIssue = new GitHubIssue();

        ghIssue.setAssignee(processUser(issue.getAssignee(), delta));
        ghIssue.setBody(issue.getBody());
        ghIssue.setBodyHtml(issue.getBodyHtml());
        ghIssue.setBodyText(issue.getBodyText());
        ghIssue.setBugId(Long.toString(issue.getId()));
        ghIssue.setClosedTime(issue.getClosedAt());
        ghIssue.setCreationTime(issue.getCreatedAt());
        ghIssue.setCreator(processUser(issue.getUser(), delta));
        ghIssue.setNumComments(issue.getComments());
        ghIssue.setStatus(issue.getState());
        ghIssue.setTitle(issue.getTitle());
        ghIssue.setUpdatedTime(issue.getUpdatedAt());
        ghIssue.setUrl(issue.getUrl());
        ghIssue.setHtmlUrl(issue.getHtmlUrl());
        ghIssue.setMilestone(processMilestone(issue.getMilestone(), delta));

        for (Label label : issue.getLabels()) {
            GitHubLabel ghLabel = new GitHubLabel();
            ghLabel.setColour(label.getColor());
            ghLabel.setName(label.getName());
            ghLabel.setUrl(label.getUrl());
            ghIssue.addLabel(ghLabel);
        }

        // Is this needed?
        ghIssue.setBugTrackingSystem(bts);

        bugs.add(ghIssue);

    }

    protected static void storeComment(ExtendedComment comment,
            List<BugTrackingSystemComment> comments, BugTrackingSystem bts,
            GitHubBugTrackingSystemDelta delta) {
        GitHubComment ghComment = new GitHubComment();

        ghComment.setCommentId(Long.toString(comment.getId()));
        ghComment.setBugId(Long.toString(comment.getIssueId()));
        ghComment.setCreationTime(comment.getCreatedAt());
        ghComment.setCreator(processUser(comment.getUser(), delta));
        ghComment.setUpdatedAt(comment.getUpdatedAt());
        ghComment.setUrl(comment.getUrl());

        // Is this needed?
        ghComment.setBugTrackingSystem(bts);

        comments.add(ghComment);
    }

    public static void main(String[] args) throws Exception {

        GitHubBugTracker bugTracker = new GitHubBugTracker();
        bugTracker.setUser("sampsyo");
        bugTracker.setRepository("beets");
        bugTracker.setLogin("ossmetertest");
        bugTracker.setPassword("T35tAccount");

        //Date date = new Date(new DateTime(2014, 6, 1, 0, 0).toDate());
       Date date = new Date(new DateTime(2011, 2, 18, 0, 0).toDate());

        GitHubManager github = new GitHubManager();
        GitHubBugTrackingSystemDelta delta = (GitHubBugTrackingSystemDelta) github
                .getDelta(bugTracker, date);

        System.out.println(delta.getNewBugs().size());
        System.out.println(delta.getUpdatedBugs().size());
        for (BugTrackingSystemBug bug : delta.getNewBugs()) {
            System.out.println(bug.getBugId() + " " + bug.getCreationTime());
        }
        System.out.println("===UPDATED===");
        for (BugTrackingSystemBug bug : delta.getUpdatedBugs()) {
            System.out.println(bug.getBugId() + " " + bug.getCreationTime());
        }
        System.out.println("===COMMENTS===");
        for (BugTrackingSystemComment comment : delta.getComments()) {
            GitHubComment ghComment = (GitHubComment) comment;
            System.out.println(comment.getCommentId() + " "
                    + ghComment.getUpdatedAt());
        }
        System.out.println("==PULL REQUESTS===");
        for (GitHubPullRequest pr : delta.getPullRequests()) {
            System.out.println(pr.getId() + " " + pr.getUpdatedAt());
        }

        /*
         * GitHubSession github = new GitHubSession(); DateTime since = new
         * DateTime(); since = since.withDayOfMonth(1); since =
         * since.withMonthOfYear(4);
         * 
         * System.out.println(since);
         * 
         * List<ExtendedComment> comments = github.getComments("eclipse",
         * "egit-github", since); for (ExtendedComment comment : comments) {
         * System.out.println(comment.getIssueUrl()); }
         */
    }
}
