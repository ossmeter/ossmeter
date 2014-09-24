package org.ossmeter.factoid.mytempfactoid;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.ossmeter.metricprovider.historic.avgnumberofrequestsreplies.AverageNumberOfRequestsRepliesProvider;
import org.ossmeter.metricprovider.historic.avgnumberofrequestsreplies.model.AverageRR;
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
import com.mongodb.DBCollection;

public class CocomoFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "shartblah";
	}

	@Override
	public String getFriendlyName() {
		return "friendlyblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		//I need to generalise this for other managers
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(AverageNumberOfRequestsRepliesProvider.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		
//		  AverageNumberOfRequestsRepliesProvider provider = 
//				  ((AverageNumberOfRequestsRepliesProvider)uses.get(0)).adapt(context.getProjectDB(project));

//		DBCollection db = context.getProjectDB(project).getCollection(AverageNumberOfRequestsRepliesProvider.class.getCanonicalName());
		
		
		
		
		AverageNumberOfRequestsRepliesProvider provider = new AverageNumberOfRequestsRepliesProvider();
		Date end = new Date();
		Date start = end.addDays(-30);
		List<Pongo> pongoList = provider.getHistoricalMeasurements(context, project, start, end);
		
		if (pongoList.size()>0) {
			AverageRR firstAvgRR = (AverageRR) pongoList.get(0);
			AverageRR lastAvgRR = (AverageRR) pongoList.get(pongoList.size()-1);
			float diffArticles = firstAvgRR.getAverageArticles() - lastAvgRR.getAverageArticles(); 
			float diffRequests = firstAvgRR.getAverageRequests() - lastAvgRR.getAverageRequests(); 
			float diffReply = firstAvgRR.getAverageReplies() - lastAvgRR.getAverageReplies(); 
		}
		  
		// Assumes ALL projects are "semi-detached"
		double a = 3.0;
		double b = 1.12;
		double c = 2.5;
		double d = 0.35;
		
		int kloc = new Random().nextInt(50000000); // FIXME get from metric
		kloc /= 1000;
		
		double effortApplied = a * Math.pow(kloc, b); // person months
		double devTime = c * Math.pow(effortApplied, d); // months
		double peopleRequired = effortApplied / devTime; // count
		
		int years = (int)effortApplied / 12;
		
		factoid.setFactoid("Toke an estimated " + years + " years (COCOMO model).");
		
		if (years < 5) {
			factoid.setStars(StarRating.ONE);
		} else if (years < 10) {
			factoid.setStars(StarRating.TWO);
		} else if (years < 50) {
			factoid.setStars(StarRating.THREE);
		} else {
			factoid.setStars(StarRating.FOUR);
		}
		
		factoid.setCategory(...);
	}

}
