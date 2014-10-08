package org.ossmeter.platform.bugtrackingsystem.jira.api;

import java.util.List;

public class JiraSearchResult {
    private int startAt;
    private int maxResults;
    private int total;
    private List<JiraIssue> issues;
    
    public int getStartAt() {
        return startAt;
    }
    
    public int getMaxResults() {
        return maxResults;
    }
    
    public int getTotal() {
        return total;
    }
    
    public List<JiraIssue> getIssues() {
        return issues;
    }
    
}
