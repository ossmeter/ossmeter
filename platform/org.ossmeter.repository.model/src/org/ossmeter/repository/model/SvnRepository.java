package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class SvnRepository extends VcsRepository {
	
	
	
	public SvnRepository() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.VcsRepository");
		CREATED_AT.setOwningType("org.ossmeter.repository.model.SvnRepository");
		UPDATED_AT.setOwningType("org.ossmeter.repository.model.SvnRepository");
		URL.setOwningType("org.ossmeter.repository.model.SvnRepository");
	}
	
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	
	
	
	
}