package org.ossmeter.metricprovider.historic.avgbugcreationclosedtime;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.metricprovider.trans.bugheadermetadata.BugHeaderMetadataMetricProvider;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugData;
import org.ossmeter.metricprovider.trans.bugheadermetadata.model.BugHeaderMetadata;
import org.ossmeter.metricprovider.historic.avgbugcreationclosedtime.model.DailyAvgBugOpenTime;
import org.ossmeter.metricprovider.historic.avgbugcreationclosedtime.model.DailyBugzillaData;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.communicationchannel.nntp.NntpUtil;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;

import com.googlecode.pongo.runtime.Pongo;

public class AvgBugzillaBugOpenTimeProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.ossmeter.metricprovider.generic.avgbugcreationclosedtime";

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

	private static final long SECONDS_DAY = 24 * 60 * 60;

	@Override
	public Pongo measure(Project project) {
		DailyAvgBugOpenTime avgBugOpenTime = new DailyAvgBugOpenTime();
		for (IMetricProvider used : uses) {
			BugHeaderMetadata usedBhm = ((BugHeaderMetadataMetricProvider)used).adapt(context.getProjectDB(project));
			long seconds = 0;
			int durations = 0;
			for (BugData bugData: usedBhm.getBugs()) {
				if (!bugData.getLastClosedTime().equals("null")) {
					java.util.Date javaOpenTime = NntpUtil.parseDate(bugData.getCreationTime());
					java.util.Date javaCloseTime = NntpUtil.parseDate(bugData.getLastClosedTime());
					seconds += ( Date.duration(javaOpenTime, javaCloseTime) / 1000);
					durations++;
				}
			}
			if (durations>0) {
				DailyBugzillaData dailyBugzillaData = new DailyBugzillaData();
				long avgDuration = seconds/durations;
				int days = (int) (avgDuration / SECONDS_DAY);
				long lessThanDay = (avgDuration % SECONDS_DAY);
				String formatted = DurationFormatUtils.formatDuration(lessThanDay*1000, "HH:mm:ss:SS");
				dailyBugzillaData.setAvgBugOpenTime(days+":"+formatted);
//				System.out.println(days + ":" + formatted);
				dailyBugzillaData.setBugsConsidered(durations);
				avgBugOpenTime.getBugzillas().add(dailyBugzillaData);
			}

		}
		return avgBugOpenTime;
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
		return "bugopentime";
	}

	@Override
	public String getFriendlyName() {
		return "Average Bug Duration";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average time between creating and closing bugzilla bugs. " +
				"Format: dd:HH:mm:ss:SS, where dd=days, HH:hours, mm=minutes, ss:seconds, SS=milliseconds.";
	}
}
