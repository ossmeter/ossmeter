package org.ossmeter.platform.osgi.executors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.osgi.ErrorThrowingTransientMetricProvider;
import org.ossmeter.platform.osgi.ManualRegistrationMetricProviderManager;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.osgi.OsgiPongoFactoryContributor;
import com.mongodb.Mongo;

public class MetricExecutorTest {
	
	protected static Platform platform;
	protected static Mongo mongo;
	
	@BeforeClass
	public static void setup() throws Exception {
		PongoFactory.getInstance().getContributors().add(new OsgiPongoFactoryContributor());
		
		mongo = new Mongo();
		platform = new Platform(mongo);
		
		Project project = new Project();
		project.setName("debug-project");
		String startDate = new Date().addDays(-2).toString();
		project.setLastExecuted(startDate);
		
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
	}
	
	@AfterClass
	public static void closedown() {
		
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName("debug-project");
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().remove(project);
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().sync();
		
		mongo.close();
	}
	
	@Test
	public void testLastExecutedDate() throws Exception {
		
		ManualRegistrationMetricProviderManager metricProviderManager = new ManualRegistrationMetricProviderManager();
		metricProviderManager.addMetricProvider(new ErrorThrowingTransientMetricProvider());
		platform.setMetricProviderManager(metricProviderManager);
		
		assertEquals(1,platform.getMetricProviderManager().getMetricProviders().size());
		
		Project project = platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByName("debug-project");
		String startDate = project.getLastExecuted();

		ProjectExecutor pe = new ProjectExecutor(platform, project);
		pe.run();
		
		assertTrue(project.getInErrorState());
		assertEquals(startDate, project.getLastExecuted());
		
		// 
	}
	
	
}
