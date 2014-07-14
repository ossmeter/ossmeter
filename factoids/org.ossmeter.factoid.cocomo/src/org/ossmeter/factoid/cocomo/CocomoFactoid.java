package org.ossmeter.factoid.cocomo;

import java.util.List;
import java.util.Random;

import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.Project;

public class CocomoFactoid extends AbstractFactoidMetricProvider{

	@Override
	public String getShortIdentifier() {
		return "";
	}

	@Override
	public String getFriendlyName() {
		return ""; // This method will be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return ""; // This method will be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return null;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
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
		
		factoid.setFactoid("Took an estimated " + years + " years (COCOMO model).");
		
		if (years < 5) {
			factoid.setStars(StarRating.ONE);
		} else if (years < 10) {
			factoid.setStars(StarRating.TWO);
		} else if (years < 50) {
			factoid.setStars(StarRating.THREE);
		} else {
			factoid.setStars(StarRating.FOUR);
		}
	}

}
