package org.ossmeter.platform.client.api.tests;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.client.api.ProjectImporter;
import org.ossmeter.repository.model.Project;

import com.mongodb.Mongo;

public class TestProjectImporter extends TestAbstractResource {

	static Mongo mongo;
	static Platform platform;
	
	@BeforeClass
	public static void setup() throws Exception {
		mongo = new Mongo();
		platform = new Platform(mongo);
	}
	
	@AfterClass
	public static void close() throws Exception {
		mongo.close();
	}
	
	@Test
	public void testEclipse() {
		ProjectImporter importer = new ProjectImporter();
		Project p = importer.importProject("https://projects.eclipse.org/projects/modeling.epsilon");
		
		assertNotNull(p);
		assertEquals("Epsilon", p.getName());
		
		p = importer.importProject("https://projects.eclipse.org/projects/modeling.epsiloon");
		assertNull(p);
	}
	
	@Test
	public void testGitHub() {
		ProjectImporter importer = new ProjectImporter();
		Project p = importer.importProject("https://github.com/jrwilliams/gif-hook");
		
		assertNotNull(p);
		assertEquals("gif-hook", p.getName());
		
		p = importer.importProject("https://github.com/jrwilliams/");
		assertNull(p);
	
	}
	
	@Test
	public void testSourceForge() {

	
	}
	
	

}
