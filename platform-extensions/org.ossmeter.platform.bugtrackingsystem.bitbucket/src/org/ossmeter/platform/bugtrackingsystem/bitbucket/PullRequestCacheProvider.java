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
package org.ossmeter.platform.bugtrackingsystem.bitbucket;

import java.util.Date;
import java.util.Iterator;

import org.ossmeter.platform.bugtrackingsystem.bitbucket.api.BitbucketPullRequest;
import org.ossmeter.platform.bugtrackingsystem.bitbucket.api.BitbucketRestClient;
import org.ossmeter.platform.bugtrackingsystem.cache.provider.BasicCacheProvider;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.bitbucket.BitbucketBugTrackingSystem;

public class PullRequestCacheProvider extends
		BasicCacheProvider<BitbucketPullRequest, Long> {

	@Override
	public Iterator<BitbucketPullRequest> getItems(BugTrackingSystem bugTracker)
			throws Exception {

		BitbucketBugTrackingSystem bts = (BitbucketBugTrackingSystem) bugTracker;
		BitbucketRestClient bitbucket = BitbucketManager
				.getBitbucketRestClient(bts);
		return bitbucket.getPullRequests(bts.getUser(), bts.getRepository());
	}

	@Override
	public boolean changedOnDate(BitbucketPullRequest item, Date date,
			BugTrackingSystem bugTracker) {
		return findMatchOnDate(date, item.getCreatedOn(), item.getUpdatedOn());
	}

	@Override
	public boolean changedSinceDate(BitbucketPullRequest item, Date date,
			BugTrackingSystem bugTracker) {
		return findMatchSinceDate(date, item.getCreatedOn(), item.getUpdatedOn());
	}

	@Override
	public Long getKey(BitbucketPullRequest item) {
		return item.getId();
	}

	@Override
	public void process(BitbucketPullRequest item, BugTrackingSystem bugTracker) {
		// Do nothing
	}

}
