package org.ossmeter.repository.model.eclipseforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Release extends Pongo {
	
	
	
	public Release() { 
		super();
		TYPE.setOwningType("org.ossmeter.repository.model.eclipseforge.Release");
		DATE.setOwningType("org.ossmeter.repository.model.eclipseforge.Release");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public ReleaseType getType() {
		ReleaseType type = null;
		try {
			type = ReleaseType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public Release setType(ReleaseType type) {
		dbObject.put("type", type+"");
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public Release setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}