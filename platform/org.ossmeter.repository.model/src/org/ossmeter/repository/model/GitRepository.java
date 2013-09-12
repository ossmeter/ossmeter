package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GitRepository extends VcsRepository {
	
	
	
	public GitRepository() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.VcsRepository");
		CREATED_AT.setOwningType("org.ossmeter.repository.model.GitRepository");
		UPDATED_AT.setOwningType("org.ossmeter.repository.model.GitRepository");
		URL.setOwningType("org.ossmeter.repository.model.GitRepository");
	}
	
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	
	
	
	
	
	
}