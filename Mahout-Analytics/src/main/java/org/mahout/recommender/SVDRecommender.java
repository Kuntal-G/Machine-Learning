package org.mahout.recommender;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.svd.ALSWRFactorizer;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;


public class SVDRecommender {
	
	private static final String RECOMMENDATION_INPUT = "test/Recommendations/movie.csv";

	public static void main(String[] args) throws IOException, TasteException {
		// SVD recommender 
	    DataModel svdmodel = new FileDataModel (new File(RECOMMENDATION_INPUT));
		
		ALSWRFactorizer factorizer = new ALSWRFactorizer(svdmodel, 3, 0.065, 1);
		Recommender svdrecommender = new org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender(svdmodel, factorizer);
		
				for (RecommendedItem recommendation :svdrecommender.recommend(3,1))
				{
					System.out.println(recommendation);
				}		
		}
	}


