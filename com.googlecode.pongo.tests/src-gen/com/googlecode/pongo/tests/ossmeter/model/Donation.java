package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Donation extends Pongo {
	
	protected List<String> charities = null;
	
	
	public Donation() { 
		super();
		dbObject.put("charities", new BasicDBList());
		COMMENT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Donation");
		STATUS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Donation");
		CHARITIES.setOwningType("com.googlecode.pongo.tests.ossmeter.model.Donation");
	}
	
	public static StringQueryProducer COMMENT = new StringQueryProducer("comment"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static ArrayQueryProducer CHARITIES = new ArrayQueryProducer("charities");
	
	
	public String getComment() {
		return parseString(dbObject.get("comment")+"", "");
	}
	
	public Donation setComment(String comment) {
		dbObject.put("comment", comment);
		notifyChanged();
		return this;
	}
	public DonationStatus getStatus() {
		DonationStatus status = null;
		try {
			status = DonationStatus.valueOf(dbObject.get("status")+"");
		}
		catch (Exception ex) {}
		return status;
	}
	
	public Donation setStatus(DonationStatus status) {
		dbObject.put("status", status.toString());
		notifyChanged();
		return this;
	}
	
	public List<String> getCharities() {
		if (charities == null) {
			charities = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("charities"));
		}
		return charities;
	}
	
	
	
}