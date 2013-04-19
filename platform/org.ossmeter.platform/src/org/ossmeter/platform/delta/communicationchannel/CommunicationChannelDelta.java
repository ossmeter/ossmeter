package org.ossmeter.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.ossmeter.repository.model.CommunicationChannel;

public class CommunicationChannelDelta  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	transient protected CommunicationChannel communicationChannel;
	protected List<CommunicationChannelArticle> articles = new ArrayList<CommunicationChannelArticle>();
//	protected String latestArticleId;
	
	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}
	
	public void setNewsgroup(CommunicationChannel communicationChannel) {
		this.communicationChannel = communicationChannel;
	}
	
	public List<CommunicationChannelArticle> getArticles() {
		return articles;
	}
	
//	public String getLatestArticleId() {
//		return latestArticleId;
//	}
	
//	public void setLatestArticleId(String latestArticleId) {
//		this.latestArticleId = latestArticleId;
//	} //TODO THIS NEEDS SETTING ON CREATION
	
}
