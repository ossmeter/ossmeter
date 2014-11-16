package org.ossmeter.metricprovider.trans.bugs.activeusers;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugs.activeusers.model.BugData;
import org.ossmeter.metricprovider.trans.bugs.activeusers.model.BugsActiveUsersTransMetric;
import org.ossmeter.metricprovider.trans.bugs.activeusers.model.User;
import org.ossmeter.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerComments;
import org.ossmeter.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.communicationchannel.nntp.NntpUtil;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class ActiveUsersTransMetricProvider implements ITransientMetricProvider<BugsActiveUsersTransMetric>{

	protected final int STEP = 15;
	
	protected PlatformBugTrackingSystemManager bugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ActiveUsersTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}


	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(RequestReplyClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.bugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsActiveUsersTransMetric adapt(DB db) {
		return new BugsActiveUsersTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsActiveUsersTransMetric db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		RequestReplyClassificationTransMetric usedClassifier = 
				((RequestReplyClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for ( BugTrackingSystemDelta bugTrackingSystemDelta: delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			BugData bugData = db.getBugs().findOneByBugTrackerId(bugTrackingSystem.getOSSMeterId());
			if (bugData == null) {
				bugData = new BugData();
				bugData.setBugTrackerId(bugTrackingSystem.getOSSMeterId());
				bugData.setPreviousUsers(0);
				bugData.setDays(1);
				db.getBugs().add(bugData);
			} else {
				bugData.setPreviousUsers(bugData.getUsers());
				bugData.setDays(bugData.getDays()+1);
			}
			
			List<BugTrackingSystemComment> comments = bugTrackingSystemDelta.getComments();
			for (BugTrackingSystemComment comment: comments) {
				Iterable<User> usersIt = db.getUsers().
						find(User.BUGTRACKERID.eq(bugTrackingSystem.getOSSMeterId()), 
								User.USERID.eq(comment.getCreator()));
				User user = null;
				for (User u:  usersIt) {
					user = u;
				}
				if (user == null) {
					user = new User();
					user.setBugTrackerId(bugTrackingSystem.getOSSMeterId());
					user.setUserId(comment.getCreator());
					user.setLastActivityDate(comment.getCreationTime().toString());
					user.setComments(1);
					String requestReplyClass = getRequestReplyClass(usedClassifier, bugTrackingSystem, comment);
					if (requestReplyClass.equals("Reply"))
						user.setReplies(1);
					else if (requestReplyClass.equals("Request"))
						user.setRequests(1);
					db.getUsers().add(user);
				} else {
					java.util.Date javaDate = NntpUtil.parseDate(user.getLastActivityDate());
					Date userDate = new Date(javaDate);
					Date articleDate = new Date(comment.getCreationTime());
					if (articleDate.compareTo(userDate)==1)
						user.setLastActivityDate(comment.getCreationTime().toString());
					user.setComments(user.getComments() + 1 );
					String requestReplyClass = getRequestReplyClass(usedClassifier, bugTrackingSystem, comment);
					if (requestReplyClass.equals("Reply"))
						user.setReplies(user.getReplies()+1);
					else if (requestReplyClass.equals("Request"))
						user.setRequests(user.getRequests()+1);
				}
				db.sync();
			}
			
			Iterable<User> usersIt = db.getUsers().findByBugTrackerId(bugTrackingSystem.getOSSMeterId());
			int users = 0,
				activeUsers = 0,
				inactiveUsers = 0;
			for (User user:  usersIt) {
				Boolean active = true;
				users++;
				java.util.Date javaDate = NntpUtil.parseDate(user.getLastActivityDate());
				if (javaDate!=null) {
					Date date = new Date(javaDate);
					if (projectDelta.getDate().compareTo(date.addDays(STEP)) >0) {
						active=false;
					}
				} else
					active=false;
				if (active) activeUsers++;
				else inactiveUsers++;
			}
			bugData.setActiveUsers(activeUsers);
			bugData.setInactiveUsers(inactiveUsers);
			bugData.setUsers(users);
			db.sync();
		}
	}

	private String getRequestReplyClass(RequestReplyClassificationTransMetric usedClassifier, 
			BugTrackingSystem bugTracker, BugTrackingSystemComment comment) {
		Iterable<BugTrackerComments> bugTrackerCommentsIt = usedClassifier.getBugTrackerComments().
				find(BugTrackerComments.BUGTRACKERID.eq(bugTracker.getOSSMeterId()), 
						BugTrackerComments.COMMENTID.eq(comment.getCommentId()));
		BugTrackerComments bugTrackerComments = null;
		for (BugTrackerComments bts:  bugTrackerCommentsIt) {
			bugTrackerComments = bts;
		}
		if (bugTrackerComments == null) {
			System.err.println("Active users metric -\t" + 
					"there is no classification for comment: " + comment.getCommentId() +
					"\t of bug tracker: " + bugTracker.getOSSMeterId());
//			System.exit(-1);
		} else
			return bugTrackerComments.getClassificationResult();
		return "";
	}

	@Override
	public String getShortIdentifier() {
		return "activeusers";
	}

	@Override
	public String getFriendlyName() {
		return "Active Users";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps track of the users that submitted news comments " +
				"in the last 15 days.";
	}

}
