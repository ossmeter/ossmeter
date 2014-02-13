package org.ossmeter.repository.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ImportData extends Pongo {
	
	
	
	public ImportData() { 
		super();
		LASTIMPORTEDPROJECT.setOwningType("org.ossmeter.repository.model.ImportData");
	}
	
	public static StringQueryProducer LASTIMPORTEDPROJECT = new StringQueryProducer("lastImportedProject"); 
	
	
	public String getLastImportedProject() {
		return parseString(dbObject.get("lastImportedProject")+"", "");
	}
	
	public ImportData setLastImportedProject(String lastImportedProject) {
		dbObject.put("lastImportedProject", lastImportedProject);
		notifyChanged();
		return this;
	}
	
	
	
	
}