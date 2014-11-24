package com.googlecode.pongo.tests.inheritance;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.pongo.tests.inheritance.model.ChildOne;
import com.googlecode.pongo.tests.inheritance.model.ChildTwo;
import com.googlecode.pongo.tests.inheritance.model.Grandchild;
import com.googlecode.pongo.tests.inheritance.model.InheritanceDB;
import com.googlecode.pongo.tests.inheritance.model.Parent;
import com.googlecode.pongo.tests.inheritance.model.ParentCollection;
import com.mongodb.Mongo;

public class FindByTests {

	Mongo mongo = null;
	ParentCollection classes = null;
	InheritanceDB db = null;
	
	@Before 
	public void setup() throws Exception {
		if (mongo == null) {
			mongo = new Mongo();
		}
		mongo.dropDatabase("inheritancedb");
		db = new InheritanceDB(mongo.getDB("inheritancedb"));
		db.setClearPongoCacheOnSync(true);
		
		classes = db.getClasses();
	}
	
	
	@Test
	public void testFindBy() {
		
		// Create our hierarchy
		ChildOne c11 = new ChildOne();
		c11.setName("John");
		c11.setAge(20);
		classes.add(c11);
		
		ChildOne c12 = new ChildOne();
		c12.setName("James");
		c12.setAge(30);
		classes.add(c12);
		
		ChildTwo c21 = new ChildTwo();
		c21.setName("John");
		c21.setAge(50);
		classes.add(c21);
		
		Grandchild c31 = new Grandchild();
		c31.setName("Tony");
		c31.setAge(1);
		classes.add(c31);
		
		db.sync();
		classes = db.getClasses();
	
		// Now query
		assertEquals(1, sizeOfIterable(classes.findChildOnesByName("John")));
		assertEquals(1, sizeOfIterable(classes.findChildOnesByName("James")));
		assertEquals(1, sizeOfIterable(classes.findChildTwosByName("John")));
		assertEquals(2, sizeOfIterable(classes.findByName("John")));
		// FIXME: cannot do subsubclasses: I.e. classes.findGrandchildByName(..)

		assertEquals(2, sizeOfIterable(classes.find(Parent.NAME.eq("John"))));
		assertEquals(1, sizeOfIterable(classes.find(ChildOne.NAME.eq("John"))));
		assertEquals(1, sizeOfIterable(classes.find(ChildTwo.NAME.eq("John"))));
		assertEquals(0, sizeOfIterable(classes.find(Grandchild.NAME.eq("John"))));
		
		assertEquals(1, sizeOfIterable(classes.find(Parent.NAME.eq("Tony"))));
		assertEquals(0, sizeOfIterable(classes.find(ChildOne.NAME.eq("Tony"))));
		assertEquals(1, sizeOfIterable(classes.find(ChildTwo.NAME.eq("Tony"))));
		assertEquals(1, sizeOfIterable(classes.find(Grandchild.NAME.eq("Tony"))));
		
		assertEquals(3, sizeOfIterable(classes.find(Parent.NAME.regex("J.*"))));
		assertEquals(2, sizeOfIterable(classes.find(ChildOne.NAME.regex("J.*"))));
		assertEquals(1, sizeOfIterable(classes.find(ChildTwo.NAME.regex("J.*"))));
		assertEquals(0, sizeOfIterable(classes.find(Grandchild.NAME.regex("J.*"))));
		
		assertEquals(3, sizeOfIterable(classes.find(Parent.NAME.in("John", "James"))));
		assertEquals(2, sizeOfIterable(classes.find(ChildOne.NAME.in("John", "James"))));
		assertEquals(1, sizeOfIterable(classes.find(ChildTwo.NAME.in("John", "James"))));
		assertEquals(0, sizeOfIterable(classes.find(Grandchild.NAME.in("John", "James"))));
		
		assertEquals(1, sizeOfIterable(classes.find(Parent.NAME.nin("John", "Tony"))));
		assertEquals(1, sizeOfIterable(classes.find(ChildOne.NAME.nin("John", "Tony"))));
		assertEquals(0, sizeOfIterable(classes.find(ChildTwo.NAME.nin("John", "Tony"))));
		assertEquals(0, sizeOfIterable(classes.find(Grandchild.NAME.nin("John", "Tony"))));
		
		assertEquals(2, sizeOfIterable(classes.find(Parent.AGE.lessThan(25))));
		assertEquals(1, sizeOfIterable(classes.find(ChildOne.AGE.lessThan(25))));
		assertEquals(1, sizeOfIterable(classes.find(ChildTwo.AGE.lessThan(25))));
		assertEquals(1, sizeOfIterable(classes.find(Grandchild.AGE.lessThan(25))));

		assertEquals(2, sizeOfIterable(classes.find(Parent.AGE.greaterThan(25))));
		assertEquals(1, sizeOfIterable(classes.find(ChildOne.AGE.greaterThan(25))));
		assertEquals(1, sizeOfIterable(classes.find(ChildTwo.AGE.greaterThan(25))));
		assertEquals(0, sizeOfIterable(classes.find(Grandchild.AGE.greaterThan(25))));
		
		assertEquals(1, sizeOfIterable(classes.find(Parent.AGE.lessThan(35).greaterThan(25))));
		assertEquals(1, sizeOfIterable(classes.find(ChildOne.AGE.lessThan(35).greaterThan(25))));
		assertEquals(0, sizeOfIterable(classes.find(ChildTwo.AGE.lessThan(35).greaterThan(25))));
		assertEquals(0, sizeOfIterable(classes.find(Grandchild.AGE.lessThan(35).greaterThan(25))));
		
		assertEquals(2, sizeOfIterable(classes.find(Parent.AGE.greaterThan(25), Parent.NAME.in("John", "James"))));
	} 
	
	protected int sizeOfIterable(Iterable iterable) {
		int count = 0;
		Iterator it = iterable.iterator();
		while (it.hasNext()) {
			count++;
			it.next();
		}
		return count;
	}

}
