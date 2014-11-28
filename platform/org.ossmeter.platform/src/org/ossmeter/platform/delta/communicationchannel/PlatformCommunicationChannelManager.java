/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.ossmeter.platform.delta.communicationchannel;

import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.cache.communicationchannel.CommunicationChannelContentsCache;
import org.ossmeter.platform.cache.communicationchannel.CommunicationChannelDeltaCache;
import org.ossmeter.platform.cache.communicationchannel.ICommunicationChannelContentsCache;
import org.ossmeter.platform.cache.communicationchannel.ICommunicationChannelDeltaCache;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public abstract class PlatformCommunicationChannelManager implements ICommunicationChannelManager<CommunicationChannel> {
	
	protected List<ICommunicationChannelManager> communicationChannelManagers;
	protected ICommunicationChannelDeltaCache deltaCache;
	protected ICommunicationChannelContentsCache contentsCache;
	
	abstract public List<ICommunicationChannelManager> getCommunicationChannelManagers();
	
	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {
		return getCommunicationChannelManager(communicationChannel) != null;
	}

	protected ICommunicationChannelManager getCommunicationChannelManager(CommunicationChannel communicationChannel) {
		for (ICommunicationChannelManager communicationChannelManager : getCommunicationChannelManagers()) {
			if (communicationChannelManager.appliesTo(communicationChannel)) {
				return communicationChannelManager;
			}
		}
		return null;
	}
	
	@Override
	public Date getFirstDate(DB db, CommunicationChannel communicationChannel)
			throws Exception {
		for (ICommunicationChannelManager communicationChannelManager : getCommunicationChannelManagers()) {
			if (communicationChannelManager.appliesTo(communicationChannel)) {
				return communicationChannelManager.getFirstDate(db, communicationChannel);
			}
		}
//		throw new RuntimeException("No communication channel manager applies to " + communicationChannel);
		//FIXME: Needs to log this error. 
		return null;
	}
	
	@Override
	public CommunicationChannelDelta getDelta(DB db, CommunicationChannel communicationChannel, Date date)  throws Exception {
		CommunicationChannelDelta cache = getDeltaCache().getCachedDelta(communicationChannel.getUrl(), date);
		if (cache != null) {
			System.err.println("CommunicationChannelDelta CACHE HIT!");
			return cache;
		}
		
		ICommunicationChannelManager communicationChannelManager = getCommunicationChannelManager(communicationChannel);
		if (communicationChannelManager != null) {
			CommunicationChannelDelta delta = communicationChannelManager.getDelta(db, communicationChannel, date);
			getDeltaCache().putDelta(communicationChannel.getUrl(), date, delta);
			return delta;
		}
		return null;
	}

	@Override
	public String getContents(DB db, CommunicationChannel communicationChannel, CommunicationChannelArticle article) throws Exception {
		String cache = getContentsCache().getCachedContents(article);
		if (cache != null) {
			System.err.println("CommunicationChannelArticle CACHE HIT!");
			return cache;
		}

		ICommunicationChannelManager communicationChannelManager =
		getCommunicationChannelManager((article.getCommunicationChannel()));
		
		if (communicationChannelManager != null) {
			String contents = communicationChannelManager.getContents(db, communicationChannel, article);
			getContentsCache().putContents(article, contents);
			return contents;
		}
		return null;
	}
	
	public ICommunicationChannelContentsCache getContentsCache() {
		if (contentsCache == null) {
			contentsCache = new CommunicationChannelContentsCache();
		}
		return contentsCache;
	}
	
	public ICommunicationChannelDeltaCache getDeltaCache() {
		if (deltaCache == null) {
			deltaCache = new CommunicationChannelDeltaCache();
		}
		return deltaCache;		
	}

}
