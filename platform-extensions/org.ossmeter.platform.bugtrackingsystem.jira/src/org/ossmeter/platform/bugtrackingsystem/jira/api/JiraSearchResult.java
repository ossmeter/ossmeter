/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
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
