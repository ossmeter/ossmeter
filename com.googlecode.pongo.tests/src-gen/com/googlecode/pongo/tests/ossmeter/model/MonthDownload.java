package com.googlecode.pongo.tests.ossmeter.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class MonthDownload extends Pongo {
	
	
	
	public MonthDownload() { 
		super();
		MONTH.setOwningType("com.googlecode.pongo.tests.ossmeter.model.MonthDownload");
		YEAR.setOwningType("com.googlecode.pongo.tests.ossmeter.model.MonthDownload");
		VALUE.setOwningType("com.googlecode.pongo.tests.ossmeter.model.MonthDownload");
	}
	
	public static NumericalQueryProducer MONTH = new NumericalQueryProducer("month");
	public static NumericalQueryProducer YEAR = new NumericalQueryProducer("year");
	public static NumericalQueryProducer VALUE = new NumericalQueryProducer("value");
	
	
	public int getMonth() {
		return parseInteger(dbObject.get("month")+"", 0);
	}
	
	public MonthDownload setMonth(int month) {
		dbObject.put("month", month);
		notifyChanged();
		return this;
	}
	public int getYear() {
		return parseInteger(dbObject.get("year")+"", 0);
	}
	
	public MonthDownload setYear(int year) {
		dbObject.put("year", year);
		notifyChanged();
		return this;
	}
	public int getValue() {
		return parseInteger(dbObject.get("value")+"", 0);
	}
	
	public MonthDownload setValue(int value) {
		dbObject.put("value", value);
		notifyChanged();
		return this;
	}
	
	
	
	
}