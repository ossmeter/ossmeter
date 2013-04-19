package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class GoogleIssueComment extends Pongo {
	
	
	
	public GoogleIssueComment() { 
		super();
	}
	
	public String getText() {
		return parseString(dbObject.get("text")+"", "");
	}
	
	public GoogleIssueComment setText(String text) {
		dbObject.put("text", text + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}