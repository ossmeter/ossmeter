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
