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
