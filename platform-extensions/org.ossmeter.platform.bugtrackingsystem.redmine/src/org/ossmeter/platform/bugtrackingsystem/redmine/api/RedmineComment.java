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
package org.ossmeter.platform.bugtrackingsystem.redmine.api;

import java.util.ArrayList;
import java.util.List;

import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class RedmineComment extends BugTrackingSystemComment {

	private static final long serialVersionUID = 1L;

	private Integer creatorId;
	private List<RedmineCommentDetails> details = new ArrayList<RedmineCommentDetails>();

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public List<RedmineCommentDetails> getDetails() {
		return details;
	}

}
