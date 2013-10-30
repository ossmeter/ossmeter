package org.ossmeter.requestreplyclassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import uk.ac.nactem.geniatagger.GeniaTaggerSingleton;
import uk.ac.nactem.splitter.EnglishSentenceSplitter;
import uk.ac.nactem.tools.GeniaTagger.GeniaToken;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Classifier {

	private final int MAX_CHARACTERS_IN_WORD = 60;

	List<ClassificationInstance> classificationInstanceList;
	Map<String, String> classificationResults; 

	private static Set<String> keptPoS;
	
	public Classifier() {
		keptPoS = new HashSet<String>();
		keptPoS.add("N");
		keptPoS.add("J");
		classificationInstanceList = new ArrayList<ClassificationInstance>();
	}

	public void add(ClassificationInstance classificationInstance) {
		classificationInstanceList.add(classificationInstance);
	}
	
	public String getClassificationResult(ClassificationInstance classificationInstance) {
		String composedId = classificationInstance.getComposedId();
		if (classificationResults.containsKey(composedId))
			return classificationResults.get(composedId);
		else {
			System.out.println("No classification result found for classificationInstance: " 
									+ classificationInstance.toString());
			return null;
		}
	}
	
	
	public void classify() {

		EnglishSentenceSplitter splitter = new EnglishSentenceSplitter();
		GeniaTaggerSingleton geniatagger = GeniaTaggerSingleton.getInstance();
		
		FeatureGenerator featureGenerator = new FeatureGenerator(
				"classifierFiles/lemmaFeaturesList", 
				"classifierFiles/empiricalFeaturesList", keptPoS);

		for (ClassificationInstance xmlItem: classificationInstanceList) {
			List<int[]> output = null;
			try {
				output = splitter.markupRawText(xmlItem.getText());
			} catch (Exception e) {
				System.err.println("Sentence splitter error.");
				e.printStackTrace();
			}
			for (int[] indexes : output) {
				String cleanedSentence = "",
					   sentence = xmlItem.getText().substring(indexes[2], indexes[3]);
				String[] components = sentence.split("\\s+");
				for (String component: components) {
					if (component.length() < MAX_CHARACTERS_IN_WORD) {
						if (cleanedSentence.length() > 0) cleanedSentence += " ";
						cleanedSentence += component;
					}
				}
				List<GeniaToken> geniaTokens = 
						geniatagger.getTagger().tagToTokens(cleanedSentence);
				featureGenerator.updateData(xmlItem.getComposedId(), geniaTokens);
			}
		}
		
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		try {
			featureGenerator.generateFeatures(classificationInstanceList, path+"classifierFiles/test.arff");
		} catch (IOException e) {
			System.out.println("Cannot write feature file.");
			e.printStackTrace();
		}
		
		FilteredClassifier fc = null;
 		try {
			fc = (FilteredClassifier) weka.core.SerializationHelper.read(path+"classifierFiles/filteredClassifier.model");
		} catch (Exception e) {
			System.err.println("Cannot load filtered classifier.");
			e.printStackTrace();
		}
		
		DataSource testSource = null;
		try {
			testSource = new DataSource(path+"classifierFiles/test.arff");
		} catch (Exception e1) {
			System.out.println("Cannot read feature file.");
			e1.printStackTrace();
		}
		Instances unlabeled = prepareInstances(testSource);
		classificationResults = new HashMap<String, String>();
		for (int i = 0; i < unlabeled.numInstances(); i++) {
			double pred = 0;
			try {
				pred = fc.classifyInstance(unlabeled.instance(i));
			} catch (Exception e) {
				System.err.println("Filtered classifier error.");
				e.printStackTrace();
			}
			classificationResults.put(unlabeled.instance(i).stringValue(0), 
									  unlabeled.classAttribute().value((int) pred));
		}
		
	}

	private static Instances prepareInstances(DataSource source) {
		Instances instances = null;
		try {
			instances = source.getDataSet();
		} catch (Exception e) {
			System.err.println("Cannot read classification data.");
			e.printStackTrace();
		}
		if (instances.classIndex() == -1)
			instances.setClassIndex(instances.numAttributes() - 1);
		return instances;
	}
	
}
