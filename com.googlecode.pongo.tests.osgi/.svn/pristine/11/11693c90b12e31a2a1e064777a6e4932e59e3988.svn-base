package com.googlecode.pongo.tests.osgi;

import org.junit.Test;

import static org.junit.Assert.*;

import com.googlecode.pongo.runtime.ClasspathPongoFactoryContributor;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.PongoFactoryContributor;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;

public class OsgiTests {
	
	protected String pongoClass = "com.googlecode.pongo.tests.osgi.model.Osgi";
	
	@Test
	public void testOsgiPongoFactoryContributor() throws Exception {
		OsgiPongoFactoryContributor contributor = new OsgiPongoFactoryContributor();
		assertTrue(contributor.canCreate(pongoClass));
		contributor.create(pongoClass);
	}
	
}
