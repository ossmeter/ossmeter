package com.googlecode.pongo.tests.zoo.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class AnimalCollection extends PongoCollection<Animal> {
	
	public AnimalCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<Animal> findById(String id) {
		return new IteratorIterable<Animal>(new PongoCursorIterator<Animal>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Animal> findByName(String q) {
		return new IteratorIterable<Animal>(new PongoCursorIterator<Animal>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Animal findOneByName(String q) {
		Animal animal = (Animal) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (animal != null) {
			animal.setPongoCollection(this);
		}
		return animal;
	}
	
	
	public Iterable<Animal> findMammalsByName(String q) {
		return new IteratorIterable<Animal>(new PongoCursorIterator<Animal>(this, dbCollection.find(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Mammal"))));
	}
	
	public Animal findOneMammalByName(String q) {
		Animal animal = (Animal) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Mammal")));
		if (animal != null) {
			animal.setPongoCollection(this);
		}
		return animal;
	}
	
	
	public Iterable<Animal> findAmphibiansByName(String q) {
		return new IteratorIterable<Animal>(new PongoCursorIterator<Animal>(this, dbCollection.find(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Amphibian"))));
	}
	
	public Animal findOneAmphibianByName(String q) {
		Animal animal = (Animal) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Amphibian")));
		if (animal != null) {
			animal.setPongoCollection(this);
		}
		return animal;
	}
	
	
	public Iterable<Animal> findReptilesByName(String q) {
		return new IteratorIterable<Animal>(new PongoCursorIterator<Animal>(this, dbCollection.find(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Reptile"))));
	}
	
	public Animal findOneReptileByName(String q) {
		Animal animal = (Animal) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Reptile")));
		if (animal != null) {
			animal.setPongoCollection(this);
		}
		return animal;
	}
	
	
	public Iterable<Animal> findBirdsByName(String q) {
		return new IteratorIterable<Animal>(new PongoCursorIterator<Animal>(this, dbCollection.find(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Bird"))));
	}
	
	public Animal findOneBirdByName(String q) {
		Animal animal = (Animal) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Bird")));
		if (animal != null) {
			animal.setPongoCollection(this);
		}
		return animal;
	}
	
	
	public Iterable<Animal> findFishsByName(String q) {
		return new IteratorIterable<Animal>(new PongoCursorIterator<Animal>(this, dbCollection.find(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Fish"))));
	}
	
	public Animal findOneFishByName(String q) {
		Animal animal = (Animal) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "").append("_type", "com.googlecode.pongo.tests.zoo.model.Fish")));
		if (animal != null) {
			animal.setPongoCollection(this);
		}
		return animal;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Animal> iterator() {
		return new PongoCursorIterator<Animal>(this, dbCollection.find());
	}
	
	public void add(Animal animal) {
		super.add(animal);
	}
	
	public void remove(Animal animal) {
		super.remove(animal);
	}
	
}