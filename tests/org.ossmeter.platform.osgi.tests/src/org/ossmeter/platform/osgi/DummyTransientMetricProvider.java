package org.ossmeter.platform.osgi;

import java.util.List;

import org.apache.log4j.Logger;
import org.ossmeter.platform.AbstractTransientMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.logging.OssmeterLogger;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.PongoDB;
import com.mongodb.DB;

public class DummyTransientMetricProvider extends AbstractTransientMetricProvider<PongoDB> {

	protected Logger logger;
	
	public DummyTransientMetricProvider() {
		this.logger = OssmeterLogger.getLogger(getShortIdentifier());
	}
	
	@Override
	public String getShortIdentifier() {
		return "dummy";
	}

	@Override
	public String getFriendlyName() {
		return null;
	}

	@Override
	public String getSummaryInformation() {
		return null;
	}

	@Override
	public boolean appliesTo(Project project) {
		return true;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		
	}

	@Override
	public PongoDB adapt(DB db) {
		return null;
	}

	@Override
	public void measure(Project project, ProjectDelta delta, PongoDB db) {
		// TODO Auto-generated method stub
		logger.info("DummyMetricProvider executed.");
	}

}
