package com.arc.sentiment.model;

public class SentimentResponse {
	String text;
	String classifier;
	String sentimentLabel;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}

	public String getSentimentLabel() {
		return sentimentLabel;
	}

	public void setSentimentLabel(String sentimentLabel) {
		this.sentimentLabel = sentimentLabel;
	}

}
