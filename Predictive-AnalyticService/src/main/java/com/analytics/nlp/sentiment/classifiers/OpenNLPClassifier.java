package com.analytics.nlp.sentiment.classifiers;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.analytics.nlp.sentiment.loadclassifiermodels.LoadOpenNlpSentimentModel;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class OpenNLPClassifier implements SentimentClassifier {

	public String getSentiment(String text) {
		String retLabel = "";
		DocumentCategorizerME myCategorizer = LoadOpenNlpSentimentModel.getClassiferInstance();
		double[] outcomes = myCategorizer.categorize(text);
		String category = myCategorizer.getBestCategory(outcomes);

		if (category.equalsIgnoreCase("1")) {
			retLabel="Positive";
		} else {
			retLabel="Negative";
		}

		return null;
	}

}
