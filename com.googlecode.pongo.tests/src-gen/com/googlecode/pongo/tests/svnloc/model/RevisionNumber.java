package com.googlecode.pongo.tests.svnloc.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class RevisionNumber extends Pongo {
	
	
	
	public RevisionNumber() { 
		super();
	}
	
	public int getNumber() {
		return parseInteger(dbObject.get("number")+"", 0);
	}
	
	public RevisionNumber setNumber(int number) {
		dbObject.put("number", number + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}