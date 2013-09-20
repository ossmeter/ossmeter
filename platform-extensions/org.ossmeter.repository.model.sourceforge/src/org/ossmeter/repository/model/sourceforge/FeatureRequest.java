package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class FeatureRequest extends Request {
	
	
	
	public FeatureRequest() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.Request","org.ossmeter.repository.model.sourceforge.Tracker","org.ossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.ossmeter.repository.model.sourceforge.FeatureRequest");
		STATUS.setOwningType("org.ossmeter.repository.model.sourceforge.FeatureRequest");
		SUMMARY.setOwningType("org.ossmeter.repository.model.sourceforge.FeatureRequest");
		CREATED_AT.setOwningType("org.ossmeter.repository.model.sourceforge.FeatureRequest");
		UPDATED_AT.setOwningType("org.ossmeter.repository.model.sourceforge.FeatureRequest");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	
	
	
	
}