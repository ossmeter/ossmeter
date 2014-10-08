package org.ossmeter.platform.bugtrackingsystem.sourceforge.api;

import java.util.Date;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class SourceForgeComment extends BugTrackingSystemComment {

    private static final long serialVersionUID = 1L;
    
    private String subject;
	private SourceForgeAttachment[] attachments;
	private Date updateDate;
	
	public SourceForgeComment() {
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getUpdateDate() {
        return updateDate;
    }
	
	public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
	
	public SourceForgeAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(SourceForgeAttachment[] attachments) {
		this.attachments = attachments;
	}
	
	
}
