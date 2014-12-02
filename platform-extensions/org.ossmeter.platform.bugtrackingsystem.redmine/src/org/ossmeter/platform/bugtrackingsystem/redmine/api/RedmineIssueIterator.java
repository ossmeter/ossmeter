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

import java.util.Iterator;

public class RedmineIssueIterator implements Iterator<RedmineIssue> {
	private final RedmineRestClient redmine;
	private Iterator<Integer> issueIds;

	public RedmineIssueIterator(RedmineRestClient redmine,
			Iterator<Integer> issueIds) {
		this.redmine = redmine;
		this.issueIds = issueIds;
	}

	@Override
	public boolean hasNext() {
		return issueIds.hasNext();
	}

	@Override
	public RedmineIssue next() {
		try {
			return redmine.getIssue(issueIds.next());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
