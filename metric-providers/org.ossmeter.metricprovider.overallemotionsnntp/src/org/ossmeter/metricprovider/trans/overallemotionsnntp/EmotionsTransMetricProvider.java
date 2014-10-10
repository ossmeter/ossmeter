package org.ossmeter.metricprovider.trans.overallemotionsnntp;

import java.util.Collections;
import java.util.List;

import org.ossmeter.metricprovider.trans.overallemotionsnntp.model.Emotion;
import org.ossmeter.metricprovider.trans.overallemotionsnntp.model.NewsgroupData;
import org.ossmeter.metricprovider.trans.overallemotionsnntp.model.NewsgroupsEmotionsTransMetric;
import org.ossmeter.platform.IMetricProvider;
import org.ossmeter.platform.ITransientMetricProvider;
import org.ossmeter.platform.MetricProviderContext;
import org.ossmeter.platform.delta.ProjectDelta;
import org.ossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.ossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.ossmeter.repository.model.CommunicationChannel;
import org.ossmeter.repository.model.Project;
import org.ossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.ossmeter.sentimentclassifier.opennlptartarus.libsvm.EmotionalDimensions;

import com.mongodb.DB;

public class EmotionsTransMetricProvider implements ITransientMetricProvider<NewsgroupsEmotionsTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return EmotionsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
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
	public NewsgroupsEmotionsTransMetric adapt(DB db) {
		return new NewsgroupsEmotionsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsEmotionsTransMetric db) {
		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		
		for (CommunicationChannelDelta communicationChannelSystemDelta : delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelSystemDelta.getCommunicationChannel();
			if (!(communicationChannel instanceof NntpNewsGroup)) continue;
			NntpNewsGroup newsGroup = (NntpNewsGroup) communicationChannel;
			Iterable<NewsgroupData> newsgroupDataIt = 
					db.getNewsgroups().find(NewsgroupData.URL_NAME.eq(newsGroup.getUrl()));
			NewsgroupData newsgroupData = null;
			for (NewsgroupData ngd:  newsgroupDataIt) newsgroupData = ngd;
			if (newsgroupData == null) {
				newsgroupData = new NewsgroupData();
				newsgroupData.setUrl_name(newsGroup.getUrl());
				newsgroupData.setNumberOfArticles(0);
				db.getNewsgroups().add(newsgroupData);
			}
			newsgroupData.setNumberOfArticles(newsgroupData.getNumberOfArticles() + 
											 communicationChannelSystemDelta.getArticles().size());

			for (CommunicationChannelArticle article: communicationChannelSystemDelta.getArticles()) {

				ClassificationInstance instance = new ClassificationInstance();
				instance.setArticleNumber(article.getArticleNumber());
				instance.setUrl(newsGroup.getUrl());
				instance.setText(article.getText());
				
				String[] emotionalDimensions = 
						EmotionalDimensions.getDimensions(instance).split(",");
				
				for (String dimension: emotionalDimensions) {
					dimension = dimension.trim();
					if (dimension.length()>0) {
						Iterable<Emotion> emotionIt = db.getDimensions().
												find(Emotion.URL_NAME.eq(newsGroup.getUrl()),
													 Emotion.EMOTIONLABEL.eq(dimension));
						
						Emotion emotion = null;
						for (Emotion em: emotionIt) emotion = em;
						if (emotion == null) {
							emotion = new Emotion();
							emotion.setUrl_name(newsGroup.getUrl());
							emotion.setEmotionLabel(dimension);
							emotion.setNumberOfArticles(0);
							db.getDimensions().add(emotion);
						}
						emotion.setNumberOfArticles(emotion.getNumberOfArticles() + 1);
						db.sync();
					}
				}
			}
			
			Iterable<Emotion> emotionIt = db.getDimensions().findByUrl_name(newsGroup.getUrl());
			for (Emotion emotion: emotionIt) {
				emotion.setPercentage(
						((float)100*emotion.getNumberOfArticles()) 
						/ newsgroupData.getNumberOfArticles());
			}

			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "newsgroupemotions";
	}

	@Override
	public String getFriendlyName() {
		return "Emotional Dimensions in Newsgroup Articles";
	}

	@Override
	public String getSummaryInformation() {
		return "Emotional Dimensions in Newsgroup Articles";
	}

}
