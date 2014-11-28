package org.ossmeter.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupArticles extends Pongo {
	
	
	
	public NewsgroupArticles() { 
		super();
		NEWSGROUPNAME.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
		ARTICLENUMBER.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
		CLASSIFICATIONRESULT.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
		DATE.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer ARTICLENUMBER = new NumericalQueryProducer("articleNumber");
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupArticles setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getArticleNumber() {
		return parseInteger(dbObject.get("articleNumber")+"", 0);
	}
	
	public NewsgroupArticles setArticleNumber(int articleNumber) {
		dbObject.put("articleNumber", articleNumber);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public NewsgroupArticles setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public NewsgroupArticles setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}