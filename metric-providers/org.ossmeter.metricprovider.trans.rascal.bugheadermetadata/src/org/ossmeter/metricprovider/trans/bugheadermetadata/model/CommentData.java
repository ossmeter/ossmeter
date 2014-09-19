package org.ossmeter.metricprovider.trans.bugheadermetadata.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class CommentData extends Pongo {
	
	
	
	public CommentData() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
		PRODUCT.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
		COMPONENT.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
		BUGID.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
		COMMENTID.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
		CREATIONTIME.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
		CREATOR.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
		CONTENTCLASS.setOwningType("org.ossmeter.metricprovider.trans.bugheadermetadata.model.CommentData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer PRODUCT = new StringQueryProducer("product"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer COMMENTID = new StringQueryProducer("commentId"); 
	public static StringQueryProducer CREATIONTIME = new StringQueryProducer("creationTime"); 
	public static StringQueryProducer CREATOR = new StringQueryProducer("creator"); 
	public static StringQueryProducer CONTENTCLASS = new StringQueryProducer("contentClass"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public CommentData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getProduct() {
		return parseString(dbObject.get("product")+"", "");
	}
	
	public CommentData setProduct(String product) {
		dbObject.put("product", product);
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public CommentData setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public CommentData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getCommentId() {
		return parseString(dbObject.get("commentId")+"", "");
	}
	
	public CommentData setCommentId(String commentId) {
		dbObject.put("commentId", commentId);
		notifyChanged();
		return this;
	}
	public String getCreationTime() {
		return parseString(dbObject.get("creationTime")+"", "");
	}
	
	public CommentData setCreationTime(String creationTime) {
		dbObject.put("creationTime", creationTime);
		notifyChanged();
		return this;
	}
	public String getCreator() {
		return parseString(dbObject.get("creator")+"", "");
	}
	
	public CommentData setCreator(String creator) {
		dbObject.put("creator", creator);
		notifyChanged();
		return this;
	}
	public String getContentClass() {
		return parseString(dbObject.get("contentClass")+"", "");
	}
	
	public CommentData setContentClass(String contentClass) {
		dbObject.put("contentClass", contentClass);
		notifyChanged();
		return this;
	}
	
	
	
	
}