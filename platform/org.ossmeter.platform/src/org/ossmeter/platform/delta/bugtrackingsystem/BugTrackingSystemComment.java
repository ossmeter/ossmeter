package org.ossmeter.platform.delta.bugtrackingsystem;

import java.io.Serializable;
import java.util.Date;

import org.ossmeter.repository.model.BugTrackingSystem;

public class BugTrackingSystemComment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String commentId;
	protected String creator;
	protected Date creationTime;
//	protected String text;
	transient protected String bugId;
	transient protected BugTrackingSystem bugTrackingSystem;	
	
	public String getBugId() {
		return bugId;
	}
	
	public void setBugId(String bugId) {
		this.bugId = bugId;
	}
	
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

//	public String getText() {
//		return text;
//	}

//	public void setText(String text) {
//		this.text = text;
//	}

	public BugTrackingSystem getBugTrackingSystem() {
		return bugTrackingSystem;
	}

	public void setBugTrackingSystem(BugTrackingSystem bugTrackingSystem) {
		this.bugTrackingSystem = bugTrackingSystem;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BugTrackingSystemComment) {
			if ( this.bugId != ((BugTrackingSystemComment) obj).getBugId() ) {
				return false;
			} 
			if (!this.commentId.equals(((BugTrackingSystemComment) obj).getCommentId())) {
				return false;
			}
			return true;
		}
		
		return false;
	}
	
	
}
