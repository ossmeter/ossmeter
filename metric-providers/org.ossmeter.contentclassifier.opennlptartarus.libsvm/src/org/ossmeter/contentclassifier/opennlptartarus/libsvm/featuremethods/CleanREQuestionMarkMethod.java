package org.ossmeter.contentclassifier.opennlptartarus.libsvm.featuremethods;

import org.ossmeter.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;

public class CleanREQuestionMarkMethod {

	public static int predict(ClassificationInstance xmlResourceItem) {
		return combine(
					REMethod.predict(xmlResourceItem), 
					CleanQuestionMarkMethod.predict(xmlResourceItem)
			   );

	}
	
	private static int combine(int rePrediction, int cleanQmPrediction) {
		if (rePrediction == 1)	//	"Request"
			return rePrediction;
		return cleanQmPrediction;
	}

}
