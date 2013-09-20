package org.ossmeter.metricprovider.requestreplyclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupArticlesData extends Pongo {
	
	
	
	public NewsgroupArticlesData() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData");
		ARTICLEID.setOwningType("org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData");
		CLASSIFICATIONRESULT.setOwningType("org.ossmeter.metricprovider.requestreplyclassification.model.NewsgroupArticlesData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer ARTICLEID = new NumericalQueryProducer("articleId");
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public NewsgroupArticlesData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public int getArticleId() {
		return parseInteger(dbObject.get("articleId")+"", 0);
	}
	
	public NewsgroupArticlesData setArticleId(int articleId) {
		dbObject.put("articleId", articleId);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public NewsgroupArticlesData setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	
	
	
	
}