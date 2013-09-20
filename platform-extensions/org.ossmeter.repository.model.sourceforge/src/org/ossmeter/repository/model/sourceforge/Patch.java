package org.ossmeter.repository.model.sourceforge;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Patch extends Tracker {
	
	
	
	public Patch() { 
		super();
		super.setSuperTypes("org.ossmeter.repository.model.sourceforge.Tracker","org.ossmeter.repository.model.sourceforge.NamedElement");
		LOCATION.setOwningType("org.ossmeter.repository.model.sourceforge.Patch");
		STATUS.setOwningType("org.ossmeter.repository.model.sourceforge.Patch");
	}
	
	public static StringQueryProducer LOCATION = new StringQueryProducer("location"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	
	
	
	
	
	
}