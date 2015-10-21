package com.analytics.recommender;

import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.common.ClassUtils;



public final class RecommenderSingleton {

	  private final Recommender recommender;

	  private static RecommenderSingleton instance;

	  public static synchronized RecommenderSingleton getInstance() {
	    if (instance == null) {
	      throw new IllegalStateException("Not initialized");
	    }
	    return instance;
	  }

	  public static synchronized void initializeIfNeeded(String recommenderClassName) {
	    if (instance == null) {
	      instance = new RecommenderSingleton(recommenderClassName);
	    }
	  }

	  private RecommenderSingleton(String recommenderClassName) {
	    if (recommenderClassName == null) {
	      throw new IllegalArgumentException("Recommender class name is null");
	    }
	    recommender = ClassUtils.instantiateAs(recommenderClassName, Recommender.class);
	  }

	  public Recommender getRecommender() {
	    return recommender;
	  }

	}
