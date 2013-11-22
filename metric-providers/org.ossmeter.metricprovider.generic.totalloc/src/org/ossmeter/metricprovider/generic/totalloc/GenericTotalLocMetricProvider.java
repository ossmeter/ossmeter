package org.ossmeter.metricprovider.generic.totalloc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.generic.totalloc.model.TotalLoc;
import org.ossmeter.metricprovider.rascal.metric.LinesOfCode;
import org.ossmeter.metricprovider.rascal.metric.trans.model.LinesOfCodeData;
import org.ossmeter.metricprovider.rascal.metric.trans.model.Loc;
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
	public String getFriendlyName() {
		return "Total lines of code over time";
	}
	@Override
	public String getShortIdentifier() {
		return "totalloc";
	}
	/**
	 * {@inheritDoc}
	 * Disclaimer: this summary information is taken from Wikipedia. It's only for illustrative purposes.
	 */
	@Override
	public String getSummaryInformation() {
		return "Lines of code is a software metric used to measure the size of a computer program by counting the number of lines in the text of the program's source code.";
	}
	
	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public Pongo measure(Project project) {
		List<LinesOfCodeData> locData = new ArrayList<LinesOfCodeData>();
	
		for (IMetricProvider used : uses) {
			Loc usedLoc =  ((LinesOfCode)used).adapt(context.getProjectDB(project));
			for (LinesOfCodeData rd : usedLoc.getLinesOfCode()) {
				locData.add(rd);
			}
		}
		
		TotalLoc hist = new TotalLoc();
		
		hist.setUrl(project.getVcsRepositories().get(0).getUrl());
		hist.setRepoType(project.getVcsRepositories().get(0).getClass().getCanonicalName());
//		hist.setRevision(locD.getRevisionNumber());
		
		// Now count those lines!
		long tloc = 0, eloc = 0, sloc = 0, cloc = 0;
		for (LinesOfCodeData locD : locData) {
			tloc += locD.getTotalLines();
			sloc += locD.getSourceLines();
			cloc += locD.getCommentedLines();
			eloc += locD.getEmptyLines();
		}
		hist.setTotalLines(tloc);
		hist.setSourceLines(sloc);
		hist.setCommentedLines(cloc);
		hist.setEmptyLines(eloc);

		return hist;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(LinesOfCode.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
}
