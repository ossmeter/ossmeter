package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class Donation extends Pongo {
	
	protected List<String> charities = null;
	
	
	public Donation() { 
		super();
		dbObject.put("charities", new BasicDBList());
	}
	
	public String getComment() {
		return parseString(dbObject.get("comment")+"", "");
	}
	
	public Donation setComment(String comment) {
		dbObject.put("comment", comment + "");
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
		dbObject.put("status", status + "");
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