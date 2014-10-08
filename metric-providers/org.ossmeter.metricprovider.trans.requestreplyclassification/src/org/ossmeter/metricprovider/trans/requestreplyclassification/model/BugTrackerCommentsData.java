package org.ossmeter.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerCommentsData extends Pongo {
	
	
	
	public BugTrackerCommentsData() { 
		super();
		BUGTRACKERID.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerCommentsData");
		BUGID.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerCommentsData");
		COMMENTID.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerCommentsData");
		CLASSIFICATIONRESULT.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerCommentsData");
		DATE.setOwningType("org.ossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerCommentsData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer CLASSIFICATIONRESULT = new StringQueryProducer("classificationResult"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerCommentsData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerCommentsData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public BugTrackerCommentsData setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getClassificationResult() {
		return parseString(dbObject.get("classificationResult")+"", "");
	}
	
	public BugTrackerCommentsData setClassificationResult(String classificationResult) {
		dbObject.put("classificationResult", classificationResult);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public BugTrackerCommentsData setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}