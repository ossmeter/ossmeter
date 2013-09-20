package org.ossmeter.requestreplyclassifier;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.ossmeter.requestreplyclassifier.featuremethods.EmpiricalPredictor;

import uk.ac.nactem.tools.GeniaTagger.GeniaToken;

public class FeatureGenerator {
	
	private Features lemmaFeatures;
	private DocumentFeatures documentFeatures;
	private Features empiricalFeatures;
	
    public FeatureGenerator(String lemmaFeaturesFileName,
    						String empiricalFeaturesFileName,
							Set<String> keptPOStagsInFeatures
							) {
		super();
		lemmaFeatures = new Features(lemmaFeaturesFileName);
		documentFeatures = new DocumentFeatures(lemmaFeatures, keptPOStagsInFeatures);
		empiricalFeatures = new Features(empiricalFeaturesFileName);
	}

    public void updateData(String documentId, List<GeniaToken> geniaTokens) {
        for(GeniaToken token: geniaTokens)  {
        	documentFeatures.add(documentId, token.getNormalForm(), token.getPOS());
        }
    }
	
	public void generateFeatures(List<ClassificationInstance> itemList, String filename) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename)); 
        writeFeatures(writer);
//    	int counter = 0;
        int lastFeatureId = lemmaFeatures.getHighestOrder() + 1;
        for (ClassificationInstance xmlResourceItem: itemList) {
        	writeInstanceFeatures(xmlResourceItem, writer);
        	writer.write(lastFeatureId + " ?}\n");
//			counter++;
        }
		writer.newLine();
		writer.flush();
		writer.close();
//		System.out.println(counter + " instances written in file: " + filename);
	}
	
	private void writeInstanceFeatures(ClassificationInstance xmlResourceItem, Writer writer) throws IOException {
		writer.write("{0 \"" + xmlResourceItem.getComposedId() + "\", ");
		for (int order: empiricalFeatures.getSortedOrders()) {
			if (EmpiricalPredictor.predict(empiricalFeatures.getLemma(order), xmlResourceItem) == 1)
				writer.write(order + " 1, ");
		}
		SortedSet<Integer> sortedOrders = documentFeatures.getSortedOrders(xmlResourceItem.getComposedId());
		if (sortedOrders!=null)
			for (int order: sortedOrders) {
				writer.write(order + " " + documentFeatures.getFrequency(xmlResourceItem.getComposedId(), order) + ", ");
		}
	}

	private void writeFeatures(Writer writer) throws IOException {
		writer.write("@relation subdomains\n");
		writer.write("@attribute LABEL string\n");
		for (int order: empiricalFeatures.getSortedOrders()) {
			writer.write("@attribute " + empiricalFeatures.getLemma(order) + " numeric\n");
		}
		for (int order: lemmaFeatures.getSortedOrders()) {
			writer.write("@attribute " + "LEMMA_" + order + ":" + lemmaFeatures.getCleanLemma(order) + " numeric\n");
		}
		writer.write("@attribute subdomain {\"Request\",\"Reply\"}\n");
		writer.write("@data\n");
	}

}
