package org.ossmeter.platform.app.york.util;

import java.util.Random;

import org.ossmeter.metricprovider.generic.numberofactiveusersperday.NumberOfActiveUsersPerDayProvider;
import org.ossmeter.metricprovider.generic.numberofactiveusersperday.model.DailyActiveUsers;
import org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.NumberOfRequestsRepliesPerDayProvider;
import org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.model.DailyNorr;
import org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.NumberOfResolvedClosedBugzillaBugsProvider;
import org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model.DailyNorcb;
import org.ossmeter.metricprovider.generic.overalldailynumberofarticles.OverallDailyNumberOfArticlesProvider;
import org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model.DailyNewsgroupData;
import org.ossmeter.metricprovider.generic.overalldailynumberofarticles.model.DailyNoa;
import org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.OverallDailyNumberOfBugzillaCommentsProvider;
import org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model.DailyNobc;
import org.ossmeter.metricprovider.generic.overalldailynumberofbugzillapatches.OverallDailyNumberOfBugzillaPatchesProvider;
import org.ossmeter.metricprovider.generic.overalldailynumberofbugzillapatches.model.DailyNobp;
import org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.OverallDailyNumberOfNewBugzillaBugsProvider;
import org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model.DailyBugzillaData;
import org.ossmeter.metricprovider.generic.overalldailynumberofnewbugzillabugs.model.DailyNonbb;
import org.ossmeter.metricprovider.rascal.trans.model.IntegerMeasurement;
import org.ossmeter.metricprovider.rascal.trans.model.Measurement;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.Platform;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.MetricProviderType;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * This class is purely for illustration purposes and is not intended for release.
 * @author jimmy
 *
 */
public class SampleDataCreator {
	
	public static void main(String[] args) throws Exception {
		Mongo mongo = new Mongo();
		// Clean up
		mongo.dropDatabase("ossmeter");
		mongo.dropDatabase("Pongo");
		
		Platform platform = new Platform(mongo);

		// Add projects
		Project pongo = ProjectCreationUtil.createProjectSvnNntpBugzilla(
				"Pongo", "pongo", "", //"Pongo is a template-based Java POJO generator for MongoDB. Instead of using low-level DBObjects to interact with your MongoDB database, with Pongo you can define your data/domain model using Emfatic and then generate strongly-typed Java classes you can then use to work with your database at a more convenient level of abstraction. ", 
				"", "", "", "", "", false, "", "");
		
		platform.getProjectRepositoryManager().getProjectRepository().getProjects().add(pongo);
		platform.getProjectRepositoryManager().getProjectRepository().sync();
		
		// Add sample data for projects
		Date start = new Date("20110101");
		Date end = new Date("20131001");
		for (Project project: platform.getProjectRepositoryManager().getProjectRepository().getProjects()) {
			DB db = platform.getMetricsRepository(project).getDb();
			createHistoricRascalData(db, project, "rascal://countLoc", start, end, 1000, 500);
			createHistoricRascalData(db, project, "rascal://countLoc", start, end, 1000, 500);
			createHistoricNewBugs(db, project,  start, end, 4, 3);
			createHistoricClosedBugs(db, project, start, end, 0, 1);
			createHistoricNewComments(db, project, start, end, 0, 5);
			createHistoricBugPatches(db, project, start, end, 0, 2);
			createHistoricDailyArticles(db, project, start, end, 10, 5);
			createHistoricDailyRequestReplies(db, project, start, end, 3, 4, 5, 10);
			createHistoricDailyActiveUsers(db, project, start, end, 2, 3);
		}
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	protected static Random random = new Random();

	public static void createHistoricRascalData(DB db, Project project, String metricName, Date start, Date end, int startValue, int interval) {
		String metricId = "measurements";
		DBCollection collection =  db.getCollection("measurements");
		
		for (Date date : Date.range(start, end)){
			Measurement metricValue = new IntegerMeasurement();
			startValue = nextInt(startValue, interval);
			((IntegerMeasurement) metricValue).setValue(startValue);
			metricValue.setMetric(metricName);
			metricValue.setUri("file:///foo/bar");
			
			collection.save(appendDate(metricValue, date));
		}
		setMetricProviderLastExecuted(project, end, metricId);
	}
	
	public static void createHistoricNewBugs(DB db, Project project, Date start, Date end, int startValue, int interval) {
		String metricId = OverallDailyNumberOfNewBugzillaBugsProvider.IDENTIFIER;
		DBCollection collection = db.getCollection(metricId);
		
		for (Date date : Date.range(start, end)){
			startValue = nextInt(startValue, interval);
			
			DailyBugzillaData bug = new DailyBugzillaData();
			bug.setNumberOfBugs(startValue);
			DailyNonbb d = new DailyNonbb();
			d.getBugzillas().add(bug);
			
			collection.save(appendDate(d, date));
		}
		setMetricProviderLastExecuted(project, end, new OverallDailyNumberOfNewBugzillaBugsProvider().getShortIdentifier());
	}
	
	public static void createHistoricClosedBugs(DB db, Project project, Date start, Date end, int startValue, int interval) {
		String metricId = NumberOfResolvedClosedBugzillaBugsProvider.IDENTIFIER;
		DBCollection collection = db.getCollection(metricId);
		
		for (Date date : Date.range(start, end)){
			startValue = nextInt(startValue, interval);
			
			org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model.DailyBugzillaData bug = new org.ossmeter.metricprovider.generic.numberofresolvedclosedbugzillabugs.model.DailyBugzillaData();
			bug.setNumberOfResolvedClosedBugs(startValue);
			DailyNorcb d = new DailyNorcb();
			d.getBugzillas().add(bug);
			
			collection.save(appendDate(d, date));
		}
		setMetricProviderLastExecuted(project, end, new NumberOfResolvedClosedBugzillaBugsProvider().getShortIdentifier());
	}
	
	public static void createHistoricNewComments(DB db, Project project, Date start, Date end, int startValue, int interval) {
		String metricId = OverallDailyNumberOfBugzillaCommentsProvider.IDENTIFIER;
		DBCollection collection = db.getCollection(metricId);
		
		for (Date date : Date.range(start, end)){
			startValue = nextInt(startValue, interval);
			
			org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model.DailyBugzillaData bug = new org.ossmeter.metricprovider.generic.overalldailynumberofbugzillacomments.model.DailyBugzillaData();
			bug.setNumberOfComments(startValue);
			DailyNobc d = new DailyNobc();
			d.getBugzillas().add(bug);
			
			collection.save(appendDate(d, date));
		}
		setMetricProviderLastExecuted(project, end, new OverallDailyNumberOfBugzillaCommentsProvider().getShortIdentifier());
	}
	
	public static void createHistoricBugPatches(DB db, Project project, Date start, Date end, int startValue, int interval) {
		String metricId = OverallDailyNumberOfBugzillaPatchesProvider.IDENTIFIER;
		DBCollection collection = db.getCollection(metricId);
		
		for (Date date : Date.range(start, end)){
			startValue = nextInt(startValue, interval);
			
			org.ossmeter.metricprovider.generic.overalldailynumberofbugzillapatches.model.DailyBugzillaData bug = new org.ossmeter.metricprovider.generic.overalldailynumberofbugzillapatches.model.DailyBugzillaData();
			bug.setNumberOfPatches(startValue);
			DailyNobp d = new DailyNobp();
			d.getBugzillas().add(bug);
			
			collection.save(appendDate(d, date));
		}
		setMetricProviderLastExecuted(project, end, new OverallDailyNumberOfBugzillaPatchesProvider().getShortIdentifier());
	}

	public static void createHistoricDailyArticles(DB db, Project project, Date start, Date end, int startValue, int interval) {
		String metricId = OverallDailyNumberOfArticlesProvider.IDENTIFIER;
		DBCollection collection = db.getCollection(metricId);
		
		for (Date date : Date.range(start, end)){
			startValue = nextInt(startValue, interval);
			
			DailyNewsgroupData grp = new DailyNewsgroupData();
			grp.setNumberOfArticles(startValue);
			DailyNoa d = new DailyNoa();
			d.getNewsgroups().add(grp);
			
			collection.save(appendDate(d, date));
		}
		setMetricProviderLastExecuted(project, end, new OverallDailyNumberOfArticlesProvider().getShortIdentifier());
	}

	public static void createHistoricDailyRequestReplies(DB db, Project project, Date start, Date end, int startValue1, int interval1, int startValue2, int interval2) {
//		String metricId = NumberOfRequestsRepliesPerDayProvider.IDENTIFIER;
//		DBCollection collection = db.getCollection(metricId);
//		
//		for (Date date : Date.range(start, end)){
//			startValue1 = nextInt(startValue1, interval1);
//			startValue2 = nextInt(startValue2, interval2);
//			
//			org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.model.DailyNewsgroupData grp = new org.ossmeter.metricprovider.generic.numberofrequestsrepliesperday.model.DailyNewsgroupData();
//			grp.setNumberOfRequests(startValue1);
//			grp.setNumberOfReplies(startValue2);
//			DailyNorr d = new DailyNorr();
//			d.getNewsgroups().add(grp);
//			
//			collection.save(appendDate(d, date));
//		}
//		setMetricProviderLastExecuted(project, end, new NumberOfRequestsRepliesPerDayProvider().getShortIdentifier());
	}

	public static void createHistoricDailyActiveUsers(DB db, Project project, Date start, Date end, int startValue, int interval) {
		String metricId = NumberOfActiveUsersPerDayProvider.IDENTIFIER;
		DBCollection collection = db.getCollection(metricId);
		
		for (Date date : Date.range(start, end)){
			startValue = nextInt(startValue, interval);
			
			org.ossmeter.metricprovider.generic.numberofactiveusersperday.model.DailyNewsgroupData grp = new org.ossmeter.metricprovider.generic.numberofactiveusersperday.model.DailyNewsgroupData();
			grp.setNumberOfActiveUsers(startValue);
			DailyActiveUsers d = new DailyActiveUsers();
			d.getNewsgroups().add(grp);
			
			collection.save(appendDate(d, date));
		}
		setMetricProviderLastExecuted(project, end, new NumberOfActiveUsersPerDayProvider().getShortIdentifier());
	}

	protected static DBObject appendDate(Pongo pongo, Date date) {
		DBObject dbObject = pongo.getDbObject();
		dbObject.put("__date", date.toString());
		return dbObject;
	}

	protected static void setMetricProviderLastExecuted(Project project, Date date, String metricId) {
		MetricProvider mp = new MetricProvider();
		mp.setMetricProviderId(metricId);
		mp.setLastExecuted(date.toString());
		mp.setType(MetricProviderType.HISTORIC);
		project.getMetricProviderData().add(mp);		
	}
	
	protected static int nextInt(int start, int interval) {
		return random.nextInt(interval) + start;
	}
}
