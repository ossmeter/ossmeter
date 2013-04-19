package org.ossmeter.platform.cache.communicationchannel;

import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;

public interface ICommunicationChannelContentsCache {
	
	public String getCachedContents(CommunicationChannelArticle article);
	
	public void putContents(CommunicationChannelArticle article, String contents);
}
