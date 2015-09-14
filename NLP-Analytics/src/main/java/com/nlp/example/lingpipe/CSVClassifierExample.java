package com.nlp.example.lingpipe;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.aliasi.classify.BaseClassifier;
import com.aliasi.classify.Classification;
import com.aliasi.util.AbstractExternalizable;
import com.nlp.example.util.LingpipeUtil;

public class CSVClassifierExample {
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		String inputPath = args.length > 0 ? inputPath = args[0] : "data/disney.csv";	
		String classifierPath = args.length > 1 ? args[1] : "models/3LangId.LMClassifier";
		@SuppressWarnings("unchecked")
		BaseClassifier<CharSequence> classifier 
			= (BaseClassifier<CharSequence>) 
				AbstractExternalizable.readObject(new File(classifierPath));
		List<String[]> lines = LingpipeUtil.readCsvRemoveHeader(new File(inputPath));
		for(String [] line: lines) {
			String text = line[LingpipeUtil.TEXT_OFFSET];
			Classification classified = classifier.classify(text);
			System.out.println("InputText: " + text);
			System.out.println("Best Classified Language: " + classified.bestCategory());
		}
	}

}
