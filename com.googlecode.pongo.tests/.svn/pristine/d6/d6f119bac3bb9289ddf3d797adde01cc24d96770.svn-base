package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ActivityFact extends Pongo {
	
	
	
	public ActivityFact() { 
		super();
		MONTH.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		ADDEDCODE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		REMOVEDCODE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		ADDEDCOMMENTS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		REMOVEDCOMMENTS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		ADDEDBLANKS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		REMOVEDBLANKS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		COMMITS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
		CONTRIBUTORS.setOwningType("com.googlecode.pongo.tests.ossmeter.model.ActivityFact");
	}
	
	public static NumericalQueryProducer MONTH = new NumericalQueryProducer("month");
	public static NumericalQueryProducer ADDEDCODE = new NumericalQueryProducer("addedCode");
	public static NumericalQueryProducer REMOVEDCODE = new NumericalQueryProducer("removedCode");
	public static NumericalQueryProducer ADDEDCOMMENTS = new NumericalQueryProducer("addedComments");
	public static NumericalQueryProducer REMOVEDCOMMENTS = new NumericalQueryProducer("removedComments");
	public static NumericalQueryProducer ADDEDBLANKS = new NumericalQueryProducer("addedBlanks");
	public static NumericalQueryProducer REMOVEDBLANKS = new NumericalQueryProducer("removedBlanks");
	public static NumericalQueryProducer COMMITS = new NumericalQueryProducer("commits");
	public static NumericalQueryProducer CONTRIBUTORS = new NumericalQueryProducer("contributors");
	
	
	public int getMonth() {
		return parseInteger(dbObject.get("month")+"", 0);
	}
	
	public ActivityFact setMonth(int month) {
		dbObject.put("month", month);
		notifyChanged();
		return this;
	}
	public int getAddedCode() {
		return parseInteger(dbObject.get("addedCode")+"", 0);
	}
	
	public ActivityFact setAddedCode(int addedCode) {
		dbObject.put("addedCode", addedCode);
		notifyChanged();
		return this;
	}
	public int getRemovedCode() {
		return parseInteger(dbObject.get("removedCode")+"", 0);
	}
	
	public ActivityFact setRemovedCode(int removedCode) {
		dbObject.put("removedCode", removedCode);
		notifyChanged();
		return this;
	}
	public int getAddedComments() {
		return parseInteger(dbObject.get("addedComments")+"", 0);
	}
	
	public ActivityFact setAddedComments(int addedComments) {
		dbObject.put("addedComments", addedComments);
		notifyChanged();
		return this;
	}
	public int getRemovedComments() {
		return parseInteger(dbObject.get("removedComments")+"", 0);
	}
	
	public ActivityFact setRemovedComments(int removedComments) {
		dbObject.put("removedComments", removedComments);
		notifyChanged();
		return this;
	}
	public int getAddedBlanks() {
		return parseInteger(dbObject.get("addedBlanks")+"", 0);
	}
	
	public ActivityFact setAddedBlanks(int addedBlanks) {
		dbObject.put("addedBlanks", addedBlanks);
		notifyChanged();
		return this;
	}
	public int getRemovedBlanks() {
		return parseInteger(dbObject.get("removedBlanks")+"", 0);
	}
	
	public ActivityFact setRemovedBlanks(int removedBlanks) {
		dbObject.put("removedBlanks", removedBlanks);
		notifyChanged();
		return this;
	}
	public int getCommits() {
		return parseInteger(dbObject.get("commits")+"", 0);
	}
	
	public ActivityFact setCommits(int commits) {
		dbObject.put("commits", commits);
		notifyChanged();
		return this;
	}
	public int getContributors() {
		return parseInteger(dbObject.get("contributors")+"", 0);
	}
	
	public ActivityFact setContributors(int contributors) {
		dbObject.put("contributors", contributors);
		notifyChanged();
		return this;
	}
	
	
	
	
}