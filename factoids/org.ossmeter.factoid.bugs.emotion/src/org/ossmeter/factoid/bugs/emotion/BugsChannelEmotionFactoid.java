package org.ossmeter.factoid.bugs.emotion;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugs.emotions.EmotionsTransMetricProvider;
import org.ossmeter.metricprovider.trans.bugs.emotions.model.BugsEmotionsTransMetric;
import org.ossmeter.metricprovider.trans.bugs.emotions.model.EmotionDimension;
import org.ossmeter.platform.AbstractFactoidMetricProvider;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.factoids.Factoid;
import org.ossmeter.platform.factoids.StarRating;
import org.ossmeter.repository.model.Project;

public class BugsChannelEmotionFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelEmotion";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Channel Emotion";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(EmotionsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName("Bug Channel Emotion Factoid");

		BugsEmotionsTransMetric emotionsTransMetric = 
				((EmotionsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		StringBuffer stringBuffer = new StringBuffer();
		
		float positiveEmotionPercentageSum = 0,
			  negativeEmotionPercentageSum = 0,
			  mostCommonPositivePercentage = 0,
			  mostCommonNegativePercentage = 0,
			  leastCommonPositivePercentage = 100,
			  leastCommonNegativePercentage = 100;
		String mostCommonPositive = "",
			   mostCommonNegative = "",
			   leastCommonPositive = "",
			   leastCommonNegative = "";
		for (EmotionDimension dimension: emotionsTransMetric.getDimensions()) {
			if ( ( dimension.getEmotionLabel().equals("anger") )
			  || ( dimension.getEmotionLabel().equals("sadness") )
			  || ( dimension.getEmotionLabel().equals("disgust") )
			  || ( dimension.getEmotionLabel().equals("fear") ) ) {
				negativeEmotionPercentageSum += dimension.getCumulativePercentage();
				if ( mostCommonNegativePercentage < dimension.getCumulativePercentage() ) {
					mostCommonNegativePercentage = dimension.getCumulativePercentage();
					mostCommonNegative = dimension.getEmotionLabel();
				}
				if ( leastCommonNegativePercentage > dimension.getCumulativePercentage() ) {
					leastCommonNegativePercentage = dimension.getCumulativePercentage();
					leastCommonNegative = dimension.getEmotionLabel();
				}
			} else {
				positiveEmotionPercentageSum += dimension.getCumulativePercentage();
				if ( mostCommonPositivePercentage < dimension.getCumulativePercentage() ) {
					mostCommonPositivePercentage = dimension.getCumulativePercentage();
					mostCommonPositive = dimension.getEmotionLabel();
				}
				if ( leastCommonPositivePercentage > dimension.getCumulativePercentage() ) {
					leastCommonPositivePercentage = dimension.getCumulativePercentage();
					leastCommonPositive = dimension.getEmotionLabel();
				}
			}
		}
		
		float positiveEmotionPercentage = positiveEmotionPercentageSum / 4,
			  negativeEmotionPercentage = negativeEmotionPercentageSum / 4;
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		if ( ( positiveEmotionPercentage > 80 ) || ( negativeEmotionPercentage < 35 ) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( ( positiveEmotionPercentage > 65 ) || ( negativeEmotionPercentage < 50 ) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( ( positiveEmotionPercentage > 50 ) || ( negativeEmotionPercentage < 65 ) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}
		
		stringBuffer.append("There are ");
		if  ( positiveEmotionPercentage > 80 )
			stringBuffer.append("many");
		else if ( positiveEmotionPercentage > 65 )
			stringBuffer.append("not so many");
		else if ( positiveEmotionPercentage > 50 )
			stringBuffer.append("few");
		else if ( positiveEmotionPercentage > 35 )
			stringBuffer.append("very few");
		stringBuffer.append(" comments that express positive emotions, while there are ");
		if  ( negativeEmotionPercentage > 80 )
			stringBuffer.append("many");
		else if ( negativeEmotionPercentage > 65 )
			stringBuffer.append("not so many");
		else if ( negativeEmotionPercentage > 50 )
			stringBuffer.append("few");
		else if ( negativeEmotionPercentage > 35 )
			stringBuffer.append("very few");
		stringBuffer.append(" comments that express negative emotions.\n");
		
		stringBuffer.append("The most and least common negative emotions are ");
		stringBuffer.append(mostCommonNegative);
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(mostCommonNegativePercentage));
		stringBuffer.append(" %) and ");
		stringBuffer.append(leastCommonNegative);
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(leastCommonNegativePercentage));
		stringBuffer.append(" %)");
		stringBuffer.append(", while the most and least common positive emotions are ");
		stringBuffer.append(mostCommonPositive);
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(mostCommonPositivePercentage));
		stringBuffer.append(" %) and ");
		stringBuffer.append(leastCommonPositive);
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(leastCommonPositivePercentage));
		stringBuffer.append(" %).\n");
		
	}

}
