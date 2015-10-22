package com.analytics.nlp.sentiment.classifiers;

import java.util.HashMap;
import java.util.List;

import com.analytics.nlp.sentiment.loadclassifiermodels.LoadStanfordSentimentModel;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;



public class StanfordSentimentClassifier implements SentimentClassifier {
//
	//@Override
	public String getSentiment(String text) {

		String retLabel = "";

		StanfordCoreNLP pipeline = LoadStanfordSentimentModel.getStanfordModelInstance();
		Annotation document = new Annotation(text);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		HashMap<String, Double> sentimentCountMap = new HashMap<String, Double>();

		for (CoreMap sentence : sentences) {

			/* Tree tree =
			sentence.get(SentimentAnnotatedTree.class);*/
			
			retLabel = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
			if (retLabel.toLowerCase().contains("neg")) {
				retLabel = "Negative";
			}
			if (retLabel.toLowerCase().contains("pos")) {
				retLabel = "Positive";
			}
			Double count = sentimentCountMap.get(retLabel);

			/**
			 * Uncomment the line below to check the sentiments per sentence
			 */
			// System.out.println(sentence + " " + retLabel);

			if (count == null) {
				sentimentCountMap.put(retLabel, 1.0);
			} else {
				sentimentCountMap.put(retLabel, count + 1.0);
			}

			// System.out.println(retLabel+" - " + sentence);

		}

		// System.out.println(sentimentCountMap);
		String finalLabel = decideSentimentByCount(sentimentCountMap, sentences);
		return finalLabel;
	}
	
	static String decideSentimentByCount(HashMap<String, Double> sentimentCountMap, List<CoreMap> sentences) {
		String sentiment = "";
		Double countPos = sentimentCountMap.get("Positive") == null ? 0.0: sentimentCountMap.get("Positive");
		Double countNeg = sentimentCountMap.get("Negative") == null ? 0.0: sentimentCountMap.get("Negative");
		Double countNeutral = sentimentCountMap.get("Neutral") == null ? 0.0: sentimentCountMap.get("Neutral");
		if (countPos == countNeg) {
			sentiment = "Neutral";
		} else if (countPos > countNeg && countPos > countNeutral) {
			sentiment = "Positive";
		} else if (countNeg > countPos && countNeg > countNeutral) {
			sentiment = "Negative";
		} else {
			sentiment = "Neutral";
		}
		return sentiment;

	}

}
