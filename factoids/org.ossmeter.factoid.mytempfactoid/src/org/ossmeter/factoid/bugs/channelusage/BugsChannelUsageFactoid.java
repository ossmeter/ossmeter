/*******************************************************************************
 * Copyright (c) 2014 OSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.ossmeter.factoid.bugs.channelusage;

import java.text.ParseException;
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

public class BugsChannelUsageFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelUsage";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Channel Usage";
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
		return Arrays.asList(NewBugsHistoricMetricProvider.IDENTIFIER,
							 CommentsHistoricMetricProvider.IDENTIFIER,
							 PatchesHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName("Bug Channel Usage Factoid");

		NewBugsHistoricMetricProvider newBugsProvider = null;
		CommentsHistoricMetricProvider commentsProvider = null;
		PatchesHistoricMetricProvider patchesProvider = null;
		
		
		for (IMetricProvider m : this.uses) {
			if (m instanceof NewBugsHistoricMetricProvider) {
				newBugsProvider = (NewBugsHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof CommentsHistoricMetricProvider) {
				commentsProvider = (CommentsHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof PatchesHistoricMetricProvider) {
				patchesProvider = (PatchesHistoricMetricProvider) m;
				continue;
			}
		}
		
		Date end = new Date();
		Date start = (new Date()).addDays(-365);
//		Date start=null, end=null;
//		try {
//			start = new Date("20050301");
//			end = new Date("20060301");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> newBugsList = newBugsProvider.getHistoricalMeasurements(context, project, start, end),
					commentsList = commentsProvider.getHistoricalMeasurements(context, project, start, end),
					patchesList = patchesProvider.getHistoricalMeasurements(context, project, start, end);
		
		Map<String, Integer> trackerBugs = new HashMap<String, Integer>();
		int numberOfBugs = parseNewBugsPongos(newBugsList, trackerBugs);

		Map<String, Integer> trackerComments = new HashMap<String, Integer>();
		int numberOfComments = parseCommentsPongos(commentsList, trackerComments);

		Map<String, Integer> trackerPatches = new HashMap<String, Integer>();
		int numberOfPatches = parsePatchesPongos(patchesList, trackerPatches);

		int workingDaysInAYear = 250;
		if ( (numberOfBugs > workingDaysInAYear) || (numberOfComments > workingDaysInAYear) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (2 * numberOfBugs > workingDaysInAYear) || (2 * numberOfComments > workingDaysInAYear) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (4 * numberOfBugs > workingDaysInAYear) || (4 * numberOfComments > workingDaysInAYear) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}
		

		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("The project is associated with ");
		stringBuffer.append(project.getBugTrackingSystems().size());
		stringBuffer.append(" bug tracking ");
		
		if (project.getBugTrackingSystems().size()==1)
			stringBuffer.append("system.\nIn the last year, it has ");
		else
			stringBuffer.append("systems.\nIn the last year, they have ");
			
		if ( (numberOfBugs > workingDaysInAYear) || (numberOfComments > workingDaysInAYear) ) {
			stringBuffer.append("received high ");
		} else if ( (2 * numberOfBugs > workingDaysInAYear) || (2 * numberOfComments > workingDaysInAYear) ) {
			stringBuffer.append("received much ");
		} else if ( (4 * numberOfBugs > workingDaysInAYear) || (4 * numberOfComments > workingDaysInAYear) ) {
			stringBuffer.append("received some ");
		} else
			stringBuffer.append("not received much ");
		stringBuffer.append("attention.\n");

		stringBuffer.append(numberOfBugs);
		stringBuffer.append(" new bugs, ");
		stringBuffer.append(numberOfComments);
		stringBuffer.append(" new comments and ");
		stringBuffer.append(numberOfPatches);
		stringBuffer.append(" new patches have been posted, in total.\n");

		for (String tracker: sortByKeys(trackerBugs)) {
			stringBuffer.append(trackerBugs.get(tracker));
			stringBuffer.append(" new bugs, ");
			int commentFrequency = 0;
			if (trackerComments.containsKey(tracker))
				commentFrequency = trackerComments.get(tracker);
			stringBuffer.append(commentFrequency);
			stringBuffer.append(" new comments and ");
			int patchFrequency = 0;
			if (trackerPatches.containsKey(tracker))
				patchFrequency = trackerPatches.get(tracker);
			stringBuffer.append(patchFrequency);
			stringBuffer.append(" new patches have been posted to bug tracking system ");
			stringBuffer.append(tracker);
			stringBuffer.append(".\n");
		}

		end = new Date();
		start = (new Date()).addDays(-30);
		newBugsList = newBugsProvider.getHistoricalMeasurements(context, project, start, end);
		commentsList = commentsProvider.getHistoricalMeasurements(context, project, start, end);
		patchesList = patchesProvider.getHistoricalMeasurements(context, project, start, end);
		
		trackerBugs = new HashMap<String, Integer>();
		numberOfBugs = parseNewBugsPongos(newBugsList, trackerBugs);

		trackerComments = new HashMap<String, Integer>();
		numberOfComments = parseCommentsPongos(commentsList, trackerComments);

		trackerPatches = new HashMap<String, Integer>();
		numberOfPatches = parsePatchesPongos(patchesList, trackerPatches);

		
		stringBuffer.append("In the last month, ");
		stringBuffer.append(numberOfBugs);
		stringBuffer.append(" new bugs, ");
		stringBuffer.append(numberOfComments);
		stringBuffer.append(" new comments and ");
		stringBuffer.append(numberOfPatches);
		if (trackerBugs.size()==1)
			stringBuffer.append(" new patches have been posted to the bug tracker of the project.\n");
		else
			stringBuffer.append(" new patches have been posted to the bug trackers of the project.\n");

		for (String tracker: sortByKeys(trackerBugs)) {
			stringBuffer.append(trackerBugs.get(tracker));
			stringBuffer.append(" new bugs, ");
			int commentFrequency = 0;
			if (trackerComments.containsKey(tracker))
				commentFrequency = trackerComments.get(tracker);
			stringBuffer.append(commentFrequency);
			stringBuffer.append(" new comments and ");
			int patchFrequency = 0;
			if (trackerPatches.containsKey(tracker))
				patchFrequency = trackerPatches.get(tracker);
			stringBuffer.append(patchFrequency);
			stringBuffer.append(" new patches have been posted to bug tracking system ");
			stringBuffer.append(tracker);
			stringBuffer.append(".\n");
		}

		factoid.setFactoid(stringBuffer.toString());

	}

	private int parseNewBugsPongos(List<Pongo> newBugsList, Map<String, Integer> trackerBugs) {
		int numberOfBugs = 0;
		for (Pongo pongo: newBugsList) {
			BugsNewBugsHistoricMetric newBugsPongo = (BugsNewBugsHistoricMetric) pongo;
			for (DailyBugData bugData: newBugsPongo.getBugs()) {
				numberOfBugs++;
				if (trackerBugs.containsKey(bugData.getBugTrackerId()))
					trackerBugs.put(bugData.getBugTrackerId(), 
							trackerBugs.get(bugData.getBugTrackerId()) + 1);
				else
					trackerBugs.put(bugData.getBugTrackerId(), 1);
			}
		}
		return numberOfBugs;
	}
		
	private int parseCommentsPongos(List<Pongo> commentsList, Map<String, Integer> trackerComments) {
		int numberOfComments = 0;
		for (Pongo pongo: commentsList) {
			BugsCommentsHistoricMetric commentsPongo = (BugsCommentsHistoricMetric) pongo;
			for (org.ossmeter.metricprovider.historic.bugs.comments.model.DailyBugData 
					bugData: commentsPongo.getBugs()) {
				numberOfComments += bugData.getNumberOfComments();
				if (trackerComments.containsKey(bugData.getBugTrackerId()))
					trackerComments.put(bugData.getBugTrackerId(), 
										trackerComments.get(bugData.getBugTrackerId()) + bugData.getNumberOfComments());
				else
					trackerComments.put(bugData.getBugTrackerId(), bugData.getNumberOfComments());
			}
		}
		return numberOfComments;
	}
	
	private int parsePatchesPongos(List<Pongo> patchesList, Map<String, Integer> trackerPatches) {
		int numberOfPatches = 0;
		for (Pongo pongo: patchesList) {
			BugsPatchesHistoricMetric patchesPongo = (BugsPatchesHistoricMetric) pongo;
			for (org.ossmeter.metricprovider.historic.bugs.patches.model.DailyBugData 
					bugData: patchesPongo.getBugs()) {
				numberOfPatches += bugData.getNumberOfPatches();
				if (trackerPatches.containsKey(bugData.getBugTrackerId()))
					trackerPatches.put(bugData.getBugTrackerId(), 
							trackerPatches.get(bugData.getBugTrackerId()) + bugData.getNumberOfPatches());
				else
					trackerPatches.put(bugData.getBugTrackerId(), bugData.getNumberOfPatches());
			}
		}
		return numberOfPatches;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedSet<String> sortByKeys(Map<String, ?> map) {
		return new TreeSet(map.keySet());
	}

}