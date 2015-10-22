package com.analytics.nlp.sentiment.classifiers;

import com.aliasi.classify.LMClassifier;
import com.analytics.nlp.sentiment.loadclassifiermodels.LoadLingPipeSentimentModel;


public class LingPipeSentimentClassifier implements SentimentClassifier{

	public String getSentiment(String text) {
		text = text.toLowerCase();
		String retLabel = "";
		LMClassifier mClassifier = LoadLingPipeSentimentModel.getClassiferInstance();
		String label = mClassifier.classify(text).bestCategory();
		if(label.toLowerCase().contains("neg")){
			retLabel="Negative";
		}
		if (label.toLowerCase().contains("pos")) {
			retLabel = "Positive";
		}
		return retLabel;
	}

}
