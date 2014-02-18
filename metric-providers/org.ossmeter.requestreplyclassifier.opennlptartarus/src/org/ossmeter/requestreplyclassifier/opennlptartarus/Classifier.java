package org.ossmeter.requestreplyclassifier.opennlptartarus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.apache.commons.lang.time.DurationFormatUtils;
import org.ossmeter.requestreplyclassifier.opennlptartarus.ClassificationInstance;
import org.ossmeter.requestreplyclassifier.opennlptartarus.FeatureGenerator;

import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Classifier {

	List<ClassificationInstance> classificationInstanceList;
	Map<String, String> classificationResults; 

	private static Set<String> keptPoS;
	
	public Classifier() {
		keptPoS = new HashSet<String>();
		keptPoS.add("N");
		keptPoS.add("J");
		classificationInstanceList = new ArrayList<ClassificationInstance>();
	}

	public int instanceListSize() {
		return classificationInstanceList.size();
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

		FeatureGenerator featureGenerator = new FeatureGenerator(
				"classifierFiles/lemmaFeaturesList", 
				"classifierFiles/empiricalFeaturesList", keptPoS);

//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"initialised featureGenerator");

//		long taggerTime = 0; 
		for (ClassificationInstance xmlItem: classificationInstanceList) {
				featureGenerator.updateData(xmlItem.getComposedId(), 
												xmlItem.getCleanTokenSentences());
//				currentTime = System.currentTimeMillis();
//				taggerTime += (currentTime - previousTime);
//				previousTime = currentTime;
		}
//		System.err.println(time(taggerTime) + "\t" + "tagger time");
		
//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"updated featureGenerator");

		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
		if (path.endsWith("bin/"))
			path = path.substring(0, path.lastIndexOf("bin/"));
		try {
			featureGenerator.generateFeatures(classificationInstanceList, path+"classifierFiles/test.arff");
		} catch (IOException e) {
			System.out.println("Cannot write feature file.");
			e.printStackTrace();
		}
		
//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"generated features");

		FilteredClassifier fc = null;
 		try {
			fc = (FilteredClassifier) weka.core.SerializationHelper.read(path+"classifierFiles/filteredClassifier-OpenNLPTartarus.model");
		} catch (Exception e) {
			System.err.println("Cannot load filtered classifier.");
			e.printStackTrace();
		}
		
//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"loaded filtered classifier");

		DataSource testSource = null;
		try {
			testSource = new DataSource(path+"classifierFiles/test.arff");
		} catch (Exception e1) {
			System.out.println("Cannot read feature file.");
			e1.printStackTrace();
		}

//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"loaded test source");

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
		
//		previousTime = printTimeMessage(startTime, previousTime, instanceListSize(), 
//										"classification finished");
//		return previousTime;
	}

//	private long printTimeMessage(long startTime, long previousTime, int size, String message) {
//		long currentTime = System.currentTimeMillis();
//		System.err.println(time(currentTime - previousTime) + "\t" +
//						   time(currentTime - startTime) + "\t" +
//						   size + "\t" + message);
//		return currentTime;
//	}

//	private String time(long timeInMS) {
//		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
//	}

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
