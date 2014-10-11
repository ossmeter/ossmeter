package org.ossmeter.platform.importers.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ EclipseImporterTest.class, GitHubImporterTest.class,
		SourceForgeImporterTest.class })
public class AllTests {

	
}
