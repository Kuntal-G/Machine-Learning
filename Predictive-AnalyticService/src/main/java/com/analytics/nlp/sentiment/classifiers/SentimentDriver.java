package com.analytics.nlp.sentiment.classifiers;


public class SentimentDriver {

	public static void main(String[] args) {

		String text = "The experience was good. Probably the best in my life";

		SentimentClassifierFactory sentimentClassifierFactory = new SentimentClassifierFactory();
		SentimentClassifier sentimentClassifier = sentimentClassifierFactory.getSentimentClassifier("lingpipe");
		String label=sentimentClassifier.getSentiment(text);
		System.out.println(text+" : "+ label);
	}
	
	public static String getLabel(String text, String classifierType){
		SentimentClassifierFactory sentimentClassifierFactory = new SentimentClassifierFactory();
		SentimentClassifier sentimentClassifier = sentimentClassifierFactory.getSentimentClassifier(classifierType);
		String label=sentimentClassifier.getSentiment(text);
		return label;
	}
}
