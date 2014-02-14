package org.ossmeter.platform.mining.msr14;

import java.util.Iterator;

import org.ossmeter.platform.mining.msr14.model.Artefact;
import org.ossmeter.platform.mining.msr14.model.Biodiversity;
import org.ossmeter.platform.mining.msr14.model.Commit;
import org.ossmeter.platform.mining.msr14.model.Countable;
import org.ossmeter.platform.mining.msr14.model.IssueEvent;
import org.ossmeter.platform.mining.msr14.model.IssueEventKind;
import org.ossmeter.platform.mining.msr14.model.Project;
import org.ossmeter.platform.mining.msr14.model.User;

import com.googlecode.pongo.runtime.querying.Logical;
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
		
		Mongo msrMongo = new Mongo(new ServerAddress("localhost", 1234));
		Mongo bioMongo = new Mongo(new ServerAddress("localhost", 12345));
		
		Biodiversity bio = new Biodiversity(bioMongo.getDB("biodiversity"));

		DB msrDb = msrMongo.getDB("msr14");
		
//		#1 User extraction
		System.out.println("Extracting users...");
		DBCursor cursor = msrDb.getCollection("users").find();
		cursor.addOption(Bytes.QUERYOPTION_NOTIMEOUT);
		Iterator<DBObject> it = cursor.iterator();

		bioMongo.getDB("biodiversity").getCollection("users").ensureIndex(new BasicDBObject("login",1));

		int count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			User user = new User();
			user.setId(obj.getString("id"));
			user.setLogin(obj.getString("login"));
			user.setLocation(obj.getString("location"));
			user.setPublicRepos(obj.getInt("public_repos"));
			
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

		BasicDBObject index = new BasicDBObject();
		index.put("name",1);
		index.put("ownerName",1);
		
		bioMongo.getDB("biodiversity").getCollection("projects").ensureIndex(index);

		count = 0;
		while(it.hasNext()){
			BasicDBObject obj = (BasicDBObject) it.next();
			
			Project project = new Project();
			project.setName(obj.getString("name"));
			project.setId(obj.getString("id"));
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
			
			if (owner != null) owner.getOwns().add(project);
			
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
			
			User author = bio.getUsers().findOne(User.LOGIN.eq(authorLogin));
			User committer = bio.getUsers().findOne(User.LOGIN.eq(committerLogin));
			if (author != null) {
				if (author.getCommitAuthors() == null) author.setCommitAuthors(new Commit());
				author.getCommitAuthors().setNumber(author.getCommitAuthors().getNumber()+1);
				author.getCommitAuthors().setTotal(author.getCommitAuthors().getTotal() + total);
				author.getCommitAuthors().setAdditions(author.getCommitAuthors().getAdditions()+additions);
				author.getCommitAuthors().setDeletions(author.getCommitAuthors().getDeletions()+deletions);
			} else {
				System.err.println("Found commit with unknown author: " + authorLogin);
			}
			if (committer != null) {
				if (committer.getCommitCommitter() == null) committer.setCommitCommitter(new Commit());
				committer.getCommitCommitter().setNumber(committer.getCommitCommitter().getNumber()+1);
				committer.getCommitCommitter().setTotal(committer.getCommitCommitter().getTotal() + total);
				committer.getCommitCommitter().setAdditions(committer.getCommitCommitter().getAdditions()+additions);
				committer.getCommitCommitter().setDeletions(committer.getCommitCommitter().getDeletions()+deletions);
			} else {
				System.err.println("Found commit with unknown committer: " + committerLogin);
			}
			// Files
			BasicDBList files = (BasicDBList) obj.get("files");
			if (files != null) {
				for (Object f : files) {
					BasicDBObject file = (BasicDBObject)f;
					
					String filename = file.getString("filename");
					if (filename.lastIndexOf(".") != -1) { // If it has an extension, we want that. If not, use the entire filename
						filename = filename.substring(filename.lastIndexOf("."));
						filename = filename.toLowerCase(); // Ensure consistency
					}
					
					if (author != null) addArtefact(author, filename);
					if (committer != null) addArtefact(committer, filename);
				}
			}
			
			if (author != null && files !=null) {
				if (author.getCommitAuthors()==null) author.setCommitAuthors(new Commit());
				author.getCommitAuthors().setTotalFiles(author.getCommitAuthors().getTotalFiles()+files.size());
				author.getCommitAuthors().setAverageFilesPerCommit(author.getCommitAuthors().getTotalFiles()/author.getCommitAuthors().getNumber());
			}
			if (committer != null && files !=null) {
				if (committer.getCommitCommitter()==null) committer.setCommitCommitter(new Commit());
				committer.getCommitCommitter().setTotalFiles(committer.getCommitCommitter().getTotalFiles()+files.size());
				committer.getCommitCommitter().setAverageFilesPerCommit(committer.getCommitCommitter().getTotalFiles()/committer.getCommitCommitter().getNumber());
			}
			
			// Only a very small number of commit comments actually reference the repo
			// Instead we're going to have to strip the string 
			String[] url = convertUrlIntoProjectNameAndOwner(obj.getString("url"));
			
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(url[1]), Project.OWNERNAME.eq(url[0])).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) {
					project.setNumberOfCommits(project.getNumberOfCommits()+1);
					if (author != null && !author.getCommitsAsAuthor().contains(project)) author.getCommitsAsAuthor().add(project);
					if (committer != null && !committer.getCommitsAsCommitter().contains(project)) committer.getCommitsAsCommitter().add(project);
				} 
			}
			else {
				System.err.println("Didn't find project:" + url[0] + ":"+url[1] + ", prestrip: " + obj.getString("url"));
			}
			
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
			
			if (user.getCommitComments() == null) user.setCommitComments(new Countable());
			user.getCommitComments().setNumber(user.getCommitComments().getNumber()+1);
			user.getCommitComments().setFrequency(0); //FIXME needs implementings
			
			// Only a very small number of commit comments actually reference the repo
			// Instead we're going to have to strip the string 
			String[] url = convertUrlIntoProjectNameAndOwner(obj.getString("url"));
			
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(url[1]), Project.OWNERNAME.eq(url[0])).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) project.setNumberOfCommitComments(project.getNumberOfCommitComments()+1);
				if (project != null && !user.getCommentsOnCommits().contains(project)) user.getCommentsOnCommits().add(project);
			}
			count++;
			if (count % 1000 == 0) {
				System.out.print(count + ", ");
				bio.sync();
			}
		}
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
			
			if (user.getPullRequests() == null) user.setPullRequests(new Countable());
			user.getPullRequests().setNumber(user.getPullRequests().getNumber()+1);
			user.getPullRequests().setFrequency(0); //FIXME needs implementings

			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) project.setNumberOfPullRequests(project.getNumberOfPullRequests()+1);
				if (!user.getSubmitsPullRequests().contains(project)) user.getSubmitsPullRequests().add(project);
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
				System.err.println("Found pull request comment with unrecognised user:" + username);
				continue;
			}
			
			if (user.getPullRequestComments() == null) user.setPullRequestComments(new Countable());
			user.getPullRequestComments().setNumber(user.getPullRequestComments().getNumber()+1);
			user.getPullRequestComments().setFrequency(0); //FIXME needs implementings
			
			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) project.setNumberOfPullRequestComments(project.getNumberOfPullRequestComments()+1);
				if (!user.getCommentsOnPullRequests().contains(project)) user.getCommentsOnPullRequests().add(project);
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
				System.err.println("Found issue with unrecognised user:" + username);
				continue;
			}
			
			if (user.getIssues() == null) user.setIssues(new Countable());
			user.getIssues().setNumber(user.getIssues().getNumber()+1);
			user.getIssues().setFrequency(0); //FIXME needs implementings
			
			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) project.setNumberOfIssues(project.getNumberOfIssues()+1);
				if (!user.getSubmitsIssues().contains(project)) user.getSubmitsIssues().add(project);
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
				System.err.println("Found issue comment with unrecognised user:" + username);
				continue;
			}
			
			if (user.getIssueComments() == null) user.setIssueComments(new Countable());
			user.getIssueComments().setNumber(user.getIssueComments().getNumber()+1);
			user.getIssueComments().setFrequency(0); //FIXME needs implementings
			
			// Project
			Iterator<Project> repoIt = bio.getProjects().find(Project.NAME.eq(obj.getString("repo")), Project.OWNERNAME.eq(obj.getString("owner"))).iterator();
			if (repoIt.hasNext()) {
				Project project = repoIt.next();
				if (project != null) project.setNumberOfIssueComments(project.getNumberOfIssueComments()+1);
				if (!user.getCommentsOnIssues().contains(project)) user.getCommentsOnIssues().add(project);
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
				System.err.println("Found issue event with unrecognised user:" + username);
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
			for (IssueEvent ie : user.getIssueEvents()) {
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
				user.getIssueEvents().add(ie);
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
			a.setNumberOfProjects(1); //FIXME not implemented yet
			a.setCount(1);
			user.getArtefacts().add(a);
		}
	}
}
