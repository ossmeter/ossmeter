package org.ossmeter.metricprovider.trans.overallemotionsbugzilla;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.BugzillaData;
import org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.Emotion;
import org.ossmeter.metricprovider.trans.overallemotionsbugzilla.model.OverallEmotionBugs;
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
import org.ossmeter.repository.model.bts.bugzilla.Bugzilla;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.EmotionalDimensions;

import com.mongodb.DB;

public class OverallEmotionsMetricProvider implements ITransientMetricProvider<OverallEmotionBugs>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return OverallEmotionsMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (BugTrackingSystem bugTrackingSystem: project.getBugTrackingSystems()) {
			if (bugTrackingSystem instanceof Bugzilla) return true;
		}
		return false;
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
	public OverallEmotionBugs adapt(DB db) {
		return new OverallEmotionBugs(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, OverallEmotionBugs db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			if (!(bugTrackingSystem instanceof Bugzilla)) continue;
			Bugzilla bugzilla = (Bugzilla) bugTrackingSystem;
			Iterable<BugzillaData> bugzillaDataIt = 
					db.getBugzillas().find(BugzillaData.URL.eq(bugzilla.getUrl()),
									 	   BugzillaData.COMPONENT.eq(bugzilla.getComponent()),
									 	   BugzillaData.PRODUCT.eq(bugzilla.getProduct()));
			BugzillaData bugzillaData = null;
			for (BugzillaData bd:  bugzillaDataIt) bugzillaData = bd;
			if (bugzillaData == null) {
				bugzillaData = new BugzillaData();
				bugzillaData.setUrl(bugzilla.getUrl());
				bugzillaData.setProduct(bugzilla.getProduct());
				bugzillaData.setComponent(bugzilla.getComponent());
				bugzillaData.setNumberOfComments(0);
				db.getBugzillas().add(bugzillaData);
			}
			bugzillaData.setNumberOfComments(bugzillaData.getNumberOfComments() + 
											 bugTrackingSystemDelta.getComments().size());
			
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				
				ClassificationInstance instance = new ClassificationInstance();
				instance.setBugId(comment.getBugId());
				instance.setCommentId(comment.getCommentId());
				instance.setComponent(bugzilla.getComponent());
				instance.setProduct(bugzilla.getProduct());
				instance.setText(comment.getText());
				
				String[] emotionalDimensions = 
						EmotionalDimensions.getDimensions(instance).split(",");
				
				for (String dimension: emotionalDimensions) {
					dimension = dimension.trim();
					if (dimension.length()>0) {
						Iterable<Emotion> emotionIt = db.getDimensions().
													find(Emotion.URL.eq(bugzilla.getUrl()),
														 Emotion.COMPONENT.eq(bugzilla.getComponent()),
														 Emotion.PRODUCT.eq(bugzilla.getProduct()),
														 Emotion.EMOTIONLABEL.eq(dimension));
						Emotion emotion = null;
						for (Emotion em:  emotionIt) emotion = em;
						if (emotion == null) {
							emotion = new Emotion();
							emotion.setUrl(bugzilla.getUrl());
							emotion.setComponent(bugzilla.getComponent());
							emotion.setProduct(bugzilla.getProduct());
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
					find(Emotion.URL.eq(bugzilla.getUrl()),
							Emotion.COMPONENT.eq(bugzilla.getComponent()),
							Emotion.PRODUCT.eq(bugzilla.getProduct()));
			for (Emotion emotion: emotionIt) {
				emotion.setPercentage(
						((float)100*emotion.getNumberOfComments()) 
						/ bugzillaData.getNumberOfComments());
			}

			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "emotionsBugzilla";
	}

	@Override
	public String getFriendlyName() {
		return "Emotional Dimensions in Bugzilla Comments";
	}

	@Override
	public String getSummaryInformation() {
		return "Emotional Dimensions in Bugzilla Comments";
	}

}
