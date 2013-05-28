package org.ossmeter.platform.bugtrackingsystem.bugzilla;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class BugzillaComment  extends BugTrackingSystemComment {

	private static final long serialVersionUID = 1L;
	
	protected String creatorId;
	protected String author;

	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
}
