package org.ossmeter.factoid.bugs.responsetime;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.responsetime.ResponseTimeHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.bugs.responsetime.model.BugsResponseTimeHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.severityresponsetime.model.BugsSeverityResponseTimeHistoricMetric;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugsChannelResponseTimeFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelResponseTime";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Channel Response Time";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugsResponseTimeHistoricMetric.class.getCanonicalName(),
							 BugsSeverityResponseTimeHistoricMetric.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName("Bug Channel Response Time Factoid");

		ResponseTimeHistoricMetricProvider responseTimeProvider = new ResponseTimeHistoricMetricProvider();
//		SeverityResponseTimeHistoricMetricProvider severityResponseTimeProvider = new SeverityResponseTimeHistoricMetricProvider();
		
		int eightHoursMilliSeconds = 8 * 60 * 60 * 1000, 
			dayMilliSeconds = 3 * eightHoursMilliSeconds,
			weekMilliSeconds = 7 * dayMilliSeconds;
		
		Date end = new Date();
		Date start = (new Date()).addDays(-365);
		List<Pongo> responseTimeList = responseTimeProvider.getHistoricalMeasurements(context, project, start, end);
//					severityResponseTimeList = severityResponseTimeProvider.getHistoricalMeasurements(context, project, start, end);
		
		long cumulativeAvgResponseTime = 0,
			 yearlyAvgResponseTime = 0;

		if ( responseTimeList.size() > 0 ) {
			BugsResponseTimeHistoricMetric responseTimeMetric = 
					(BugsResponseTimeHistoricMetric) responseTimeList.get(responseTimeList.size() - 1);
			cumulativeAvgResponseTime = responseTimeMetric.getCumulativeAvgResponseTime();
			yearlyAvgResponseTime = getYearlyAvgResponseTime(responseTimeList);
		}

		if ( ( yearlyAvgResponseTime > 0 ) && ( yearlyAvgResponseTime < eightHoursMilliSeconds ) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( ( yearlyAvgResponseTime > 0 ) && ( yearlyAvgResponseTime < dayMilliSeconds ) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( ( yearlyAvgResponseTime > 0 ) && ( yearlyAvgResponseTime < weekMilliSeconds ) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("Considering the whole duration of the project, " +
							"requests receive a first response ");
		if ( cumulativeAvgResponseTime < eightHoursMilliSeconds ) {
			stringBuffer.append("very");
		} else if ( cumulativeAvgResponseTime < dayMilliSeconds ) {
			stringBuffer.append("");
		} else if ( cumulativeAvgResponseTime < weekMilliSeconds ) {
			stringBuffer.append("fairly");
		} else
			stringBuffer.append("not so");
		stringBuffer.append(" quickly.\n");

		stringBuffer.append("Lately, requests receive a first response ");
		if ( yearlyAvgResponseTime < eightHoursMilliSeconds ) {
			stringBuffer.append("very");
		} else if ( yearlyAvgResponseTime < dayMilliSeconds ) {
			stringBuffer.append("");
		} else if ( yearlyAvgResponseTime < weekMilliSeconds ) {
			stringBuffer.append("fairly");
		} else
			stringBuffer.append("not so");
		stringBuffer.append(" quickly.\n");
		
		stringBuffer.append("Response speed is lately ");
		if ( Math.abs(cumulativeAvgResponseTime-yearlyAvgResponseTime) < eightHoursMilliSeconds )
			stringBuffer.append("approximately equal");
		else if ( cumulativeAvgResponseTime > yearlyAvgResponseTime )
			stringBuffer.append("better");
		else 
			stringBuffer.append("worse");
		stringBuffer.append(" than the overall average for the project.\n");

		factoid.setFactoid(stringBuffer.toString());

	}
	
	private long getYearlyAvgResponseTime(List<Pongo> responseTimeList) {
		long totalResponseTime = 0;
		int totalBugsConsidered = 0;
		for (Pongo pongo: responseTimeList) {
			BugsResponseTimeHistoricMetric responseTimePongo = (BugsResponseTimeHistoricMetric) pongo;
			totalBugsConsidered += responseTimePongo.getBugsConsidered();
			totalResponseTime += responseTimePongo.getAvgResponseTime();
		}
		return totalResponseTime / totalBugsConsidered;
	}

}
