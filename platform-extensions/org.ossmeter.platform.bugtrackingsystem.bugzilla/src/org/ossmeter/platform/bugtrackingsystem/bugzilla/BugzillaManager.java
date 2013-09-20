package org.ossmeter.platform.bugtrackingsystem.bugzilla;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import utils.BugSearch.SearchLimiter;
import utils.BugSearch.SearchQuery;
import utils.BugzillaSession;
import utils.Comment;

import com.j2bugzilla.base.Bug;
import com.j2bugzilla.base.BugzillaException;


public class BugzillaManager implements IBugTrackingSystemManager<Bugzilla> {
	
	private final String BUG_QUERY_LIMIT = "10";
	private final int COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE = 10;

	@Override
	public boolean appliesTo(Bugzilla bugzilla) {
		return bugzilla instanceof Bugzilla;
	}

	@Override
	public BugTrackingSystemDelta getDelta(Bugzilla bugzilla, Date date) throws Exception {
		
		System.out.println("Date: " + date.toString());
		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		
		BugTrackingSystemDelta delta = new BugTrackingSystemDelta();
		delta.setBugTrackingSystem(bugzilla);
		// Get bugs started on date
		List<Bug> bugs = getBugs(bugzilla, delta, session, date);
		// Get the comments of bugs that were started on date
		getComments(bugzilla, delta, session, bugs, date);
		// Get the comments that were started before date
		getUpdatedBugsComments(bugzilla, delta, session, date);
		return delta;
	}

	private void getUpdatedBugsComments(Bugzilla bugzilla, BugTrackingSystemDelta delta, 
			 BugzillaSession session, Date date) throws Exception {

		SearchQuery[] searchQueries;
		if (!bugzilla.getComponent().equals("null"))
			searchQueries = new SearchQuery[4];
		else
			searchQueries = new SearchQuery[3];
		searchQueries[0] = new SearchQuery(SearchLimiter.PRODUCT, bugzilla.getProduct());
		searchQueries[1] = new SearchQuery(SearchLimiter.LIMIT, BUG_QUERY_LIMIT);
		if (!bugzilla.getComponent().equals("null"))
			searchQueries[3] = new SearchQuery(SearchLimiter.COMPONENT, bugzilla.getComponent());
		java.util.Date javaDate = date.toJavaDate();
		int counter = 0;
		while (date.compareTo(javaDate)==0) {
			counter++;
			searchQueries[2] = new SearchQuery( SearchLimiter.LAST_CHANGE_TIME, date.toJavaDate() );
			List<Bug> bugs = session.getBugs(searchQueries);
			System.out.println(counter + ". getComments:\t" + bugs.size() + " bugs retrieved");
			javaDate = session.getCreationTime(bugs.get(bugs.size()-1));
			int startOffset = 0, endOffset;
			while (startOffset < bugs.size()) {
				if (startOffset + COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE <= bugs.size())
					endOffset = startOffset + COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE;
				else
					endOffset = bugs.size();
				List<Bug> bugsSlice = bugs.subList(startOffset, endOffset);
				getComments(bugzilla, delta, session, bugsSlice, date);
				startOffset += COMMENT_QUERY__NO_BUGS_RETRIEVED_AT_ONCE;
			}
		}
	}
	
	private void getComments(Bugzilla bugzilla, BugTrackingSystemDelta delta, 
				BugzillaSession session, List<Bug> bugs, Date date) throws Exception {
	    System.out.println("bugs size is: " + bugs.size());
	    if (bugs.size()>0) {
    		List<Integer> bugIds = new ArrayList<Integer>();
    		Map<Integer, Bug> bugIdBugMap = new HashMap<Integer, Bug>();
	    	for (Bug bug: bugs) {
	    		bugIds.add(bug.getID());
	    		bugIdBugMap.put(bug.getID(), bug);
	    	}
		    List<Comment> comments = session.getCommentsForBugIds(bugIds, date.toJavaDate());
			System.out.println("getBugs for comments A:\t" + comments.size() + " comments retrieved");
			for (Comment comment: comments) {
				if (date.compareTo(comment.getTimestamp())==0)  {
					System.out.println(date.toString() + 
						"\t" + comment.getBugId() + 
						"\t" + comment.getId() + 
						"\t" + comment.getTimestamp().toString());
					storeBug(bugzilla, bugIdBugMap.get(comment.getBugId()), 
							delta.getUpdatedBugs(), "delta.getUpdatedBugs()", session);
					storeComment(bugzilla, comment, delta);
				}
			}
	    }
	}
	
	private List<Bug> getBugs(Bugzilla bugzilla, 
			BugTrackingSystemDelta delta, BugzillaSession session, Date date) throws BugzillaException {
		SearchQuery[] searchQueries;
		if (!bugzilla.getComponent().equals("null"))
			searchQueries = new SearchQuery[4];
		else
			searchQueries = new SearchQuery[3];
		searchQueries[0] = new SearchQuery(SearchLimiter.PRODUCT, bugzilla.getProduct());
		searchQueries[1] = new SearchQuery(SearchLimiter.LIMIT, BUG_QUERY_LIMIT);
		if (!bugzilla.getComponent().equals("null"))
			searchQueries[3] = new SearchQuery(SearchLimiter.COMPONENT, bugzilla.getComponent());
		List<Bug> bugs = new ArrayList<Bug>();
		java.util.Date javaDate = date.toJavaDate();
		int counter = 0;
		while (date.compareTo(javaDate)==0) {
			counter++;
			searchQueries[2] = new SearchQuery( SearchLimiter.CREATION_TIME, javaDate );
			List<Bug> retrievedBugs = session.getBugs(searchQueries);
			System.out.println(counter + ". getBugs:\t" + retrievedBugs.size() + " bugs retrieved");
			for (Bug retrievedBug : retrievedBugs) {
				if (date.compareTo(session.getCreationTime(retrievedBug)) == 0) {
					System.out.println(date.toString() + 
							"\t" + retrievedBug.getID() + 
							"\t" + session.getCreationTime(retrievedBug).toString());
					storeBug(bugzilla, retrievedBug, delta.getNewBugs(), "delta.getNewBugs()", session);
					bugs.add(retrievedBug);
				}
				javaDate = session.getCreationTime(retrievedBug);
			}
		}
		return bugs;
	}

	private void storeComment(Bugzilla bugzilla, 
								Comment comment,
									BugTrackingSystemDelta delta) {
		Boolean alreadyStored = false;
		for (BugTrackingSystemComment storedComment: delta.getComments())
			if (Integer.parseInt(storedComment.getCommentId())==comment.getId())
				alreadyStored = true;
		if (!alreadyStored) {
			BugzillaComment bugzillaComment = new BugzillaComment();		
			bugzillaComment.setAuthor(comment.getAuthor());
			bugzillaComment.setBugId(comment.getBugId()+"");

			Bugzilla newBugzilla = new Bugzilla();
			newBugzilla.setUrl(bugzilla.getUrl());
			newBugzilla.setProduct(bugzilla.getProduct());
			newBugzilla.setComponent(bugzilla.getComponent());
			bugzillaComment.setBugTrackingSystem(newBugzilla);

			bugzillaComment.setCommentId(comment.getId()+"");
			bugzillaComment.setCreationTime(comment.getTimestamp());
			bugzillaComment.setCreator(comment.getCreator());
			bugzillaComment.setCreatorId(comment.getCreatorId()+"");
			delta.getComments().add(bugzillaComment);
			System.err.println("stored 1 comment");
		}
	}
	
	private void storeBug(Bugzilla bugzilla, Bug bug,
						   List<BugTrackingSystemBug> deltaBugList, String bugListName, 
						   BugzillaSession session) {
		Boolean alreadyStored = false;
		for (BugTrackingSystemBug storedBug: deltaBugList)
			if (Integer.parseInt(storedBug.getBugId())==bug.getID())
				alreadyStored = true;
		if (!alreadyStored) {
			BugzillaBug bugzillaBug = new BugzillaBug();
			bugzillaBug.setBugId(bug.getID()+"");
			bugzillaBug.setCreationTime(session.getCreationTime(bug));
			bugzillaBug.setCreator(session.getCreator(bug));
			bugzillaBug.setStatus(bug.getStatus());
//			bugzillaBug.setSummary(bug.getSummary());
			bugzillaBug.setAssignedTo(session.getAssignedTo(bug));
			bugzillaBug.setCategory(session.getCategory(bug));
			bugzillaBug.setClassification(session.getClassification(bug));
			bugzillaBug.setCloneOf(session.getCloneOf(bug));
			bugzillaBug.setCrm(session.getCrm(bug));
			bugzillaBug.setDocType(session.getDocType(bug));
			bugzillaBug.setDocumentationAction(session.getDocumentationAction(bug));
			bugzillaBug.setEnvironment(session.getEnvironment(bug));
			bugzillaBug.setFixedIn(session.getFixedIn(bug));
			bugzillaBug.setIsCcAccesible(session.getIsCcAccesible(bug));
			bugzillaBug.setIsConfirmed(session.getIsConfirmed(bug));
			bugzillaBug.setIsCreatorAccessible(session.getIsCreatorAccessible(bug));
			bugzillaBug.setIsOpen(session.getIsOpen(bug));
			bugzillaBug.setLastChangeTime(session.getLastChangeTime(bug));
			bugzillaBug.setLastClosed(session.getLastClosed(bug));
			bugzillaBug.setMountType(session.getMountType(bug));
			bugzillaBug.setOperatingSystem(bug.getOperatingSystem());
			bugzillaBug.setPlatform(bug.getPlatform());
			bugzillaBug.setPriority(bug.getPriority());
			bugzillaBug.setProduct(bug.getProduct());
			bugzillaBug.setQualityAssuranceContact(session.getQualityAssuranceContact(bug));
			bugzillaBug.setRegressionStatus(session.getRegressionStatus(bug));
			bugzillaBug.setReleaseNotes(session.getReleaseNotes(bug));
			bugzillaBug.setResolution(bug.getResolution());
			bugzillaBug.setSeverity(session.getSeverity(bug));
			bugzillaBug.setStoryPoints(session.getStoryPoints(bug));
			bugzillaBug.setTargetMilestone(session.getTargetMilestone(bug));
			bugzillaBug.setType(session.getType(bug));
			bugzillaBug.setVerifiedBranch(session.getVerifiedBranch(bug));
			bugzillaBug.setWhiteBoard(session.getWhiteBoard(bug));
			
			Bugzilla newBugzilla = new Bugzilla();
			newBugzilla.setUrl(bugzilla.getUrl());
			newBugzilla.setProduct(bugzilla.getProduct());
			newBugzilla.setComponent(bugzilla.getComponent());
			bugzillaBug.setBugTrackingSystem(newBugzilla);
					
			deltaBugList.add(bugzillaBug);
			System.err.println(bugListName + ": (" + bugzillaBug.getBugId() + ") stored 1 bug");
		}
	}

	@Override
	public Date getFirstDate(Bugzilla bugzilla) throws Exception {

		SearchQuery[] searchQueries;
		if (!bugzilla.getComponent().equals("null"))
			searchQueries = new SearchQuery[3];
		else
			searchQueries = new SearchQuery[2];
		searchQueries[0] = new SearchQuery(SearchLimiter.PRODUCT, bugzilla.getProduct()); // "Pulp");
		if (!bugzilla.getComponent().equals("null"))
			searchQueries[2] = new SearchQuery(SearchLimiter.COMPONENT, bugzilla.getComponent());  // "acpi");
		searchQueries[1] = new SearchQuery(SearchLimiter.LIMIT, "10");

		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		List<Bug> bugs = session.getBugs(searchQueries);
		System.out.println("getFirstDate:\t" + bugs.size() + " bugs retrieved");
		Date date = null;
		if (bugs.size()>0) {
			Bug bug = bugs.get(0);
			date = new Date(session.getCreationTime(bug));
		} else {
			System.err.println("Unable to retrieve first date");
		}
		date.addDays(-1);
		System.out.println("\t" + date.toString());
		return date;
	}

	@Override
	public String getContents(Bugzilla bugzilla, BugTrackingSystemBug bug) throws Exception {
		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		Bug retrievedBug = session.getBugById(Integer.parseInt(bug.getBugId()));
		System.out.println("getContents:\tbug retrieved");
		return retrievedBug.getSummary();
	}

	@Override
	public String getContents(Bugzilla bugzilla,
			BugTrackingSystemComment comment) throws Exception {
		BugzillaSession session = new BugzillaSession(bugzilla.getUrl());
		List<Comment> retrievedComments = 
				session.getCommentsForBugId(Integer.parseInt(comment.getBugId()));
		for (Comment retrievedComment: retrievedComments) {
			if (retrievedComment.getId() == Integer.parseInt(comment.getCommentId())) {
				System.out.println("getContents:\tcomment retrieved");
				return retrievedComment.getText();
			}
		}
		return null;
	}

}
