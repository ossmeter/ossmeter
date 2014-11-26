package org.ossmeter.factoid.newsgroups.threadlength;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.newsgroups.threads.ThreadsHistoricMetricProvider;
import org.ossmeter.metricprovider.historic.newsgroups.threads.model.NewsgroupsThreadsHistoricMetric;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsChannelThreadLengthFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelThreadLength";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Thread Length";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		ThreadsHistoricMetricProvider threadsProvider = null;
		
		for (IMetricProvider m : this.uses) {
			if (m instanceof ThreadsHistoricMetricProvider) {
				threadsProvider = (ThreadsHistoricMetricProvider) m;
				continue;
			}
		}

		Date end = new Date();
		Date start = (new Date()).addDays(-30);
//		Date start=null, end=null;
//		try {
//			start = new Date("20040801");
//			end = new Date("20050801");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> threadsList = threadsProvider.getHistoricalMeasurements(context, project, start, end);
		
		float averageComments = 0,
			  averageRequests = 0,
			  averageReplies = 0;

		if ( threadsList.size() > 0 ) {
			NewsgroupsThreadsHistoricMetric bugsPongo = 
					(NewsgroupsThreadsHistoricMetric) threadsList.get(threadsList.size() - 1);
			averageComments = bugsPongo.getAverageArticlesPerThread();
			averageRequests = bugsPongo.getAverageRequestsPerThread();
			averageReplies = bugsPongo.getAverageRepliesPerThread();
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
		stringBuffer.append(" articles.\nIn particular, a thread contains approximately ");
		stringBuffer.append( decimalFormat.format(averageRequests));
		stringBuffer.append(" requests and ");
		stringBuffer.append( decimalFormat.format(averageReplies));
		stringBuffer.append(" replies.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

}
