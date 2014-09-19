package org.ossmeter.metricprovider.trans.numberofbugzillapatches.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugzillaData extends Pongo {
	
	
	
	public BugzillaData() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.numberofbugzillapatches.model.BugzillaData");
		PRODUCT.setOwningType("org.ossmeter.metricprovider.trans.numberofbugzillapatches.model.BugzillaData");
		COMPONENT.setOwningType("org.ossmeter.metricprovider.trans.numberofbugzillapatches.model.BugzillaData");
		NUMBEROFPATCHES.setOwningType("org.ossmeter.metricprovider.trans.numberofbugzillapatches.model.BugzillaData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer PRODUCT = new StringQueryProducer("product"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static NumericalQueryProducer NUMBEROFPATCHES = new NumericalQueryProducer("numberOfPatches");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public BugzillaData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getProduct() {
		return parseString(dbObject.get("product")+"", "");
	}
	
	public BugzillaData setProduct(String product) {
		dbObject.put("product", product);
		notifyChanged();
		return this;
	}
	public String getComponent() {
		return parseString(dbObject.get("component")+"", "");
	}
	
	public BugzillaData setComponent(String component) {
		dbObject.put("component", component);
		notifyChanged();
		return this;
	}
	public int getNumberOfPatches() {
		return parseInteger(dbObject.get("numberOfPatches")+"", 0);
	}
	
	public BugzillaData setNumberOfPatches(int numberOfPatches) {
		dbObject.put("numberOfPatches", numberOfPatches);
		notifyChanged();
		return this;
	}
	
	
	
	
}