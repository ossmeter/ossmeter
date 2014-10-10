package org.ossmeter.contentclassifier.opennlptartarus.libsvm.featuremethods;

import org.ossmeter.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;

public class CleanQuestionMarkMethod {

	public static int predict(ClassificationInstance xmlResourceItem) {
		if (xmlResourceItem.getCleanText().contains("?"))
			return 1;	//	"Request"
		else
			return 0;	//	"Reply"
	}

}
