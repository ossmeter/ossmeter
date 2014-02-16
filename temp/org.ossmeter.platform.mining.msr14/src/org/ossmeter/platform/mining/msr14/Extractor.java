package org.ossmeter.platform.mining.msr14;

import java.util.Iterator;

import org.ossmeter.platform.mining.msr14.model.Artefact;
import org.ossmeter.platform.mining.msr14.model.Biodiversity;
import org.ossmeter.platform.mining.msr14.model.Commits;
import org.ossmeter.platform.mining.msr14.model.IssueEvent;
import org.ossmeter.platform.mining.msr14.model.IssueEventKind;
import org.ossmeter.platform.mining.msr14.model.Project;
import org.ossmeter.platform.mining.msr14.model.ProjectMembership;
import org.ossmeter.platform.mining.msr14.model.User;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.ServerAddress;

public class Extractor {
	public static void main(String[] args) throws Exception {
		
		Mongo msrMongo = new Mongo(new ServerAddress("localhost", 1234)); // GitHub challenge data
		Mongo bioMongo = new Mongo(new ServerAddress("localhost", 12345));// Extracted data
		
		// Create indexes
		Biodiversity bio = new Biodiversity(bioMongo.getDB("biodiversity"));
		bioMongo.getDB("biodiversity").getCollection("users").ensureIndex(new BasicDBObject("login",1));

		BasicDBObject index = new BasicDBObject();
		index.put("name",1);
		index.put("ownerName",1);
		bioMongo.getDB("biodiversity").getCollection("projects").ensureIndex(index);
		
		index = new BasicDBObject();
		index.put("projectName",1);
		index.put("projectOwner",1);
		bioMongo.getDB("biodiversity").getCollection("projectMemberships").ensureIndex(index);
		
		index = new BasicDBObject();
		index.put("projectName",1);
		index.put("userName",1);
		bioMongo.getDB("biodiversity").getCollection("projectMemberships").ensureIndex(index);
		
		bioMongo.getDB("biodiversity").getCollection("projectMemberships").ensureIndex(new BasicDBObject("userName",1));
		
		DB msrDb = msrMongo.getDB("msr14");
		
//		#1 User extraction
		System.out.println("Extracting users...");
		DBCursor cursor = msrDb.getCollection("users").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		Iterator<DBObject> it = cursor.iterator();


		int count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			User user = new User();
			user.setGhId(obj.getString("id"));
			user.setLogin(obj.getString("login"));
			user.setLocation(obj.getString("location"));
			user.setPublicRepos(obj.getInt("public_repos"));
			user.setJoinedDate(obj.getString("created_at"));
			
			bio.getUsers().add(user);
			
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		#1.2 Project extraction
		System.out.println("Extracting projects...");
		cursor = msrDb.getCollection("repos").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();

		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			Project project = new Project();
			project.setName(obj.getString("name"));
			project.setGhId(obj.getString("id"));
			project.setCreatedAt(obj.getString("created_at"));
			project.setSize(obj.getInt("size", 0));
			project.setWatchersCount(obj.getInt("watchers",0));
			project.setWatchersCount2(obj.getInt("watchers_count",0));
			project.setLanguage(obj.getString("language"));
			project.setForks(obj.getInt("forks", 0));
			project.setForksCount(obj.getInt("forks_count", 0));
			project.setOpenIssues(obj.getInt("open_issues",0));
			project.setOpenIssuesCount(obj.getInt("open_issues_count",0));
			project.setOpenIssues(obj.getInt("open_issues",0));
			project.setNetworkCount(obj.getInt("network_count", 0));
			
			BasicDBObject ownerObj = (BasicDBObject) obj.get("owner");
			User owner = null;
			if (ownerObj != null) {
				owner = bio.getUsers().findOne(User.LOGIN.eq(ownerObj.getString("login")));
				if (owner !=null) {
					project.setOwner(owner);
					project.setOwnerName(owner.getLogin());
				}
			}
			bio.getProjects().add(project);
			
			if (owner != null) { // This comes here as to reference the project, we need to have added to the project list first
				ProjectMembership pm = getProjectMembership(bio, owner, project);
				pm.setOwner(true);
			}
			
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		#2 Follower/following extraction
		System.out.println("Extracting followers...");
		cursor = msrDb.getCollection("followers").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			String followerLogin = obj.getString("login");
			String followedLogin = obj.getString("follows");
			
			User follower = bio.getUsers().findOne(User.LOGIN.eq(followerLogin));
			User followed = bio.getUsers().findOne(User.LOGIN.eq(followedLogin));
			
			if (follower != null && followed != null) {
				follower.getFollowing().add(followed);
				followed.getFollowers().add(follower);
			} else{
				System.err.println("Follower or followed is null. Follower: " +follower + ". followed: " + followed);
			}
			if (follower != null) follower.setFollowingCount(follower.getFollowingCount()+1);
			if (followed != null) followed.setFollowerCount(followed.getFollowerCount()+1);
			
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		#3 Commits
		System.out.println("Extracting commits...");
		cursor = msrDb.getCollection("commits").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			// Author and committer
			BasicDBObject commitAuthor = (BasicDBObject)obj.get("author");
			BasicDBObject commitCommitter= (BasicDBObject)obj.get("committer");
			
			String authorLogin = "";
			if (commitAuthor != null) authorLogin = commitAuthor.getString("login");
			String committerLogin = "";
			if (commitCommitter!= null) committerLogin = commitCommitter.getString("login");
			
			// Stats
			BasicDBObject stats = (BasicDBObject) obj.get("stats");
			if (stats == null) stats = new BasicDBObject(); // Create a new one so we can get zeroed values
			int total = stats.getInt("total", 0);
			int additions = stats.getInt("additions", 0);
			int deletions = stats.getInt("deletions", 0);
			
			String commitDate = ((BasicDBObject)((BasicDBObject)obj.get("commit")).get("author")).getString("date");
			
			User author = bio.getUsers().findOne(User.LOGIN.eq(authorLogin));
			User committer = bio.getUsers().findOne(User.LOGIN.eq(committerLogin));
			
			if (author != null) {
				if (author.getCommits() ==null) author.setCommits(new Commits());
				author.getCommits().setCount(author.getCommits().getCount()+1);
				author.getCommits().setTotalChanges(author.getCommits().getTotalChanges()+total);
				author.getCommits().setAdditions(author.getCommits().getAdditions()+additions);
				author.getCommits().setDeletions(author.getCommits().getDeletions()+deletions);
				author.getCommits().setAsAuthor(author.getCommits().getAsAuthor()+1);
				author.getCommits().getCommitTimes().add(commitDate);
			}
			if (committer != null) {
				if (committer.getCommits() ==null) committer.setCommits(new Commits());
				committer.getCommits().setCount(committer.getCommits().getCount()+1);
				committer.getCommits().setTotalChanges(committer.getCommits().getTotalChanges()+total);
				committer.getCommits().setAdditions(committer.getCommits().getAdditions()+additions);
				committer.getCommits().setDeletions(committer.getCommits().getDeletions()+deletions);
				committer.getCommits().setAsCommitter(committer.getCommits().getAsCommitter()+1);
				committer.getCommits().getCommitTimes().add(commitDate);
			}
			
			ProjectMembership authorPm = null;
			ProjectMembership committerPm = null;
			
			// Only a very small number of commit comments actually reference the repo
			// Instead we're going to have to strip the string 
			String[] url = convertUrlIntoProjectNameAndOwner(obj.getString("url"));
			Project project = null;
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(url[1]), Project.OWNERNAME.eq(url[0])).iterator();
			if (repoIt.hasNext()) {
				project = repoIt.next();
				if (project != null) {
					project.setNumberOfCommits(project.getNumberOfCommits()+1);
					
					if (author != null) {
						authorPm = getProjectMembership(bio, author, project);
						if (authorPm.getCommits() ==null) {
							authorPm.setCommits(new Commits());
							bio.sync();
						}
//						System.err.println("Just created an author PM for " + author + " and " + project);
						authorPm.getCommits().setCount(authorPm.getCommits().getCount()+1);
						authorPm.getCommits().setTotalChanges(authorPm.getCommits().getTotalChanges()+total);
						authorPm.getCommits().setAdditions(authorPm.getCommits().getAdditions()+1);
						authorPm.getCommits().setDeletions(authorPm.getCommits().getDeletions()+1);
						authorPm.getCommits().setAsAuthor(authorPm.getCommits().getAsAuthor()+1);
						
						// Avoid duplicating information
						if (committer != null && author.getLogin().equals(committer.getLogin())) {
							authorPm.getCommits().setAsCommitter(authorPm.getCommits().getAsCommitter()+1);
						}
						
						authorPm.getCommits().getCommitTimes().add(commitDate);
						
						if (authorPm.getCommits().getCount()>1) {
							System.out.println("More than one commit!!! " + authorPm.getCommits().getCount() + " (" + project.getName() + " " + author.getLogin());
						}
					}
					if (committer != null && author != null && !author.getLogin().equals(committer.getLogin())) {
						committerPm = getProjectMembership(bio, committer, project);
						if (committerPm.getCommits() ==null) committerPm.setCommits(new Commits());
						committerPm.getCommits().setCount(committerPm.getCommits().getCount()+1);
						committerPm.getCommits().setTotalChanges(committerPm.getCommits().getTotalChanges()+total);
						committerPm.getCommits().setAdditions(committerPm.getCommits().getAdditions()+1);
						committerPm.getCommits().setDeletions(committerPm.getCommits().getDeletions()+1);
						committerPm.getCommits().setAsCommitter(committerPm.getCommits().getAsCommitter()+1);
						
						committerPm.getCommits().getCommitTimes().add(commitDate);
					}
				} 
			}
			else {
				System.err.println("Didn't find project:" + url[0] + ":"+url[1] + ", prestrip: " + obj.getString("url"));
			}
			bio.getProjectMemberships().sync();
			bio.sync();

			// Files
			BasicDBList files = (BasicDBList) obj.get("files");
//			if (files != null) {
//				for (Object f : files) {
//					BasicDBObject file = (BasicDBObject)f;
//					
//					String filename = file.getString("filename");
//					if (filename.lastIndexOf(".") != -1) { // If it has an extension, we want that. If not, use the entire filename
//						filename = filename.substring(filename.lastIndexOf("."));
//						filename = filename.toLowerCase(); // Ensure consistency
//					}
//			// FIXME: Should strip any /'s if there is no '.' - i.e. just the last one
//					
//					if (author != null) addArtefact(author, filename);
//					if (committer != null) addArtefact(committer, filename);
////					if (project != null) addArtefact(project, filename);
//				}
//			}
			
			if (author != null && files !=null) {
				author.getCommits().setTotalFiles(author.getCommits().getTotalFiles()+files.size());
				author.getCommits().setAverageFilesPerCommit(author.getCommits().getTotalFiles()/author.getCommits().getCount());
			}
			if (committer != null && files !=null) {
				committer.getCommits().setTotalFiles(committer.getCommits().getTotalFiles()+files.size());
				committer.getCommits().setAverageFilesPerCommit(committer.getCommits().getTotalFiles()/committer.getCommits().getCount());
			}
			if (authorPm !=null && files != null) {
//				ProjectMembership authorPm = getProjectMembership(bio, author, project);
//				if (authorPm.getCommits() ==null) authorPm.setCommits(new Commits()); //FIXME: this shouldn't be needed due to above
				authorPm.getCommits().setTotalFiles(authorPm.getCommits().getTotalChanges()+files.size());
				authorPm.getCommits().setAverageFilesPerCommit(authorPm.getCommits().getTotalFiles()/authorPm.getCommits().getCount());
			}
			if (committerPm != null && files != null) {
//				ProjectMembership committerPm = getProjectMembership(bio, committer, project);
//				if (committerPm.getCommits() ==null) committerPm.setCommits(new Commits());//FIXME: this shouldn't be needed due to above
				committerPm.getCommits().setTotalFiles(committerPm.getCommits().getTotalChanges()+files.size());
				committerPm.getCommits().setAverageFilesPerCommit(committerPm.getCommits().getTotalFiles()/committerPm.getCommits().getCount());
			}
			bio.getProjectMemberships().sync();
			bio.sync();
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		#4 Commit comments
		System.out.println("Extracting commit comments...");
		cursor = msrDb.getCollection("commit_comments").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
		
			String username = getUserLoginName(bio, "user", "login", obj);
			User user = bio.getUsers().findOne(User.LOGIN.eq(username));
			if (user == null) {
				System.err.println("Found commit comment with unrecognised user: " + username);
				continue;
			}
			
			user.setTotalNumberOfCommitComments(user.getTotalNumberOfCommitComments()+1);
			
			// Only a very small number of commit comments actually reference the repo
			// Instead we're going to have to strip the string 
			String[] url = convertUrlIntoProjectNameAndOwner(obj.getString("url"));
			
//			System.out.println("Querying project " + url[1] + " and owner " + url[0]);
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(url[1]), Project.OWNERNAME.eq(url[0])).iterator();
//			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) {
					project.setNumberOfCommitComments(project.getNumberOfCommitComments()+1);
					ProjectMembership pm = getProjectMembership(bio, user, project);
					pm.setNumberOfCommitComments(pm.getNumberOfCommitComments()+1);
				}
//			}
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		cursor.close();
		bio.sync();
		System.out.println();
		
//		#5 Pull requests
		System.out.println("Extracting pull requests...");
		cursor = msrDb.getCollection("pull_requests").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			String username = getUserLoginName(bio, "user", "login", obj);
			User user = bio.getUsers().findOne(User.LOGIN.eq(username));
			if (user == null) {
				System.err.println("Found pull request with unrecognised user:" + username);
				continue;
			}
			
			user.setTotalNumberOfPullRequests(user.getTotalNumberOfPullRequests()+1);

			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) {
					project.setNumberOfPullRequests(project.getNumberOfPullRequests()+1);
					ProjectMembership pm = getProjectMembership(bio, user, project);
					pm.setNumberOfPullRequests(pm.getNumberOfPullRequests()+1);
				}
			} else {
				System.err.println("Didn't find project:" + obj.getString("repo") + ":"+obj.getString("owner"));
			}
			
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		#6 Pull request comments
		System.out.println("Extracting pull request comments...");
		cursor = msrDb.getCollection("pull_request_comments").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
		
			String username = getUserLoginName(bio, "user", "login", obj);
			User user = bio.getUsers().findOne(User.LOGIN.eq(username));
			if (user == null) {
//				System.err.println("Found pull request comment with unrecognised user:" + username);
				continue;
			}
			
			user.setTotalNumberOfPullRequestComments(user.getTotalNumberOfPullRequestComments()+1);
			
			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) {
					project.setNumberOfPullRequestComments(project.getNumberOfPullRequestComments()+1);
					ProjectMembership pm = getProjectMembership(bio, user, project);
					pm.setNumberOfPullRequestComments(pm.getNumberOfPullRequestComments()+1);
				}
			}
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
		
//		#7 Issues
		System.out.println("Extracting issues...");
		cursor = msrDb.getCollection("issues").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
		
			String username = getUserLoginName(bio, "user", "login", obj);
			User user = bio.getUsers().findOne(User.LOGIN.eq(username));
			if (user == null) {
//				System.err.println("Found issue with unrecognised user:" + username);
				continue;
			}
			
			user.setTotalNumberOfIssues(user.getTotalNumberOfIssues()+1);
			
			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) {
					project.setNumberOfIssues(project.getNumberOfIssues()+1);
					ProjectMembership pm = getProjectMembership(bio, user, project);
					pm.setNumberOfIssues(pm.getNumberOfIssues()+1);
				}
			}
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		#8 Issue comments
		System.out.println("Extracting issue comments...");
		cursor = msrDb.getCollection("issue_comments").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
		
			String username = getUserLoginName(bio, "user", "login", obj);
			User user = bio.getUsers().findOne(User.LOGIN.eq(username));
			if (user == null) {
//				System.err.println("Found issue comment with unrecognised user:" + username);
				continue;
			}
			
			user.setTotalNumberOfIssueComments(user.getTotalNumberOfIssueComments()+1);
			
			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) {
					project.setNumberOfIssueComments(project.getNumberOfIssueComments()+1);
					ProjectMembership pm = getProjectMembership(bio, user, project);
					pm.setNumberOfIssueComments(pm.getNumberOfIssueComments()+1);
				}
			}
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		#9 Issue events
		System.out.println("Extracting issue events...");
		cursor = msrDb.getCollection("issue_events").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
		
			String username = getUserLoginName(bio, "actor", "login", obj);
			User user = bio.getUsers().findOne(User.LOGIN.eq(username));
			if (user == null) {
//				System.err.println("Found issue event with unrecognised user:" + username);
				continue;
			}
			
			String eventKind = obj.getString("event");
			IssueEventKind kind = null; //FIXME
			
			switch (eventKind) {
				case "closed": kind = IssueEventKind.CLOSED; break;
				case "assigned": kind = IssueEventKind.ASSIGNED; break;
				case "mentioned": kind = IssueEventKind.MENTIONED; break;
				case "merged": kind = IssueEventKind.MERGED; break;
				case "referenced": kind = IssueEventKind.REFERENCED; break;
				case "reopened": kind = IssueEventKind.REOPENED; break;
				case "subscribed": kind = IssueEventKind.SUBSCRIBED; break;
				case "head_ref_deleted" : kind = IssueEventKind.HEAD_REF_DELETED; break;
				case "head_ref_restored" : kind = IssueEventKind.HEAD_REF_RESTORED; break;
				case "head_ref_cleaned" : kind = IssueEventKind.HEAD_REF_CLEANED; break;
				case "unsubscribed" : kind = IssueEventKind.UNSUBSCRIBED; break;
				default:
					System.err.println("Unrecognised issue event kind: " + eventKind);
			}
			if (kind == null) continue;

			boolean eventKindFound = false;
			for (IssueEvent ie : user.getTotalIssueEvents()) {
				if (ie.getEventKind().equals(kind)) {
					ie.setCount(ie.getCount()+1);
					eventKindFound = true;
					break;
				}
			}
			if (!eventKindFound) {
				IssueEvent ie = new IssueEvent();
				ie.setEventKind(kind);
				ie.setCount(1);
				user.getTotalIssueEvents().add(ie);
			}
			
			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
			
				eventKindFound = false;
				for (IssueEvent ie : project.getIssueEvents()) {
					if (ie.getEventKind().equals(kind)) {
						ie.setCount(ie.getCount()+1);
						eventKindFound = true;
						break;
					}
				}
				if (!eventKindFound) {
					IssueEvent ie = new IssueEvent();
					ie.setEventKind(kind);
					ie.setCount(1);
					project.getIssueEvents().add(ie);
				}
				
				ProjectMembership pm = getProjectMembership(bio, user, project);
				eventKindFound = false;
				for (IssueEvent ie : pm.getIssueEvents()) {
					if (ie.getEventKind().equals(kind)) {
						ie.setCount(ie.getCount()+1);
						eventKindFound = true;
						break;
					}
				}
				if (!eventKindFound) {
					IssueEvent ie = new IssueEvent();
					ie.setEventKind(kind);
					ie.setCount(1);
					pm.getIssueEvents().add(ie);
				}
			}
			
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
		System.out.println();
		
//		Watchers
		System.out.println("Extracting watchers...");
		cursor = msrDb.getCollection("watchers").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		it = cursor.iterator();
		
		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			User user = bio.getUsers().findOne(User.LOGIN.eq(obj.getString("login")));
			if (user == null) continue;
			
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null && !project.getWatchers().contains(user)) project.getWatchers().add(user);
				if (!user.getWatches().contains(project)) user.getWatches().add(project);
			}
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
		bio.sync();
	}
	
	protected static ProjectMembership getProjectMembership(Biodiversity bio, User user, Project project) {
		
		Iterator<ProjectMembership> it = bio.getProjectMemberships().find(ProjectMembership.USERNAME.eq(user.getLogin()), ProjectMembership.PROJECTNAME.eq(project.getName())).iterator();
		if (it.hasNext()) {
			return it.next();
		}

//		for (ProjectMembership pm : user.getProjects()) {//bio.getUsers().findOne(User.LOGIN.eq(user.getLogin())).getProjects()){//
//			if (pm ==null){
//				System.err.println("user.getProjects() returned a null entry again. Hmmm..." + user + " " + project);
//				break;
//			}
//			if (pm.getProjectOwner().equals(project.getOwnerName()) && pm.getProjectName().equals(project.getName())) {
////				System.err.println("FOUND AN EXISTING PM");
//				return pm;
//			}
//		}

		ProjectMembership pm = new ProjectMembership();
		pm.setProject(project);
		pm.setProjectName(project.getName());
		pm.setProjectOwner(project.getOwnerName());
		pm.setUser(user);
		pm.setUserName(user.getLogin());
		bio.getProjectMemberships().add(pm);
		user.getProjects().add(pm);
		return pm;
	}
	
	/**
	 * Some 'user' field in commit_comments are Strings. Some are objects. Seriously.
	 * @param bio
	 * @param obj
	 * @return
	 */
	protected static String getUserLoginName(Biodiversity bio, String userField, String loginField, BasicDBObject obj) {
		String username = null;
		if (obj.get(userField) == null) return null;
		if (obj.get(userField) instanceof String) {
			username = obj.getString(userField);
		} else {
			username = ((BasicDBObject)obj.get(userField)).getString(loginField);
		}
		return username;
	}
	
	protected static String[] convertUrlIntoProjectNameAndOwner(String url) {
		url = url.replaceAll("https://api.github.com/repos/", "");
		String owner = url.substring(0, url.indexOf("/"));
		url = url.substring(url.indexOf("/")+1);
		String repo = url.substring(0, url.indexOf("/"));
		return new String[]{owner,repo};
	}
	
	protected static void addArtefact(User user, String extension) {
		boolean artefactFound = false;
		for (Artefact a : user.getArtefacts()) {
			if (a.getExtension().equals(extension)) {
				a.setCount(a.getCount()+1);
				artefactFound = true;
				break;
			}
		}
		if (!artefactFound) {
			Artefact a = new Artefact();
			a.setExtension(extension);
			a.setCount(1);
			user.getArtefacts().add(a);
		}
	}
	protected static void addArtefact(Project project, String extension) {
		boolean artefactFound = false;
		for (Artefact a : project.getArtefacts()) {
			if (a.getExtension().equals(extension)) {
				a.setCount(a.getCount()+1);
				artefactFound = true;
				break;
			}
		}
		if (!artefactFound) {
			Artefact a = new Artefact();
			a.setExtension(extension);
			a.setCount(1);
			project.getArtefacts().add(a);
		}
	}
}
