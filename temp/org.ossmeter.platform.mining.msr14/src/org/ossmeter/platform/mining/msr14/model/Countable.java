package org.ossmeter.platform.mining.msr14.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Countable extends Pongo {
	
	
	
	public Countable() { 
		super();
		NUMBER.setOwningType("org.ossmeter.platform.mining.msr14.model.Countable");
		FREQUENCY.setOwningType("org.ossmeter.platform.mining.msr14.model.Countable");
	}
	
	public static NumericalQueryProducer NUMBER = new NumericalQueryProducer("number");
	public static NumericalQueryProducer FREQUENCY = new NumericalQueryProducer("frequency");
	
	
	public int getNumber() {
		return parseInteger(dbObject.get("number")+"", 0);
	}
	
	public Countable setNumber(int number) {
		dbObject.put("number", number);
		notifyChanged();
		return this;
	}
	public int getFrequency() {
		return parseInteger(dbObject.get("frequency")+"", 0);
	}
	
	public Countable setFrequency(int frequency) {
		dbObject.put("frequency", frequency);
		notifyChanged();
		return this;
	}
	
	
	
	
}