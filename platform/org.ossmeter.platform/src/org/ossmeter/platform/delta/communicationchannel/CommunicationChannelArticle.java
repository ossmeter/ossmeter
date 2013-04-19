package org.ossmeter.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.Date;

import org.ossmeter.repository.model.NntpNewsGroup;

public class CommunicationChannelArticle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String articleId;
	protected int articleNumber;
	transient protected NntpNewsGroup newsgroup;
	protected String messageThreadId;
	protected String subject;
	protected String user;
	protected Date date;
	protected String[] references;
	
	
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}

	public NntpNewsGroup getCommunicationChannel() {
		return newsgroup;
	}

	public void setNewsgroup(NntpNewsGroup communicationChannel) {
		this.newsgroup = communicationChannel;
	}

	public String getMessageThreadId() {
		return messageThreadId;
	}

	public void setMessageThreadId(String messageThreadId) {
		this.messageThreadId = messageThreadId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String[] getReferences() {
		return references;
	}

	public void setReferences(String[] references) {
		this.references = references;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CommunicationChannelArticle) {
//			if (!this.newsgroup.equals(((CommunicationChannelArticle) obj).getCommunicationChannel())) {
//				return false;
//			} 
			if (this.articleNumber != ((CommunicationChannelArticle) obj).getArticleNumber()) {
				return false;
			}
			return true;
		}
		
		return false;
	}

}
