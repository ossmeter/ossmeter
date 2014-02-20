package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public abstract class Measureable extends Pongo {
	
	protected List<IssueEvent> issueEvents = null;
	protected List<String> commitTimes = null;
	protected List<String> commitCommentTimes = null;
	protected List<String> pullRequestTimes = null;
	protected List<String> pullRequestCommentTimes = null;
	protected List<String> issueTimes = null;
	protected List<String> issueCommentTimes = null;
	protected List<Artefact> artefacts = null;
	
	
	public Measureable() { 
		super();
		dbObject.put("issueEvents", new BasicDBList());
		dbObject.put("commitTimes", new BasicDBList());
		dbObject.put("commitCommentTimes", new BasicDBList());
		dbObject.put("pullRequestTimes", new BasicDBList());
		dbObject.put("pullRequestCommentTimes", new BasicDBList());
		dbObject.put("issueTimes", new BasicDBList());
		dbObject.put("issueCommentTimes", new BasicDBList());
		dbObject.put("artefacts", new BasicDBList());
		COMMITCOUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITTOTALCHANGES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITADDITIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITDELETIONS.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITSASAUTHOR.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITSASCOMMITTER.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITTOTALFILES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		AVERAGEFILESPERCOMMIT.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		COMMITCOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		PULLREQUESTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		PULLREQUESTCOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		ISSUETIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		ISSUECOMMENTTIMES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		NUMBEROFISSUES.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		NUMBEROFCOMMITCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		NUMBEROFISSUECOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		NUMBEROFPULLREQUESTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
		NUMBEROFPULLREQUESTCOMMENTS.setOwningType("org.ossmeter.platform.mining.msr14.model.Measureable");
	}
	
	public static NumericalQueryProducer COMMITCOUNT = new NumericalQueryProducer("commitCount");
	public static NumericalQueryProducer COMMITTOTALCHANGES = new NumericalQueryProducer("commitTotalChanges");
	public static NumericalQueryProducer COMMITADDITIONS = new NumericalQueryProducer("commitAdditions");
	public static NumericalQueryProducer COMMITDELETIONS = new NumericalQueryProducer("commitDeletions");
	public static NumericalQueryProducer COMMITSASAUTHOR = new NumericalQueryProducer("commitsAsAuthor");
	public static NumericalQueryProducer COMMITSASCOMMITTER = new NumericalQueryProducer("commitsAsCommitter");
	public static NumericalQueryProducer COMMITTOTALFILES = new NumericalQueryProducer("commitTotalFiles");
	public static NumericalQueryProducer AVERAGEFILESPERCOMMIT = new NumericalQueryProducer("averageFilesPerCommit");
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer NUMBEROFCOMMITCOMMENTS = new NumericalQueryProducer("numberOfCommitComments");
	public static NumericalQueryProducer NUMBEROFISSUECOMMENTS = new NumericalQueryProducer("numberOfIssueComments");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTS = new NumericalQueryProducer("numberOfPullRequests");
	public static NumericalQueryProducer NUMBEROFPULLREQUESTCOMMENTS = new NumericalQueryProducer("numberOfPullRequestComments");
	public static ArrayQueryProducer COMMITTIMES = new ArrayQueryProducer("commitTimes");
	public static ArrayQueryProducer COMMITCOMMENTTIMES = new ArrayQueryProducer("commitCommentTimes");
	public static ArrayQueryProducer PULLREQUESTTIMES = new ArrayQueryProducer("pullRequestTimes");
	public static ArrayQueryProducer PULLREQUESTCOMMENTTIMES = new ArrayQueryProducer("pullRequestCommentTimes");
	public static ArrayQueryProducer ISSUETIMES = new ArrayQueryProducer("issueTimes");
	public static ArrayQueryProducer ISSUECOMMENTTIMES = new ArrayQueryProducer("issueCommentTimes");
	
	
	public int getCommitCount() {
		return parseInteger(dbObject.get("commitCount")+"", 0);
	}
	
	public Measureable setCommitCount(int commitCount) {
		dbObject.put("commitCount", commitCount);
		notifyChanged();
		return this;
	}
	public int getCommitTotalChanges() {
		return parseInteger(dbObject.get("commitTotalChanges")+"", 0);
	}
	
	public Measureable setCommitTotalChanges(int commitTotalChanges) {
		dbObject.put("commitTotalChanges", commitTotalChanges);
		notifyChanged();
		return this;
	}
	public int getCommitAdditions() {
		return parseInteger(dbObject.get("commitAdditions")+"", 0);
	}
	
	public Measureable setCommitAdditions(int commitAdditions) {
		dbObject.put("commitAdditions", commitAdditions);
		notifyChanged();
		return this;
	}
	public int getCommitDeletions() {
		return parseInteger(dbObject.get("commitDeletions")+"", 0);
	}
	
	public Measureable setCommitDeletions(int commitDeletions) {
		dbObject.put("commitDeletions", commitDeletions);
		notifyChanged();
		return this;
	}
	public int getCommitsAsAuthor() {
		return parseInteger(dbObject.get("commitsAsAuthor")+"", 0);
	}
	
	public Measureable setCommitsAsAuthor(int commitsAsAuthor) {
		dbObject.put("commitsAsAuthor", commitsAsAuthor);
		notifyChanged();
		return this;
	}
	public int getCommitsAsCommitter() {
		return parseInteger(dbObject.get("commitsAsCommitter")+"", 0);
	}
	
	public Measureable setCommitsAsCommitter(int commitsAsCommitter) {
		dbObject.put("commitsAsCommitter", commitsAsCommitter);
		notifyChanged();
		return this;
	}
	public int getCommitTotalFiles() {
		return parseInteger(dbObject.get("commitTotalFiles")+"", 0);
	}
	
	public Measureable setCommitTotalFiles(int commitTotalFiles) {
		dbObject.put("commitTotalFiles", commitTotalFiles);
		notifyChanged();
		return this;
	}
	public double getAverageFilesPerCommit() {
		return parseDouble(dbObject.get("averageFilesPerCommit")+"", 0.0d);
	}
	
	public Measureable setAverageFilesPerCommit(double averageFilesPerCommit) {
		dbObject.put("averageFilesPerCommit", averageFilesPerCommit);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public Measureable setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	public int getNumberOfCommitComments() {
		return parseInteger(dbObject.get("numberOfCommitComments")+"", 0);
	}
	
	public Measureable setNumberOfCommitComments(int numberOfCommitComments) {
		dbObject.put("numberOfCommitComments", numberOfCommitComments);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssueComments() {
		return parseInteger(dbObject.get("numberOfIssueComments")+"", 0);
	}
	
	public Measureable setNumberOfIssueComments(int numberOfIssueComments) {
		dbObject.put("numberOfIssueComments", numberOfIssueComments);
		notifyChanged();
		return this;
	}
	public int getNumberOfPullRequests() {
		return parseInteger(dbObject.get("numberOfPullRequests")+"", 0);
	}
	
	public Measureable setNumberOfPullRequests(int numberOfPullRequests) {
		dbObject.put("numberOfPullRequests", numberOfPullRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfPullRequestComments() {
		return parseInteger(dbObject.get("numberOfPullRequestComments")+"", 0);
	}
	
	public Measureable setNumberOfPullRequestComments(int numberOfPullRequestComments) {
		dbObject.put("numberOfPullRequestComments", numberOfPullRequestComments);
		notifyChanged();
		return this;
	}
	
	public List<String> getCommitTimes() {
		if (commitTimes == null) {
			commitTimes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("commitTimes"));
		}
		return commitTimes;
	}
	public List<String> getCommitCommentTimes() {
		if (commitCommentTimes == null) {
			commitCommentTimes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("commitCommentTimes"));
		}
		return commitCommentTimes;
	}
	public List<String> getPullRequestTimes() {
		if (pullRequestTimes == null) {
			pullRequestTimes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("pullRequestTimes"));
		}
		return pullRequestTimes;
	}
	public List<String> getPullRequestCommentTimes() {
		if (pullRequestCommentTimes == null) {
			pullRequestCommentTimes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("pullRequestCommentTimes"));
		}
		return pullRequestCommentTimes;
	}
	public List<String> getIssueTimes() {
		if (issueTimes == null) {
			issueTimes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("issueTimes"));
		}
		return issueTimes;
	}
	public List<String> getIssueCommentTimes() {
		if (issueCommentTimes == null) {
			issueCommentTimes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("issueCommentTimes"));
		}
		return issueCommentTimes;
	}
	
	public List<IssueEvent> getIssueEvents() {
		if (issueEvents == null) {
			issueEvents = new PongoList<IssueEvent>(this, "issueEvents", true);
		}
		return issueEvents;
	}
	public List<Artefact> getArtefacts() {
		if (artefacts == null) {
			artefacts = new PongoList<Artefact>(this, "artefacts", true);
		}
		return artefacts;
	}
	
	
}