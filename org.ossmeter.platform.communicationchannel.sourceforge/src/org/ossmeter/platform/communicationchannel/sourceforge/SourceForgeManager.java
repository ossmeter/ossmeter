/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.ossmeter.platform.communicationchannel.sourceforge;

import java.net.URISyntaxException;
import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.communicationchannel.cache.Cache;
import org.ossmeter.platform.communicationchannel.cache.Caches;
import org.ossmeter.platform.communicationchannel.cache.provider.BasicCacheProvider;
import org.ossmeter.platform.communicationchannel.sourceforge.api.SourceForgeArticle;
import org.ossmeter.platform.communicationchannel.sourceforge.api.SourceForgeDiscussionRestClient;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.ICommunicationChannelManager;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class SourceForgeManager implements
		ICommunicationChannelManager<Discussion> {

	private Caches<SourceForgeArticle, String> articleCaches = 
			new Caches<SourceForgeArticle, String>(new ArticleCacheProvider());

	private static class ArticleCacheProvider extends
			BasicCacheProvider<SourceForgeArticle, String> {

		public Iterator<SourceForgeArticle> getItems(CommunicationChannel communicationChannel)
				throws Exception {
			Discussion sfCommunicationChannel = (Discussion) communicationChannel;
			SourceForgeDiscussionRestClient sourceforge = getSourceForge(sfCommunicationChannel);
			return sourceforge.getArticles();
		}

		public String getKey(SourceForgeArticle item) {
			return item.getArticleId();
		}

		public boolean changedOnDate(SourceForgeArticle item,
				java.util.Date date, CommunicationChannel communicationChannel) {
			return findMatchOnDate(date, item.getDate(), item.getUpdateDate());
		}

		public boolean changedSinceDate(SourceForgeArticle item,
				java.util.Date date, CommunicationChannel communicationChannel) {
			return findMatchSinceDate(date, item.getDate(), item.getUpdateDate());
		}

		public void process(SourceForgeArticle item, CommunicationChannel communicationChannel) {
			item.setText(null); // remove content field
		}

	}

	@Override
	public boolean appliesTo(CommunicationChannel communicationChannel) {
		return (communicationChannel instanceof Discussion);
	}

	@Override
	public CommunicationChannelDelta getDelta(DB db, Discussion communicationChannel, Date date) throws Exception {

		java.util.Date day = date.toJavaDate();

		Cache<SourceForgeArticle, String> articleCache = articleCaches.getCache(communicationChannel, true);
		Iterable<SourceForgeArticle> articles = articleCache.getItemsAfterDate(day);

		SourceForgeCommunicationChannelDelta delta = new SourceForgeCommunicationChannelDelta();
		for (SourceForgeArticle article : articles) {

			java.util.Date articleDate = article.getDate();
			if (article.getUpdateDate() != null)
				articleDate = article.getUpdateDate();
			if (DateUtils.isSameDay(articleDate, day)) {
				delta.getArticles().add(article);
			}
		}

		return delta;
	}

	@Override
	public Date getFirstDate(DB db, Discussion communicationChannel) throws Exception {
		
		Cache<SourceForgeArticle, String> articleCache = articleCaches.getCache(communicationChannel, true);
		Iterable<SourceForgeArticle> articles = articleCache.getItems();

		Date firstDate = null;
		for (SourceForgeArticle article : articles) {
			java.util.Date articleJaveDate = article.getDate();
			if (article.getUpdateDate() != null)
				articleJaveDate = article.getUpdateDate();
			Date articleDate = new Date(articleJaveDate);
			if ( ( firstDate == null ) 
					|| ( firstDate.compareTo(articleDate) > 0 ) ) {
				firstDate = articleDate;
			}
		}
		return firstDate;
	}

	@Override
	public String getContents(DB db, Discussion communicationChannel, CommunicationChannelArticle article) throws Exception {
		SourceForgeDiscussionRestClient sourceforge = getSourceForge(communicationChannel);
		if (article instanceof SourceForgeArticle) {
			SourceForgeArticle existingSfArticle = (SourceForgeArticle) article;
			SourceForgeArticle sfArticle = 
					sourceforge.getArticle(
							existingSfArticle.getForumId(), 
								existingSfArticle.getMessageThreadId(), 
									existingSfArticle.getArticleId());
			if (null != sfArticle) {
				return sfArticle.getText();
			}
		}
		return null;
	}

	private static SourceForgeDiscussionRestClient getSourceForge(
			Discussion communicationChannel) throws URISyntaxException {
		SourceForgeDiscussionRestClient client =  
				new SourceForgeDiscussionRestClient(communicationChannel.getUrl());
		return client;
	}

	public static void main(String[] args) throws Exception {
		SourceForgeManager manager = new SourceForgeManager();

		Discussion communicationChannel = new Discussion();
//		communicationChannel.setUrl("http://sourceforge.net/rest/p/assimp/discussion/");
		communicationChannel.setUrl("http://sourceforge.net/rest/p/gimponosx/discussion/");

//		System.out.println("\nmanager.getFirstDate(null, communicationChannel) : " +
//							manager.getFirstDate(null, communicationChannel));

		SourceForgeCommunicationChannelDelta delta = 
				(SourceForgeCommunicationChannelDelta) manager.getDelta(null, communicationChannel, 
//						new Date(new DateTime(2014, 12, 15, 0, 0).toDate()));
						new Date(new DateTime(2014, 11, 25, 0, 0).toDate()));
		System.out.println("\ndelta.getArticles().size() : " + delta.getArticles().size());

		for (CommunicationChannelArticle article : delta.getArticles()) {
			SourceForgeArticle sourceForgeArticle = (SourceForgeArticle) article;
			System.out.println("\narticle.getForumId() : " + sourceForgeArticle.getForumId());
			System.out.println("article.getMessageThreadId() : " + article.getMessageThreadId());
			System.out.println("article.getArticleId() : " + article.getArticleId());
			System.out.println("article.getArticleNumber() : " + article.getArticleNumber());
			System.out.println("article.getSubject() : " + article.getSubject());
			System.out.println("article.getUser() : " + article.getUser());
			System.out.println("article.getDate() : " + article.getDate());
			System.out.println("article.getUpdateDate() : " + sourceForgeArticle.getUpdateDate());
//			The following should return null, because we are not storing the textual content.
			System.out.println("article.getText() : " + article.getText());
			System.out.println("manager.getContents() : " + manager.getContents(null, communicationChannel, sourceForgeArticle));
		}

		System.out.println("\nmanager.getFirstDate(null, communicationChannel) : " +
				manager.getFirstDate(null, communicationChannel));
		
		System.err.println("End of execution");

	}

}
