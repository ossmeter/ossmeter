package org.ossmeter.metricprovider.trans.contentclassesbugzilla.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class ContentClass extends Pongo {
	
	
	
	public ContentClass() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClass");
		PRODUCT.setOwningType("org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClass");
		COMPONENT.setOwningType("org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClass");
		CLASSLABEL.setOwningType("org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClass");
		NUMBEROFCOMMENTS.setOwningType("org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClass");
		PERCENTAGE.setOwningType("org.ossmeter.metricprovider.trans.contentclassesbugzilla.model.ContentClass");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer PRODUCT = new StringQueryProducer("product"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static StringQueryProducer CLASSLABEL = new StringQueryProducer("classLabel"); 
	public static NumericalQueryProducer NUMBEROFCOMMENTS = new NumericalQueryProducer("numberOfComments");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public ContentClass setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getProduct() {
		return parseString(dbObject.get("product")+"", "");
	}
	
	public ContentClass setProduct(String product) {
		dbObject.put("product", product);
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public ContentClass setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	public String getClassLabel() {
		return parseString(dbObject.get("classLabel")+"", "");
	}
	
	public ContentClass setClassLabel(String classLabel) {
		dbObject.put("classLabel", classLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public ContentClass setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public ContentClass setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}