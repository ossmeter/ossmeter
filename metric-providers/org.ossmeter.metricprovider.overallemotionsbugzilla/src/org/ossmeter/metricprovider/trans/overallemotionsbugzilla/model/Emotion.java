package org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Emotion extends Pongo {
	
	
	
	public Emotion() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.Emotion");
		PRODUCT.setOwningType("org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.Emotion");
		COMPONENT.setOwningType("org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.Emotion");
		EMOTIONLABEL.setOwningType("org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.Emotion");
		NUMBEROFCOMMENTS.setOwningType("org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.Emotion");
		PERCENTAGE.setOwningType("org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.Emotion");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer PRODUCT = new StringQueryProducer("product"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static StringQueryProducer EMOTIONLABEL = new StringQueryProducer("emotionLabel"); 
	public static NumericalQueryProducer NUMBEROFCOMMENTS = new NumericalQueryProducer("numberOfComments");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public Emotion setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getProduct() {
		return parseString(dbObject.get("product")+"", "");
	}
	
	public Emotion setProduct(String product) {
		dbObject.put("product", product);
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public Emotion setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	public String getEmotionLabel() {
		return parseString(dbObject.get("emotionLabel")+"", "");
	}
	
	public Emotion setEmotionLabel(String emotionLabel) {
		dbObject.put("emotionLabel", emotionLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public Emotion setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public Emotion setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}