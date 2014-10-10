package org.ossmeter.metricprovider.trans.bugs.emotions;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.trans.bugs.emotions.model.BugTrackerData;
import org.ossmeter.metricprovider.trans.bugs.emotions.model.BugsEmotionsTransMetric;
import org.ossmeter.metricprovider.trans.bugs.emotions.model.Emotion;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.repository.model.BugTrackingSystem;
import org.ossmeter.repository.model.Project;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.EmotionalDimensions;

import com.mongodb.DB;

public class OverallEmotionsMetricProvider implements ITransientMetricProvider<BugsEmotionsTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return OverallEmotionsMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsEmotionsTransMetric adapt(DB db) {
		return new BugsEmotionsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsEmotionsTransMetric db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			Iterable<BugTrackerData> bugTrackerDataIt = 
					db.getBugTrackerData().find(BugTrackerData.BUGTRACKERID.eq(bugTracker.getOSSMeterId()));
			BugTrackerData bugTrackerData = null;
			for (BugTrackerData bd:  bugTrackerDataIt) bugTrackerData = bd;
			if (bugTrackerData == null) {
				bugTrackerData = new BugTrackerData();
				bugTrackerData.setBugTrackerId(bugTracker.getOSSMeterId());
				bugTrackerData.setNumberOfComments(0);
				db.getBugTrackerData().add(bugTrackerData);
			}
			bugTrackerData.setNumberOfComments(bugTrackerData.getNumberOfComments() + 
											 bugTrackingSystemDelta.getComments().size());
			
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				
				ClassificationInstance instance = new ClassificationInstance();
				instance.setBugTrackerId(bugTracker.getOSSMeterId());
				instance.setBugId(comment.getBugId());
				instance.setCommentId(comment.getCommentId());
				instance.setText(comment.getText());
				
				String[] emotionalDimensions = 
						EmotionalDimensions.getDimensions(instance).split(",");
				
				for (String dimension: emotionalDimensions) {
					dimension = dimension.trim();
					if (dimension.length()>0) {
						Iterable<Emotion> emotionIt = db.getDimensions().
													find(Emotion.BUGTRACKERID.eq(bugTracker.getOSSMeterId()),
														 Emotion.EMOTIONLABEL.eq(dimension));
						Emotion emotion = null;
						for (Emotion em:  emotionIt) emotion = em;
						if (emotion == null) {
							emotion = new Emotion();
							emotion.setBugTrackerId(bugTracker.getOSSMeterId());
							emotion.setEmotionLabel(dimension);
							emotion.setNumberOfComments(0);
							db.getDimensions().add(emotion);
						}
						emotion.setNumberOfComments(emotion.getNumberOfComments() + 1);
						db.sync();
					}
				}
			}


			Iterable<Emotion> emotionIt = db.getDimensions().
					find(Emotion.BUGTRACKERID.eq(bugTracker.getOSSMeterId()));
			for (Emotion emotion: emotionIt) {
				emotion.setPercentage(
						((float)100*emotion.getNumberOfComments()) 
						/ bugTrackerData.getNumberOfComments());
			}

			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "Bugemotions";
	}

	@Override
	public String getFriendlyName() {
		return "Emotional Dimensions in Bug Comments";
	}

	@Override
	public String getSummaryInformation() {
		return "Emotional Dimensions in Bug Comments";
	}

}
