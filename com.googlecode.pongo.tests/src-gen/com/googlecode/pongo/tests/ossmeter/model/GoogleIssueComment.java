package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleIssueComment extends Pongo {
	
	
	
	public GoogleIssueComment() { 
		super();
		TEXT.setOwningType("com.googlecode.pongo.tests.ossmeter.model.GoogleIssueComment");
	}
	
	public static StringQueryProducer TEXT = new StringQueryProducer("text"); 
	
	
	public String getText() {
		return parseString(dbObject.get("text")+"", "");
	}
	
	public GoogleIssueComment setText(String text) {
		dbObject.put("text", text);
		notifyChanged();
		return this;
	}
	
	
	
	
}