package org.ossmeter.metricprovider.historic.bugs.emotions.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Dimension extends Pongo {
	
	
	
	public Dimension() { 
		super();
		BUGTRACKERID.setOwningType("org.ossmeter.metricprovider.historic.bugs.emotions.model.Dimension");
		EMOTIONLABEL.setOwningType("org.ossmeter.metricprovider.historic.bugs.emotions.model.Dimension");
		NUMBEROFCOMMENTS.setOwningType("org.ossmeter.metricprovider.historic.bugs.emotions.model.Dimension");
		CUMULATIVENUMBEROFCOMMENTS.setOwningType("org.ossmeter.metricprovider.historic.bugs.emotions.model.Dimension");
		PERCENTAGE.setOwningType("org.ossmeter.metricprovider.historic.bugs.emotions.model.Dimension");
		CUMULATIVEPERCENTAGE.setOwningType("org.ossmeter.metricprovider.historic.bugs.emotions.model.Dimension");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer EMOTIONLABEL = new StringQueryProducer("emotionLabel"); 
	public static NumericalQueryProducer NUMBEROFCOMMENTS = new NumericalQueryProducer("numberOfComments");
	public static NumericalQueryProducer CUMULATIVENUMBEROFCOMMENTS = new NumericalQueryProducer("cumulativeNumberOfComments");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	public static NumericalQueryProducer CUMULATIVEPERCENTAGE = new NumericalQueryProducer("cumulativePercentage");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public Dimension setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getEmotionLabel() {
		return parseString(dbObject.get("emotionLabel")+"", "");
	}
	
	public Dimension setEmotionLabel(String emotionLabel) {
		dbObject.put("emotionLabel", emotionLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public Dimension setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfComments() {
		return parseInteger(dbObject.get("cumulativeNumberOfComments")+"", 0);
	}
	
	public Dimension setCumulativeNumberOfComments(int cumulativeNumberOfComments) {
		dbObject.put("cumulativeNumberOfComments", cumulativeNumberOfComments);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public Dimension setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	public float getCumulativePercentage() {
		return parseFloat(dbObject.get("cumulativePercentage")+"", 0.0f);
	}
	
	public Dimension setCumulativePercentage(float cumulativePercentage) {
		dbObject.put("cumulativePercentage", cumulativePercentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}