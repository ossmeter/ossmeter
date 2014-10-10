package org.ossmeter.platform.bugtrackingsystem.github.api;

import org.eclipse.egit.github.core.Comment;

public class ExtendedComment extends Comment {

    private static final long serialVersionUID = 1L;
    
    private String issueUrl;

    public String getIssueUrl() {
        return issueUrl;
    }

    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
    }
    
    public Long getIssueId() {
        return parseIssueId(getIssueUrl());
    }
    
    protected static Long parseIssueId(String issueUrl) {
        Long id = null;
        if ( issueUrl != null ) {
            int index = issueUrl.lastIndexOf('/');
            if ( index > 0 ) {
                try {
                    String idString = issueUrl.substring(index+1);
                    id = Long.parseLong(idString);
                } catch(Exception e) {
                    // ignore any exceptions
                }
            }
        }
        return id;
    }
}
