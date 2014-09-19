package org.ossmeter.metricprovider.trans.numberofnewbugzillabugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugzillaData extends Pongo {
	
	
	
	public BugzillaData() { 
		super();
		URL.setOwningType("org.ossmeter.metricprovider.trans.numberofnewbugzillabugs.model.BugzillaData");
		PRODUCT.setOwningType("org.ossmeter.metricprovider.trans.numberofnewbugzillabugs.model.BugzillaData");
		COMPONENT.setOwningType("org.ossmeter.metricprovider.trans.numberofnewbugzillabugs.model.BugzillaData");
		NUMBEROFBUGS.setOwningType("org.ossmeter.metricprovider.trans.numberofnewbugzillabugs.model.BugzillaData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer PRODUCT = new StringQueryProducer("product"); 
	public static StringQueryProducer COMPONENT = new StringQueryProducer("component"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	
	
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
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugzillaData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}