package org.ossmeter.platform.app.york;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.Bugzilla;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.MetricProviderType;
import org.ossmeter.repository.model.NntpNewsGroup;
import org.ossmeter.repository.model.Person;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.eclipseforge.EclipseForgeProject;
import org.ossmeter.repository.model.eclipseforge.EclipsePlatform;
import org.ossmeter.repository.model.eclipseforge.ProjectStatus;
import org.ossmeter.repository.model.eclipseforge.Release;
import org.ossmeter.repository.model.eclipseforge.ReleaseType;
import org.ossmeter.repository.model.SvnRepository;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class CreateProject {

	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		DB db = mongo.getDB("ossmeter");
		DBCollection projects = db.getCollection("projects");
		
		Platform platform = new Platform(mongo);
		
		if (platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName("foo-bar") ==null) {
			Project project = new Project();
			project.setName("Foo Bar");
			project.setShortName("foo-bar");
			project.setDescription("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
			project.setYear(2012);
			project.setActive(true);
			project.setLastExecuted(new Date().toString());
			
			MetricProvider mp = new MetricProvider();
			mp.setName("loc-historic");
			mp.setMetricProviderId("loc-historic");
			mp.setType(MetricProviderType.HISTORIC);
			mp.setLastExecuted(new Date().toString());
			project.getMetricProviders().add(mp); 
			
			mp = new MetricProvider();
			mp.setName("loc-transient");
			mp.setMetricProviderId("loc-transient");
			mp.setType(MetricProviderType.TRANSIENT); 
			mp.setLastExecuted(new Date().toString());
			project.getMetricProviders().add(mp);
			
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(project);
		}
		
		if (platform.getProjectRepositoryManager().getProjectRepository().getProjects().findOneByShortName("epsilon") ==null) {
			EclipseForgeProject p = new EclipseForgeProject();
			p.setName("Epsilon");
			p.setShortName("epsilon");
			p.setDescription("Epsilon is a family of languages and tools for code generation, model-to-model transformation, model validation, comparison, migration and refactoring that work out-of-the-box with EMF and other types of models.");
			p.setYear(2007);
			p.setActive(true);
			p.setLastExecuted(new Date().toString());
			p.setDescriptionUrl("http://eclipse.org/epsilon/description.url");
			p.setParagraphUrl("http://eclipse.org/epsilon/paragraph.url");
			p.setDownloadsUrl("http://eclipse.org/epsilon/download/");
			p.setUpdatesiteUrl("http://download.eclipse.org/epsilon/updates/");
			p.setHomePage("http://eclipse.org/epsilon/");
			p.setProjectplanUrl("http://eclipse.org/epsilon/projectplan.url");
			p.setStatus(ProjectStatus.toplevel);
			
			Person dk = new Person();
			dk.setName("Dimitrios Kolovos");
			dk.setHomePage("http://www-users.cs.york.ac.uk/~dkolovos/");
//			platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(dk);
			p.getPersons().add(dk);
			p.getCommitters().add(dk);
			p.getLeaders().add(dk);
			
			Person lr = new Person();
			lr.setName("Louis Rose");
			lr.setHomePage("http://www-users.cs.york.ac.uk/~louis/");
//			platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(lr);
			p.getPersons().add(lr);
			p.getCommitters().add(lr);
			
			Person ag = new Person();
			ag.setName("Antonio Garcia Dominguez");
			ag.setHomePage("http://neptuno.uca.es/~agarcia/");
//			platform.getProjectRepositoryManager().getProjectRepository().getPersons().add(ag);
			p.getPersons().add(ag);
			p.getCommitters().add(ag);
			
//			FIXME: Attempted to create DBRef for non-referenceable object org.ossmeter.repository.model.eclipseforge.EclipsePlatform@611c1cdf
//			p.getPlatforms().add((EclipsePlatform)new EclipsePlatform().setName("Ganymede"));
//			p.getPlatforms().add((EclipsePlatform)new EclipsePlatform().setName("Galileo"));
//			p.getPlatforms().add((EclipsePlatform)new EclipsePlatform().setName("Helios"));
//			p.getPlatforms().add((EclipsePlatform)new EclipsePlatform().setName("Indigo"));
//			p.getPlatforms().add((EclipsePlatform)new EclipsePlatform().setName("Juno"));
//			p.getPlatforms().add((EclipsePlatform)new EclipsePlatform().setName("Kepler"));
			
			Release r = new Release();
			r.setType(ReleaseType.completed);
			r.setDate(new Date("20121112").toString());
			p.getReleases().add(r);
			r = new Release();
			r.setType(ReleaseType.completed);
			r.setDate(new Date("20130829").toString());
			p.getReleases().add(r);
			
			SvnRepository svnRepository = new SvnRepository();
			svnRepository.setUrl("http://dev.eclipse.org/svnroot/modeling/org.eclipse.epsilon/");
			p.getVcsRepositories().add(svnRepository); 
			
//			Bugzilla bugzilla = new Bugzilla();
//			bugzilla.setUrl("https://bugs.eclipse.org/bugs/buglist.cgi?product=epsilon");
//			bugzilla.setProduct("Epsilon");
//			bugzilla.setComponent("core");
//			p.getBugTrackingSystems().add(bugzilla);
			
			MetricProvider mp = new MetricProvider();
			mp.setName("loc-historic");
			mp.setMetricProviderId("loc-historic");
			mp.setType(MetricProviderType.HISTORIC);
			mp.setLastExecuted(new Date().toString());
			p.getMetricProviders().add(mp); 
			
			mp = new MetricProvider();
			mp.setName("loc-transient");
			mp.setMetricProviderId("loc-transient");
			mp.setType(MetricProviderType.TRANSIENT); 
			mp.setLastExecuted(new Date().toString());
			p.getMetricProviders().add(mp);
			
			platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(p);
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
//		ProjectCollection pc = new ProjectCollection(projects);
//		pc.add(project);
//		pc.sync();
	
		
	}
}
