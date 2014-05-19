package org.ossmeter.metricprovider.historic.totaldownloadcounter;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.downloadcounter.model.Download;
import org.ossmeter.metricprovider.downloadcounter.model.DownloadCounter;
import org.ossmeter.metricprovider.downloadcounter.sourceforge.SourceForgeDownloadCounterMetricProvider;
import org.ossmeter.metricprovider.historic.totaldownloadcounter.model.TotalDownloadCounter;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.sourceforge.SourceForgeProject;

import com.googlecode.pongo.runtime.Pongo;

public class TotalDownloadCounterMetricProvider implements IHistoricalMetricProvider{

	protected MetricProviderContext context;
	protected SourceForgeDownloadCounterMetricProvider downloadCounterMetricProvider;
	
	@Override
	public String getIdentifier() {
		return TotalDownloadCounterMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		return project instanceof SourceForgeProject;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// TODO Auto-generated method stub
		this.downloadCounterMetricProvider = (SourceForgeDownloadCounterMetricProvider)uses.get(0);
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		// TODO Auto-generated method stub
		return Arrays.asList("DownloadCounterMetricProvider");
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
	
	@Override
	public Pongo measure(Project project) {
		
		DownloadCounter downloadCounter =  new DownloadCounter(context.getProjectDB(project));
		int totalCounter = 0;
		
		for (Download download : downloadCounter.getDownloads()) {
			totalCounter = totalCounter + download.getCounter();
		}
		
		TotalDownloadCounter totalDownloadCounter = new TotalDownloadCounter();
		totalDownloadCounter.setDownloads(totalCounter);
		
		return totalDownloadCounter;
	}

	@Override
	public String getShortIdentifier() {
		return "tdc";
	}

	@Override
	public String getFriendlyName() {
		return "Total downloads";
	}

	@Override
	public String getSummaryInformation() {
		return "Lorum";
	}

}
