package com.googlecode.pongo.tests.zoo;

import com.googlecode.pongo.tests.zoo.model.Animal;
import com.googlecode.pongo.tests.zoo.model.Mammal;
import com.googlecode.pongo.tests.zoo.model.Monkey;
import com.googlecode.pongo.tests.zoo.model.Shark;
import com.googlecode.pongo.tests.zoo.model.Tiger;
import com.googlecode.pongo.tests.zoo.model.Zebra;
import com.googlecode.pongo.tests.zoo.model.Zoo;
import com.mongodb.Mongo;

public class ZooExample {

	public static void main(String[] a) throws Exception {
		// Initialise MongoDB
		Mongo mongo = new Mongo();
		mongo.dropDatabase("zoo"); // Ensure clean run
		
		// Create our zoo database
		Zoo zoo = new Zoo(mongo.getDB("zoo"));
		
		// Add some animals
		Monkey monkey= new Monkey();
		monkey.setName("Steve");
		monkey.setWeight(5);
		monkey.setOrigin("Africa");
		zoo.getAnimals().add(monkey);
		
		Monkey monkey2= new Monkey();
		monkey2.setName("John");
		monkey2.setWeight(4);
		monkey2.setOrigin("Asia");
		zoo.getAnimals().add(monkey2);
		
		Tiger tiger = new Tiger();
		tiger.setName("Steve");
		tiger.setWeight(15);
		tiger.setOrigin("India");
		zoo.getAnimals().add(tiger);
		
		Zebra zebra = new Zebra();
		zebra.setName("George");
		zebra.setWeight(12);
		zebra.setOrigin("Africa");
		zoo.getAnimals().add(zebra);
		
		Shark shark = new Shark();
		shark.setName("Steve");
		shark.setWeight(20);
		shark.setOrigin("Pacific Ocean");
		zoo.getAnimals().add(shark);
		
		zoo.sync();
		
		// Q1: Find all animals from Africa
		zoo.getAnimals().find(Animal.ORIGIN.eq("Africa")); // Returns two
		
		// Q2: Find all animals from Africa who are heavier than 10
		zoo.getAnimals().find(Animal.ORIGIN.eq("Africa"), Animal.WEIGHT.greaterThan(10)); // Returns one
		
		// Q3: Find all animals named Steve
		zoo.getAnimals().find(Animal.NAME.eq("Steve")); // Returns three
		
		// Q4: Find all mammals named Steve
		zoo.getAnimals().find(Mammal.NAME.eq("Steve")); // Returns two 
		
		// Q5: Find all mammals named Steve who are heavier than 10
		zoo.getAnimals().find(Mammal.NAME.eq("Steve"), Mammal.WEIGHT.greaterThan(10)); // Returns one
		
		// Q6: Find all mammals named Steve, ignoring monkey's who weigh 5 or less 
		zoo.getAnimals().find(Mammal.NAME.eq("Steve"), Mammal.WEIGHT.greaterThanOrEqualTo(5)); // Returns two
		
		// Q7: Find all mammals named Steve, who weigh more than 5, and are from Africa 
		zoo.getAnimals().find(Mammal.NAME.eq("Steve"), Mammal.WEIGHT.greaterThanOrEqualTo(5), Mammal.ORIGIN.eq("Africa")); // Returns one
	}
	
}
