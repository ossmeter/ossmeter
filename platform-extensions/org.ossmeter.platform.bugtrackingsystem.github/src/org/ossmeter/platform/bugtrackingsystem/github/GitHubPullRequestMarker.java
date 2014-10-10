package org.ossmeter.platform.bugtrackingsystem.github;

import java.io.Serializable;

public class GitHubPullRequestMarker implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long repository;
    private String label;
    private String ref;
    private String sha;
    private String user;

    public Long getRepository() {
        return repository;
    }

    public void setRepository(Long id) {
        this.repository = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
