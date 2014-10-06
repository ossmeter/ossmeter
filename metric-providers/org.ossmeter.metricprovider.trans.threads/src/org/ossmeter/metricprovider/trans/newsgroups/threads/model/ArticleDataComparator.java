package org.ossmeter.metricprovider.trans.newsgroups.threads.model;

import java.util.Comparator;

public class ArticleDataComparator implements Comparator<ArticleData>{

	@Override
	public int compare(ArticleData articleA, ArticleData articleB) {
		if (!articleA.getUrl_name().equals(articleB.getUrl_name()))
			return articleA.getUrl_name().compareTo(articleB.getUrl_name());
		else 
			return articleA.getArticleNumber() - articleB.getArticleNumber();
	}

}
