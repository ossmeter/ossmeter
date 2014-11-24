package org.ossmeter.metricprovider.trans.newsgroups.threads.model;

import java.util.Comparator;

public class ArticleDataComparator implements Comparator<ArticleData>{

	@Override
	public int compare(ArticleData articleA, ArticleData articleB) {
		if (!articleA.getNewsgroupName().equals(articleB.getNewsgroupName()))
			return articleA.getNewsgroupName().compareTo(articleB.getNewsgroupName());
		else 
			return articleA.getArticleNumber() - articleB.getArticleNumber();
	}

}
