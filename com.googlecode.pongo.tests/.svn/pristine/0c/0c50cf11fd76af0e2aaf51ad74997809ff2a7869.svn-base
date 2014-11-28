package com.googlecode.pongo.tests.zoo.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Zoo extends PongoDB {
	
	public Zoo() {}
	
	public Zoo(DB db) {
		setDb(db);
	}
	
	protected AnimalCollection animals = null;
	protected BuildingCollection buildings = null;
	protected StaffCollection staff = null;
	
	
	
	public AnimalCollection getAnimals() {
		return animals;
	}
	
	public BuildingCollection getBuildings() {
		return buildings;
	}
	
	public StaffCollection getStaff() {
		return staff;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		animals = new AnimalCollection(db.getCollection("animals"));
		pongoCollections.add(animals);
		buildings = new BuildingCollection(db.getCollection("buildings"));
		pongoCollections.add(buildings);
		staff = new StaffCollection(db.getCollection("staff"));
		pongoCollections.add(staff);
	}
}