package org.ossmeter.metricprovider.generic.totalloc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.generic.totalloc.model.TLocRepositoryData;
import org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc;
import org.ossmeter.metricprovider.loc.LocMetricProvider;
import org.ossmeter.metricprovider.loc.model.LinesOfCodeData;
import org.ossmeter.metricprovider.loc.model.Loc;
import org.ossmeter.metricprovider.loc.model.RepositoryData;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class GenericTotalLocMetricProvider implements IHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.generic.totalloc";

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
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public Pongo measure(Project project) {
		List<RepositoryData> fileRepos = new ArrayList<RepositoryData>();
	
		for (IMetricProvider used : uses) {
			Loc usedLoc =  ((LocMetricProvider)used).adapt(context.getProjectDB(project));
			for (RepositoryData rd : usedLoc.getRepositories()) {
				fileRepos.add(rd);
			}
		}
		
		TotalLoc tLoc = new TotalLoc();
		
		for (RepositoryData fileRd : fileRepos) {
			TLocRepositoryData totalRd = new TLocRepositoryData(); 
			totalRd.setUrl(fileRd.getUrl());
			totalRd.setRepoType(fileRd.getRepoType());
			totalRd.setRevision(fileRd.getRevision());
			
			// Now count those lines!
			long loc = 0;
			for (LinesOfCodeData locd : fileRd.getLinesOfCode()) {
				loc += locd.getLines();
			}
			totalRd.setTotalLines(loc);

			// Save
			tLoc.getRepositories().add(totalRd);
		}

		return tLoc;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		System.out.println("LocMetricProvider.class.getCanonicalName(): " + LocMetricProvider.class.getCanonicalName());
		return Arrays.asList(LocMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
}
