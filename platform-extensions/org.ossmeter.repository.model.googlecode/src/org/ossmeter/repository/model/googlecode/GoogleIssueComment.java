package org.ossmeter.repository.model.googlecode;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GoogleIssueComment extends Pongo {
	
	
	
	public GoogleIssueComment() { 
		super();
		TEXT.setOwningType("org.ossmeter.repository.model.googlecode.GoogleIssueComment");
		DATE.setOwningType("org.ossmeter.repository.model.googlecode.GoogleIssueComment");
	}
	
	public static StringQueryProducer TEXT = new StringQueryProducer("text"); 
	public static StringQueryProducer DATE = new StringQueryProducer("date"); 
	
	
	public String getText() {
		return parseString(dbObject.get("text")+"", "");
	}
	
	public GoogleIssueComment setText(String text) {
		dbObject.put("text", text);
		notifyChanged();
		return this;
	}
	public String getDate() {
		return parseString(dbObject.get("date")+"", "");
	}
	
	public GoogleIssueComment setDate(String date) {
		dbObject.put("date", date);
		notifyChanged();
		return this;
	}
	
	
	
	
}