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
package org.ossmeter.factoid.bugs.threadlength;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.bugs.BugsHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugsChannelThreadLengthFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelThreadLength";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Channel Thread Length";
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
		return Arrays.asList(BugsHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		BugsHistoricMetricProvider bugsProvider = null;
		
		for (IMetricProvider m : this.uses) {
			if (m instanceof BugsHistoricMetricProvider) {
				bugsProvider = (BugsHistoricMetricProvider) m;
				continue;
			}
		}

		Date end = new Date();
		Date start = (new Date()).addDays(-30);
//		Date start=null, end=null;
//		try {
//			start = new Date("20050301");
//			end = new Date("20060301");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> bugsList = bugsProvider.getHistoricalMeasurements(context, project, start, end);
		
		float averageComments = 0,
			  averageRequests = 0,
			  averageReplies = 0;

		if ( bugsList.size() > 0 ) {
			BugsBugsHistoricMetric bugsPongo = 
					(BugsBugsHistoricMetric) bugsList.get(bugsList.size() - 1);
			averageComments = bugsPongo.getAverageCommentsPerBug();
			averageRequests = bugsPongo.getAverageRequestsPerBug();
			averageReplies = bugsPongo.getAverageRepliesPerBug();
		}

		if ( (averageComments > 0 ) && (averageComments < 5 ) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (averageComments > 0 ) && ( averageComments < 10 ) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (averageComments > 0 ) && ( averageComments < 20 ) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		stringBuffer.append("Threaded discussions tend to be ");
		if ( (averageComments > 0 ) && (averageComments < 5 ) ) {
			stringBuffer.append("very short");
		} else if ( (averageComments > 0 ) && ( averageComments < 10 ) ) {
			stringBuffer.append("short");
		} else if ( (averageComments > 0 ) && ( averageComments < 20 ) ) {
			stringBuffer.append("quite long");
		} else {
			stringBuffer.append("long");
		}
		stringBuffer.append(", approximately ");
		stringBuffer.append( decimalFormat.format(averageComments));
		stringBuffer.append(" comments long.\nIn particular, a bug contains approximately ");
		stringBuffer.append( decimalFormat.format(averageRequests));
		stringBuffer.append(" requests and ");
		stringBuffer.append( decimalFormat.format(averageReplies));
		stringBuffer.append(" replies.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

}
