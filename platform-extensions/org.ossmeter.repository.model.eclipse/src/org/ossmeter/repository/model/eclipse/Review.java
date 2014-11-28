package org.ossmeter.repository.model.eclipse;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Review extends Pongo {
	
	
	
	public Review() { 
		super();
		TYPE.setOwningType("org.ossmeter.repository.model.eclipse.Review");
		STATE.setOwningType("org.ossmeter.repository.model.eclipse.Review");
		ENDDATE.setOwningType("org.ossmeter.repository.model.eclipse.Review");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer STATE = new StringQueryProducer("state"); 
	public static StringQueryProducer ENDDATE = new StringQueryProducer("endDate"); 
	
	
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public Review setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public String getState() {
		return parseString(dbObject.get("state")+"", "");
	}
	
	public Review setState(String state) {
		dbObject.put("state", state);
		notifyChanged();
		return this;
	}
	public String getEndDate() {
		return parseString(dbObject.get("endDate")+"", "");
	}
	
	public Review setEndDate(String endDate) {
		dbObject.put("endDate", endDate);
		notifyChanged();
		return this;
	}
	
	
	
	
}