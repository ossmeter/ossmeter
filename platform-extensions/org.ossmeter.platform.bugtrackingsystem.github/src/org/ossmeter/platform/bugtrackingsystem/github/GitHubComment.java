package org.ossmeter.platform.bugtrackingsystem.github;

import java.util.Date;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class GitHubComment extends BugTrackingSystemComment {

    private static final long serialVersionUID = 1L;

    private Date updatedAt;
    private String url;

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

 

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
