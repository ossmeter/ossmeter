package org.ossmeter.requestreplyclassifier.featuremethods;

import org.ossmeter.requestreplyclassifier.ClassificationInstance;

public class CleanQuestionMarkOrWordsMethod {

	public static int predict(ClassificationInstance xmlResourceItem) {
		return combine(
					CleanQuestionMarkMethod.predict(xmlResourceItem), 
					CleanQuestionWordsMethod.predict(xmlResourceItem)
				);
	}
	
	private static int combine(int cleanQmPrediction, int cleanQwPrediction) {
		if (cleanQmPrediction == 1)		//	"Request"
			return cleanQmPrediction;
		return cleanQwPrediction;
	}

}
