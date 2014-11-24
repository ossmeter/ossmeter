package com.googlecode.pongo.tests.blog.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;


public class Stats extends Pongo {
	
	
	
	public Stats() { 
		super();
	}
	
	public int getPageloads() {
		return parseInteger(dbObject.get("pageloads")+"", 0);
	}
	
	public Stats setPageloads(int pageloads) {
		dbObject.put("pageloads", pageloads + "");
		notifyChanged();
		return this;
	}
	public int getVisitors() {
		return parseInteger(dbObject.get("visitors")+"", 0);
	}
	
	public Stats setVisitors(int visitors) {
		dbObject.put("visitors", visitors + "");
		notifyChanged();
		return this;
	}
	
	
	
	
}