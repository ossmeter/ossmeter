package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class SvnRepository extends org.ossmeter.repository.model.VcsRepository {
	
	
	
	public SvnRepository() { 
		super();
	}
	
	public String getBrowse() {
		return parseString(dbObject.get("browse")+"", "");
	}
	
	public SvnRepository setBrowse(String browse) {
		dbObject.put("browse", browse + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}