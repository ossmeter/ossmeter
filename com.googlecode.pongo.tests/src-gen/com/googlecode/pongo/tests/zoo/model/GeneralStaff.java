package com.googlecode.pongo.tests.zoo.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GeneralStaff extends Staff {
	
	protected List<Building> buildings = null;
	
	
	public GeneralStaff() { 
		super();
		dbObject.put("buildings", new BasicDBList());
		super.setSuperTypes("com.googlecode.pongo.tests.zoo.model.Staff");
		NAME.setOwningType("com.googlecode.pongo.tests.zoo.model.GeneralStaff");
		ADDRESS.setOwningType("com.googlecode.pongo.tests.zoo.model.GeneralStaff");
		AGE.setOwningType("com.googlecode.pongo.tests.zoo.model.GeneralStaff");
	}
	
	public static StringQueryProducer NAME = new StringQueryProducer("name"); 
	public static StringQueryProducer ADDRESS = new StringQueryProducer("address"); 
	public static NumericalQueryProducer AGE = new NumericalQueryProducer("age");
	
	
	
	
	public List<Building> getBuildings() {
		if (buildings == null) {
			buildings = new PongoList<Building>(this, "buildings", false);
		}
		return buildings;
	}
	
	
}