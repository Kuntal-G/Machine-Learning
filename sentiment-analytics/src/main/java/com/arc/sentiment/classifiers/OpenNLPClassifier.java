package com.arc.sentiment.classifiers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class OpenNLPClassifier implements SentimentClassifier {

	public String getSentiment(String text) {
		DoccatModel model=trainModel();
		String retLabel = "";
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);
		double[] outcomes = myCategorizer.categorize(text);
		String category = myCategorizer.getBestCategory(outcomes);

		if (category.equalsIgnoreCase("1")) {
			retLabel="Positive";
		} else {
			retLabel="Negative";
		}

		return null;
	}

	private DoccatModel trainModel(){

		InputStream dataIn = null;
		try {

			dataIn = new FileInputStream("input/tweets.txt");

			ObjectStream lineStream = new PlainTextByLineStream(dataIn,"en");
			ObjectStream sampleStream = new DocumentSampleStream(lineStream);

			// Specifies the minimum number of times a feature must be seen
			int cutoff = 2;
			int trainingIterations = 30;
			DoccatModel model = DocumentCategorizerME.train("en", sampleStream,TrainingParameters.defaultParams());
			return model;
		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (dataIn != null) {
				try {
					dataIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

}
