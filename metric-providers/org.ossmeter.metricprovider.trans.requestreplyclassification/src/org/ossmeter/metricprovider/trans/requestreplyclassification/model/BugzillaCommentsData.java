package org.ossmeter.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugzillaCommentsData extends Pongo {
	
	
	
	public BugzillaCommentsData() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugzillaCommentsData");
		PROD.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugzillaCommentsData");
		COMP.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugzillaCommentsData");
		BUGID.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugzillaCommentsData");
		COMMENTID.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugzillaCommentsData");
		CLASSIFICATIONRESULT.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugzillaCommentsData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer PROD = new StringQueryProducer("prod"); 
	public static StringQueryProducer COMP = new StringQueryProducer("comp"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public BugzillaCommentsData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getProd() {
		return parseString(dbObject.get("prod")+"", "");
	}
	
	public BugzillaCommentsData setProd(String prod) {
		dbObject.put("prod", prod);
		notifyChanged();
		return this;
	}
	public String getComp() {
		return parseString(dbObject.get("comp")+"", "");
	}
	
	public BugzillaCommentsData setComp(String comp) {
		dbObject.put("comp", comp);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugzillaCommentsData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugzillaCommentsData setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public BugzillaCommentsData setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	
	
	
	
}