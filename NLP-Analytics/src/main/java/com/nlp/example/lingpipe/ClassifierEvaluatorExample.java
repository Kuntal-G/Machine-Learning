package com.nlp.example.lingpipe;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.aliasi.classify.BaseClassifier;
import com.aliasi.classify.BaseClassifierEvaluator;
import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.util.AbstractExternalizable;
import com.nlp.example.util.LingpipeUtil;

public class ClassifierEvaluatorExample {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		String inputPath = args.length > 0 ? args[0] : "data/disney_e_n.csv";	
		String classifierPath = args.length > 1 ? args[1] : "models/1LangId.LMClassifier";
		@SuppressWarnings("unchecked")
		BaseClassifier<CharSequence> classifier = (BaseClassifier<CharSequence>) AbstractExternalizable.readObject(new File(classifierPath));
		List<String[]> rows = LingpipeUtil.readAnnotatedCsvRemoveHeader(new File(inputPath));
		String[] categories = LingpipeUtil.getCategories(rows);
		boolean storeInputs = false;
		BaseClassifierEvaluator<CharSequence> evaluator 
			= new BaseClassifierEvaluator<CharSequence>(classifier,categories, storeInputs);
		for (String[] row : rows) {
			String truth = row[LingpipeUtil.ANNOTATION_OFFSET];
			String text = row[LingpipeUtil.TEXT_OFFSET];
			Classification classification = new Classification(truth);
			Classified<CharSequence> classified = new Classified<CharSequence>(text,classification);
			evaluator.handle(classified);
		}
		LingpipeUtil.printConfusionMatrix(evaluator.confusionMatrix());
	}

}
