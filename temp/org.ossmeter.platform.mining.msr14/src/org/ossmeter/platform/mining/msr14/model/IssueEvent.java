package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class IssueEvent extends Pongo {
	
	
	
	public IssueEvent() { 
		super();
		EVENTKIND.setOwningType("org.ossmeter.platform.mining.msr14.model.IssueEvent");
		COUNT.setOwningType("org.ossmeter.platform.mining.msr14.model.IssueEvent");
	}
	
	public static StringQueryProducer EVENTKIND = new StringQueryProducer("eventKind"); 
	public static NumericalQueryProducer COUNT = new NumericalQueryProducer("count");
	
	
	public IssueEventKind getEventKind() {
		IssueEventKind eventKind = null;
		try {
			eventKind = IssueEventKind.valueOf(dbObject.get("eventKind")+"");
		}
		catch (Exception ex) {}
		return eventKind;
	}
	
	public IssueEvent setEventKind(IssueEventKind eventKind) {
		dbObject.put("eventKind", eventKind.toString());
		notifyChanged();
		return this;
	}
	public int getCount() {
		return parseInteger(dbObject.get("count")+"", 0);
	}
	
	public IssueEvent setCount(int count) {
		dbObject.put("count", count);
		notifyChanged();
		return this;
	}
	
	
	
	
}