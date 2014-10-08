package org.ossmeter.metricprovider.historic.bugs.status;

import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugTrackerData;
import org.ossmeter.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.ossmeter.platform.AbstractHistoricalMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class StatusHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.ossmeter.metricprovider.historic.bugs.status";

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
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		BugsStatusHistoricMetric bugStatus = new BugsStatusHistoricMetric();
		if (uses.size()==1) {
			 BugsBugMetadataTransMetric usedBhm = ((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 int numberOfResolvedClosedBugs = 0,
				 numberOfWontFixBugs = 0,
				 numberOfWorksForMeBugs = 0,
				 numberOfNonResolvedClosedBugs = 0,
				 numberOfInvalidBugs = 0,
				 numberOfFixedBugs = 0,
				 numberOfDuplicateBugs = 0;
			 for (BugTrackerData bugTrackerData: usedBhm.getBugTrackerData()) {
				 if (bugTrackerData.getStatus().toLowerCase().equals("resolved")||
						 (bugTrackerData.getStatus().toLowerCase().equals("closed")))
					 numberOfResolvedClosedBugs++;
				 if (bugTrackerData.getResolution().toLowerCase().equals("wontfix")
						 ||(bugTrackerData.getResolution().toLowerCase().equals("cantfix")))
						 	numberOfWontFixBugs++;
				 if (bugTrackerData.getResolution().toLowerCase().equals("worksforme"))
					 numberOfWorksForMeBugs++;
				 if (!bugTrackerData.getStatus().toLowerCase().equals("resolved")
						 &&(!bugTrackerData.getStatus().toLowerCase().equals("closed")))
					 numberOfNonResolvedClosedBugs++;
				 if (bugTrackerData.getResolution().toLowerCase().equals("invalid")
						 ||(bugTrackerData.getResolution().toLowerCase().equals("notabug")))
					 numberOfInvalidBugs++;
				 if ((bugTrackerData.getResolution().toLowerCase().equals("fixed"))
						 ||(bugTrackerData.getResolution().toLowerCase().equals("upstream"))
						 ||(bugTrackerData.getResolution().toLowerCase().equals("currentrelease"))
						 ||(bugTrackerData.getResolution().toLowerCase().equals("nextrelease"))
						 ||(bugTrackerData.getResolution().toLowerCase().equals("rawhide")))
						 	numberOfFixedBugs++;
				 if (bugTrackerData.getResolution().toLowerCase().equals("duplicate"))
					 numberOfDuplicateBugs++;
			 }
			 if (numberOfResolvedClosedBugs > 0)
				 bugStatus.setNumberOfResolvedClosedBugs(numberOfResolvedClosedBugs);
			 if (numberOfWontFixBugs > 0)
				 bugStatus.setNumberOfWontFixBugs(numberOfWontFixBugs);
			 if (numberOfWorksForMeBugs > 0)
				 bugStatus.setNumberOfWorksForMeBugs(numberOfWorksForMeBugs);
			 if (numberOfNonResolvedClosedBugs > 0)
				 bugStatus.setNumberOfNonResolvedClosedBugs(numberOfNonResolvedClosedBugs);
			 if (numberOfInvalidBugs > 0)
				 bugStatus.setNumberOfInvalidBugs(numberOfInvalidBugs);
			 if (numberOfFixedBugs > 0)
				 bugStatus.setNumberOfFixedBugs(numberOfFixedBugs);
			 if (numberOfDuplicateBugs > 0)
				 bugStatus.setNumberOfDuplicateBugs(numberOfDuplicateBugs);
		}
		return bugStatus;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugMetadataTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugstatus";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bugs Per Status";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of bugs that correspond to a status value.";
	}
}
