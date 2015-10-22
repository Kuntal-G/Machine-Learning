package com.analytics.nlp.sentiment.loadclassifiermodels;

import java.io.File;
import java.io.IOException;

import com.aliasi.classify.LMClassifier;
import com.aliasi.util.AbstractExternalizable;
import com.analytics.nlp.sentiment.configuration.Configuration;


/**
 * A singleton class that loads the LingPipe Classifier model
 * @author kuntal
 *
 */
public class LoadLingPipeSentimentModel {
	
	static String MODEL_FILE;;
	private static LMClassifier LM_CLASSIFIER=null;
	private static LoadLingPipeSentimentModel INSTANCE;
	
	
	/**
	 * The private constructor
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private LoadLingPipeSentimentModel() throws ClassNotFoundException, IOException{
		loadModel();
	}
	
	/**
	 * The method that actually loads the binary model in memory
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	
	void loadModel() throws ClassNotFoundException, IOException{
		 Configuration.create();
		 this.MODEL_FILE=Configuration.LINGPIPE_MODEL;
		 LM_CLASSIFIER = (LMClassifier)AbstractExternalizable.readObject(new File(MODEL_FILE));
	}
	
	/**
	 * returns singleton instance of the class 
	 * @return
	 */
	
	public static LMClassifier getClassiferInstance(){
		if(LM_CLASSIFIER==null){
			try{
			System.out.println("LOADING OPN-FACT CLASSIFER FOR THE FIRST TIME !!!");	
			INSTANCE  = new LoadLingPipeSentimentModel();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return LM_CLASSIFIER;
	}

}
