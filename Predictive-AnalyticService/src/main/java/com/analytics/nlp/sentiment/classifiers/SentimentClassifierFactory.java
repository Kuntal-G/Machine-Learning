package com.analytics.nlp.sentiment.classifiers;

public class SentimentClassifierFactory {

	public SentimentClassifier getSentimentClassifier(String classifierType){
		if(classifierType==null){
			return null;
		}
		
		if(classifierType.isEmpty()){
			return new StanfordSentimentClassifier();
		}
		
		if(classifierType.equalsIgnoreCase("stanford")){
			return new StanfordSentimentClassifier();
		}
		
		if(classifierType.equalsIgnoreCase("lingpipe")){
			return new LingPipeSentimentClassifier();
		}
		
		if(classifierType.equalsIgnoreCase("sentiment140")){
			return new Sentiment140Classifier();
		}
		
		if(classifierType.equalsIgnoreCase("opennlp")){
			return new OpenNLPClassifier();
		}
		return null;
	}
}
