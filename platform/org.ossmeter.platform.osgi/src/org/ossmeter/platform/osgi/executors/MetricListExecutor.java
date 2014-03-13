package org.ossmeter.platform.osgi.executors;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.ossmeter.platform.Date;
import org.ossmeter.platform.IHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricHistoryManager;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.Platform;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLoggerFactory;
import org.ossmeter.repository.model.MetricProvider;
import org.ossmeter.repository.model.MetricProviderType;
import org.ossmeter.repository.model.Project;

public class MetricListExecutor implements Runnable {

	final protected Platform platform;
	final protected Project project;
	protected List<IMetricProvider> metrics;
	protected ProjectDelta delta;
	protected Date date;
	
	public MetricListExecutor(Platform platform, Project project, ProjectDelta delta, Date date) {
		this.project = project;
		this.platform = platform;
		this.delta = delta;
		this.date = date;
	}
	
	public void setMetricList(List<IMetricProvider> metrics) {
		this.metrics = metrics;
	}
	
	@Override
	public void run() {
		
		for (IMetricProvider m : metrics) {
			System.out.println("\t" + m.getIdentifier() + " executed");
			
			m.setMetricProviderContext(new MetricProviderContext(platform, 
					new OssmeterLoggerFactory().makeNewLoggerInstance(m.getIdentifier())));
			addDependenciesToMetricProvider(m);
			
			
			try {
				if (m instanceof ITransientMetricProvider) {
					((ITransientMetricProvider) m).measure(project, delta, ((ITransientMetricProvider) m).adapt(platform.getMetricsRepository(project).getDb()));
					updateMetricProviderMetaData(project, m, date, MetricProviderType.TRANSIENT);
				} else if (m instanceof IHistoricalMetricProvider) {
					MetricHistoryManager historyManager = new MetricHistoryManager(platform);
					historyManager.store(project, date, (IHistoricalMetricProvider) m);
					updateMetricProviderMetaData(project, m, date, MetricProviderType.HISTORIC);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Adds references to the dependencies of a metric provider so that they
	 * can use their data for the calculations.
	 * 
	 * FIXME: This seems like an inefficient approach. Look at this later.
	 * @param mp
	 */
	protected void addDependenciesToMetricProvider(IMetricProvider mp) {
		if (mp.getIdentifiersOfUses() == null) return; 
		
		List<IMetricProvider> uses = new ArrayList<IMetricProvider>();
		for (String id : mp.getIdentifiersOfUses()) {
			for (IMetricProvider imp : platform.getMetricProviderManager().getMetricProviders()) {
				if (imp.getIdentifier().equals(id)) {
					uses.add(imp);
					break;
				}
			}
		}
		mp.setUses(uses);
	}
	
	/**
	 * Ensures that the project DB has the up-to-date information regarding
	 * the date of last execution.
	 * @param project
	 * @param provider
	 * @param date
	 * @param type
	 */
	protected void updateMetricProviderMetaData(Project project, IMetricProvider provider, Date date, MetricProviderType type) {
		// Update project MP meta-data
		MetricProvider mp = getProjectModelMetricProvider(project, provider);
		if (mp == null) {
			mp = new MetricProvider();
			project.getMetricProviderData().add(mp);
			mp.setMetricProviderId(provider.getShortIdentifier());
			mp.setType(type);
		}
		mp.setLastExecuted(date.toString()); 
		platform.getProjectRepositoryManager().getProjectRepository().sync();
	}
	
	/**
	 * 
	 * @param project
	 * @param iProvider
	 * @return A MetricProvider (part of the Project DB) that matches the given IMetricProvider.
	 */
	protected MetricProvider getProjectModelMetricProvider(Project project, IMetricProvider iProvider) {
		Iterator<MetricProvider> it = project.getMetricProviderData().iterator();
		MetricProvider mp = null;
		while (it.hasNext()) {
			mp = it.next();
			if (mp.getMetricProviderId().equals(iProvider.getShortIdentifier())) {
				return mp;
			}
		}

		return null;
	}
}
