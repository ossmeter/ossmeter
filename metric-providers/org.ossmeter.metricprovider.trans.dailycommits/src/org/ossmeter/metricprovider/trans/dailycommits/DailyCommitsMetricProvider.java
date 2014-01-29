package org.ossmeter.metricprovider.trans.dailycommits;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.ossmeter.metricprovider.trans.commits.CommitsTransientMetricProvider;
import org.ossmeter.metricprovider.trans.commits.model.CommitData;
import org.ossmeter.metricprovider.trans.commits.model.Commits;
import org.ossmeter.metricprovider.trans.commits.model.RepositoryData;
import org.ossmeter.metricprovider.trans.dailycommits.model.DailyCommits;
import org.ossmeter.metricprovider.trans.dailycommits.model.Day;
import org.ossmeter.platform.Date;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.repository.model.Project;

import com.mongodb.DB;

public class DailyCommitsMetricProvider implements ITransientMetricProvider<DailyCommits> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return DailyCommitsMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "dailycommits";
	}

	@Override
	public String getFriendlyName() {
		return "Commits by day";
	}

	@Override
	public String getSummaryInformation() {
		return "Commits group by the day on which they occurred.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(CommitsTransientMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public DailyCommits adapt(DB db) {
		return new DailyCommits(db);
	}

	/**
	 * FIXME: Currently this will calculate it for ALL repos.
	 */
	@Override
	public void measure(Project project, ProjectDelta delta, DailyCommits db) {
		
		String[] daysOfWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

		for (String d : daysOfWeek) {
			Day day = db.getDays().findOneByName(d);
			if (day == null) {
				day = new Day();
				day.setName(d);
				day.setNumberOfCommits(0);
				db.getDays().add(day);
				db.sync();
			}
		}
		
		List<RepositoryData> repos = new ArrayList<RepositoryData>();
		
		for (IMetricProvider used : uses) {
			Commits usedCommits =  ((CommitsTransientMetricProvider)used).adapt(context.getProjectDB(project));
			for (RepositoryData rd : usedCommits.getRepositories()) {
				repos.add(rd);
			}
		}
		
		// QUICKFIX: needed because COmmitsTransientMetricPRovider wno't clear 
		// old data if there are no vcs deltas that day and so this will count old data
		if (delta.getVcsDelta().getRepoDeltas().size() >0) { 
			for (RepositoryData rd : repos) {
				for (CommitData commit : rd.getCommits()){
					try {
						Date date = new Date(commit.getDate());
						Calendar cal = Calendar.getInstance();
						cal.setTime(date.toJavaDate());
						int dow = cal.get(Calendar.DAY_OF_WEEK)-1;
						
						String dayName = daysOfWeek[dow];
						
						Day day = db.getDays().findOneByName(dayName);
						day.setNumberOfCommits(day.getNumberOfCommits()+1);
						db.sync();
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
