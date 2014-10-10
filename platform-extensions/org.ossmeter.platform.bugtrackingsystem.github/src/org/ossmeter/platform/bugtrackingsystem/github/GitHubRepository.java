package org.ossmeter.platform.bugtrackingsystem.github;

import java.io.Serializable;

public class GitHubRepository implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private long id;
    private String owner;
    private String name;
    
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
