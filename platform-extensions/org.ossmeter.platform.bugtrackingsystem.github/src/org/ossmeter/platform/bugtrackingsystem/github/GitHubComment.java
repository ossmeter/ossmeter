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
