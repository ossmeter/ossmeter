package org.ossmeter.factoid.bugs.size;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ossmeter.metricprovider.historic.bugs.comments.CommentsHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.bugs.comments.model.BugsCommentsHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.newbugs.NewBugsHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.bugs.newbugs.model.BugsNewBugsHistoricMetric;
import org.ossmeter.metricprovider.historic.bugs.newbugs.model.DailyBugData;
import org.ossmeter.metricprovider.historic.bugs.patches.PatchesHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.bugs.patches.model.BugsPatchesHistoricMetric;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugsChannelSizeFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelSize";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Channel Size";
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
		return Arrays.asList(BugsNewBugsHistoricMetric.class.getCanonicalName(),
							 BugsCommentsHistoricMetric.class.getCanonicalName(),
							 BugsPatchesHistoricMetric.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName("Bug Channel Size Factoid");

		NewBugsHistoricMetricProvider newBugsProvider = new NewBugsHistoricMetricProvider();
		CommentsHistoricMetricProvider commentsProvider = new CommentsHistoricMetricProvider();
		PatchesHistoricMetricProvider patchesProvider = new PatchesHistoricMetricProvider();
		
		Date end = new Date();
		Date start = (new Date()).addDays(-1);
		List<Pongo> newBugsList = newBugsProvider.getHistoricalMeasurements(context, project, start, end),
					commentsList = commentsProvider.getHistoricalMeasurements(context, project, start, end),
					patchesList = patchesProvider.getHistoricalMeasurements(context, project, start, end);
		
//		if ( ( newBugsList == null ) || ( newBugsList.size() == 0 ) )
//			System.err.println("---===COULD NOT RETRIEVED PONGOLIST===---");
//		else 
//			System.err.println("---===RETRIEVED PONGOLIST FOR " + newBugsList.get(0) + " DAYS===---");
			
		Map<String, Integer> trackerBugs = new HashMap<String, Integer>();
		int numberOfBugs = getCumulativeNumberOfBugs(newBugsList, trackerBugs);

		Map<String, Integer> trackerComments = new HashMap<String, Integer>();
		int numberOfComments = getCumulativeNumberOfComments(commentsList, trackerComments);

		Map<String, Integer> trackerPatches = new HashMap<String, Integer>();
		int numberOfPatches = getCumulativeNumberOfPatches(patchesList, trackerPatches);

		int threshold = 1000;
		
		if ( (numberOfBugs > threshold) || (numberOfComments > 10 * threshold) || (numberOfPatches > threshold) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (2 * numberOfBugs > threshold) || (2 * numberOfComments > 10 * threshold) || (2 * numberOfPatches > threshold) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (4 * numberOfBugs > threshold) || (4 * numberOfComments > 10 * threshold) || (4 * numberOfPatches > threshold) ) {
			factoid.setStars(StarRating.TWO);
		} else
			factoid.setStars(StarRating.ONE);
		
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("The project is associated with ");
		stringBuffer.append(project.getBugTrackingSystems().size());
		stringBuffer.append(" bug tracking ");
		
		if (project.getBugTrackingSystems().size()==1)
			stringBuffer.append("system.\n");
		else
			stringBuffer.append("systems.\n");
			
		for (String tracker: sortByKeys(trackerBugs)) {
			
			int bugs = trackerBugs.get(tracker);
			int comments = trackerComments.get(tracker);
			int patches = trackerPatches.get(tracker);

			stringBuffer.append("Bug tracking system ");
			stringBuffer.append(tracker);
			stringBuffer.append(" is of ");
			
			if ( (bugs > threshold) || (comments > 10 * threshold) || (patches > threshold) ) {
				stringBuffer.append("very large");
			} else if ( (2 * bugs > threshold) || (2 * comments > 10 * threshold) || (2 * patches > threshold) ) {
				stringBuffer.append("large");
			} else if ( (4 * bugs > threshold) || (4 * comments > 10 * threshold) || (4 * patches > threshold) ) {
				stringBuffer.append("medium");
			} else
				stringBuffer.append("small");
			stringBuffer.append(" size. It contains ");
			stringBuffer.append(bugs);
			stringBuffer.append(" bugs, ");
			stringBuffer.append(comments);
			stringBuffer.append(" comments and ");
			stringBuffer.append(patches);
			stringBuffer.append(" patches, in total.\n");
		
		}
		
		factoid.setFactoid(stringBuffer.toString());

	}

	private int getCumulativeNumberOfBugs(List<Pongo> newBugsList, Map<String, Integer> trackerBugs) {
		int sum = 0;
		for (Pongo pongo: newBugsList) {
			BugsNewBugsHistoricMetric newBugsPongo = (BugsNewBugsHistoricMetric) pongo;
			for (DailyBugData bugData: newBugsPongo.getBugs()) {
				int bugs = newBugsPongo.getCumulativeNumberOfBugs();
				trackerBugs.put(bugData.getBugTrackerId(), bugs);
				sum += bugs;
			}
		}
		return sum;
	}
	
	private int getCumulativeNumberOfComments(List<Pongo> commentsList, Map<String, Integer> trackerComments) {
		int sum = 0;
		for (Pongo pongo: commentsList) {
			BugsCommentsHistoricMetric commentsPongo = (BugsCommentsHistoricMetric) pongo;
			for (org.ossmeter.metricprovider.historic.bugs.comments.model.DailyBugData 
					bugData: commentsPongo.getBugs()) {
				int comments = commentsPongo.getCumulativeNumberOfComments();
				trackerComments.put(bugData.getBugTrackerId(), comments);
				sum += comments;
			}
		}
		return sum;
	}
	
	private int getCumulativeNumberOfPatches(List<Pongo> patchesList, Map<String, Integer> trackerPatches) {
		int sum = 0;
		for (Pongo pongo: patchesList) {
			BugsPatchesHistoricMetric patchesPongo = (BugsPatchesHistoricMetric) pongo;
			for (org.ossmeter.metricprovider.historic.bugs.patches.model.DailyBugData 
					bugData: patchesPongo.getBugs()) {
				int patches = patchesPongo.getCumulativeNumberOfPatches();
				trackerPatches.put(bugData.getBugTrackerId(), patches);
				sum += patches;
			}
		}
		return sum;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedSet<String> sortByKeys(Map<String, ?> map) {
		return new TreeSet(map.keySet());
	}

}
