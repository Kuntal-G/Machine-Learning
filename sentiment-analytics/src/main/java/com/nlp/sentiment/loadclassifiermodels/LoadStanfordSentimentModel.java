package com.nlp.sentiment.loadclassifiermodels;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

/**
 * A Singleton class that loads the Stanford Sentiment classifier model
 * 
 * @author kuntal
 *
 */
public class LoadStanfordSentimentModel {
	private static StanfordCoreNLP PIPELINE;
	private static LoadStanfordSentimentModel INSTANCE = null;

	private LoadStanfordSentimentModel() {
		loadModel();
	}

	/**
	 * Returns singleton instance of the class
	 * 
	 * @return
	 */
	public static StanfordCoreNLP getStanfordModelInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LoadStanfordSentimentModel();
		}
		return PIPELINE;
	}

	/**
	 * The method loads the binary model in memory
	 */
	void loadModel() {
		System.out.println("LOADING SENTIMENT ANALYZER MODEL FOR THE FIRST TIME !!!!");
		Properties props = new Properties();
		// props.setProperty("annotators","tokenize, ssplit, pos, lemma, parse, sentiment");
		props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
		PIPELINE = new StanfordCoreNLP(props);
	}

}
