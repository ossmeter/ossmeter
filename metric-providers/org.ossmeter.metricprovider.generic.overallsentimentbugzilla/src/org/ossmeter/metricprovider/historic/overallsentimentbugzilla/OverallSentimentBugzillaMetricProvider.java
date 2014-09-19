package org.ossmeter.metricprovider.historic.overallsentimentbugzilla;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugheadermetadata.BugHeaderMetadataMetricProvider;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.metricprovider.historic.overallsentimentbugzilla.model.DailyBugzillaData;
import org.ossmeter.metricprovider.historic.overallsentimentbugzilla.model.OverallSentimentBugs;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class OverallSentimentBugzillaMetricProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.overallsentimentbugzilla";

	protected MetricProviderContext context;
	
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}
	
	@Override
	public boolean appliesTo(Project project) {
		for (BugTrackingSystem bugTrackingSystem: project.getBugTrackingSystems()) {
			if (bugTrackingSystem instanceof Bugzilla) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		OverallSentimentBugs overallSentimentBugs = new OverallSentimentBugs();
		for (IMetricProvider used : uses) {
			 BugHeaderMetadata usedBhm = ((BugHeaderMetadataMetricProvider)used).adapt(context.getProjectDB(project));
			 float overallSentiment = 0,
				   startSentiment = 0,
				   endSentiment = 0;
			 for (BugData bugData: usedBhm.getBugs()) {
				 overallSentiment += bugData.getAverageSentiment();
				 String start = bugData.getStartSentiment();
				 if (start.equals("Positive"))
					 startSentiment+=1;
				 else if (start.equals("Negative"))
					 startSentiment-=1;
				 String end = bugData.getEndSentiment();
				 if (end.equals("Positive"))
					 endSentiment+=1;
				 else if (end.equals("Negative"))
					 endSentiment-=1;
//				 if ()
//					 numberOfWorksForMeBugs++;
			 }
			 long size = usedBhm.getBugs().size();
			 if (size>0) {
				 overallSentiment /= size;
				 startSentiment /= size;
				 endSentiment /= size;
			 }
			 
			 DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
			 dailyBugzillaData.setOverallAverageSentiment(overallSentiment);
			 dailyBugzillaData.setOverallSentimentAtThreadBeggining(startSentiment);
			 dailyBugzillaData.setOverallSentimentAtThreadEnd(endSentiment);
			 overallSentimentBugs.getBugzillas().add(dailyBugzillaData);
		}
		return overallSentimentBugs;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugHeaderMetadataMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "overallsentimentbugzilla";
	}

	@Override
	public String getFriendlyName() {
		return "Overall Sentiment of Bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the overall sentiment per bugzilla repository up to the processing date." +
				"The overall sentiment score ranges from -1 (negative sentiment) to +1 (positive sentiment)." +
				"In the computation, the sentiment score of each thread contributes equally, independently of its size.";
	}
}
