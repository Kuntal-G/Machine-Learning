package com.analytics.nlp.sentiment.model;

import com.analytics.nlp.sentiment.classifiers.SentimentDriver;

public class SentimentWrapper {
	
	String text;
	String classifierType;
	String sentimentLabel;

	
	
	public SentimentWrapper(String text, String classifierType){
		this.text=text;
		this.classifierType=classifierType;
	}

	public String getSentimentLabel() {
		return SentimentDriver.getLabel(text, classifierType);
	}
	
	
}
