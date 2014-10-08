package org.ossmeter.platform.bugtrackingsystem.redmine.api;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class RedmineComment extends BugTrackingSystemComment {

	private static final long serialVersionUID = 1L;

	private Integer creatorId;
	private List<RedmineCommentDetails> details = new ArrayList<RedmineCommentDetails>();

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public List<RedmineCommentDetails> getDetails() {
		return details;
	}

}
