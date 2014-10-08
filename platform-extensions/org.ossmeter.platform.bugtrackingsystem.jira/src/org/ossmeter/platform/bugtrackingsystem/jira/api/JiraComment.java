package org.ossmeter.platform.bugtrackingsystem.jira.api;

import java.util.Date;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class JiraComment extends BugTrackingSystemComment {

    private static final long serialVersionUID = 1L;

    private String url;
    private String updateAuthor;
    private Date updateDate;

    public String getUrl() {
        return url;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
