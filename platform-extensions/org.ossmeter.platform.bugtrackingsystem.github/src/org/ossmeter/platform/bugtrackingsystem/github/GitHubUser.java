package org.ossmeter.platform.bugtrackingsystem.github;

import java.io.Serializable;

public class GitHubUser implements Serializable {

    private static final long serialVersionUID = -8941986254623029643L;
    
    private String id;
    private String login;
    private String name;
    private String type;
    private String url;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
