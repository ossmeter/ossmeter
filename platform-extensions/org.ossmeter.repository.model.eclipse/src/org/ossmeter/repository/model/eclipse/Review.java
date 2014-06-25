package org.ossmeter.repository.model.eclipse;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


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
	
	
	public ReviewType getType() {
		ReviewType type = null;
		try {
			type = ReviewType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public Review setType(ReviewType type) {
		dbObject.put("type", type.toString());
		notifyChanged();
		return this;
	}
	public ReviewState getState() {
		ReviewState state = null;
		try {
			state = ReviewState.valueOf(dbObject.get("state")+"");
		}
		catch (Exception ex) {}
		return state;
	}
	
	public Review setState(ReviewState state) {
		dbObject.put("state", state.toString());
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