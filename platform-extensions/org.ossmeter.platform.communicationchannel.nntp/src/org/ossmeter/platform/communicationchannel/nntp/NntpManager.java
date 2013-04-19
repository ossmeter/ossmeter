package org.ossmeter.platform.communicationchannel.nntp;

import java.io.Reader;

import org.apache.commons.net.nntp.Article;
import org.apache.commons.net.nntp.NNTPClient;
import org.apache.commons.net.nntp.NewsgroupInfo;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.ossmeter.repository.model.NntpNewsGroup;

public class NntpManager implements ICommunicationChannelManager<NntpNewsGroup> {
	
	private final static int RETRIEVAL_STEP = 50;

	@Override
	public boolean appliesTo(NntpNewsGroup newsgroup) {
		return newsgroup instanceof NntpNewsGroup;
	}

	@Override
	public CommunicationChannelDelta getDelta(NntpNewsGroup newsgroup, Date date) throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);

		NewsgroupInfo newsgroupInfo = NntpUtil.selectNewsgroup(nntpClient, newsgroup);
		int lastArticle = newsgroupInfo.getLastArticle();
		int lastArticleChecked = Integer.parseInt(newsgroup.getLastArticleChecked());
		if (lastArticleChecked<0) lastArticleChecked = newsgroupInfo.getFirstArticle();

		CommunicationChannelDelta delta = new CommunicationChannelDelta();
		delta.setNewsgroup(newsgroup);

		int retrievalStep = RETRIEVAL_STEP;
		Boolean dayCompleted = false;
		while (!dayCompleted) {
			if (lastArticleChecked + retrievalStep > lastArticle) {
				retrievalStep = lastArticle - lastArticleChecked;
				dayCompleted = true;
			}
			Article[] articles = NntpUtil.getArticleInfo(nntpClient, 
					lastArticleChecked + 1, lastArticleChecked + retrievalStep);
			for (Article article: articles) {
				
				java.util.Date javaArticleDate = NntpUtil.parseDate(article.getDate());
				Date articleDate = new Date(javaArticleDate);
				if (date.compareTo(articleDate) < 0) dayCompleted = true;
				else if (date.compareTo(articleDate) == 0) {
					CommunicationChannelArticle communicationChannelArticle = new CommunicationChannelArticle();
					communicationChannelArticle.setArticleId(article.getArticleId());
					communicationChannelArticle.setArticleNumber(article.getArticleNumber());
					communicationChannelArticle.setDate(javaArticleDate);
//					I haven't seen any messageThreadIds on NNTP servers, yet.
//					communicationChannelArticle.setMessageThreadId(article.messageThreadId());
					NntpNewsGroup newNewsgroup = new NntpNewsGroup();
					newNewsgroup.setUrl(newsgroup.getUrl());
					newNewsgroup.setAuthenticationRequired(newsgroup.getAuthenticationRequired());
					newNewsgroup.setUsername(newsgroup.getUsername());
					newNewsgroup.setPassword(newsgroup.getPassword());
					newNewsgroup.setPort(newsgroup.getPort());
					newNewsgroup.setInterval(newsgroup.getInterval());
					communicationChannelArticle.setNewsgroup(newNewsgroup);
					communicationChannelArticle.setReferences(article.getReferences());
					communicationChannelArticle.setSubject(article.getSubject());
					communicationChannelArticle.setUser(article.getFrom());
					delta.getArticles().add(communicationChannelArticle);
					lastArticleChecked = article.getArticleNumber();
				} 
				else {
						//TODO: In this case, there are unprocessed articles whose date is earlier than the date requested.
						//      This means that the deltas of those article dates are incomplete, 
						//		i.e. the deltas did not contain all articles of those dates.
				}
			}
		}
		nntpClient.disconnect(); 
		newsgroup.setLastArticleChecked(lastArticleChecked+"");
		return delta;
	}

	@Override
	public Date getFirstDate(NntpNewsGroup newsgroup)
			throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		NewsgroupInfo newsgroupInfo = NntpUtil.selectNewsgroup(nntpClient, newsgroup);
		int firstArticleNumber = newsgroupInfo.getFirstArticle();
		Reader reader = nntpClient.retrieveArticle(firstArticleNumber);
		ArticleHeader articleHeader = new ArticleHeader(reader);
//		Article article = NntpUtil.getArticleInfo(nntpClient, articleId);
		nntpClient.disconnect();
//		String date = article.getDate();
		return new Date(NntpUtil.parseDate(articleHeader.getDate().trim()));
	}

	@Override
	public String getContents(NntpNewsGroup newsgroup, CommunicationChannelArticle article) throws Exception {
		NNTPClient nntpClient = NntpUtil.connectToNntpServer(newsgroup);
		NntpUtil.selectNewsgroup(nntpClient, newsgroup);		
		String contents = NntpUtil.getArticleBody(nntpClient, article.getArticleNumber());
		nntpClient.disconnect();
		return contents;
	}

}
