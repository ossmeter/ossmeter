package org.ossmeter.repository.model.eclipseforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class EclipseNewsGroup extends org.ossmeter.repository.model.NntpNewsGroup {
	
	
	
	public EclipseNewsGroup() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.eclipseforge.NntpNewsGroup");
		TYPE.setOwningType("org.ossmeter.repository.model.eclipseforge.EclipseNewsGroup");
	}
	
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	
	
	public NewsGroupType getType() {
		NewsGroupType type = null;
		try {
			type = NewsGroupType.valueOf(dbObject.get("type")+"");
		}
		catch (Exception ex) {}
		return type;
	}
	
	public EclipseNewsGroup setType(NewsGroupType type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	
	
	
	
}