package org.mahout.recommender;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class IRBasedEvaluation {

	private static final String RECOMMENDATION_INPUT = "test/Recommendations/movie.csv";
	
	public static void main(String[] args) throws TasteException, IOException {
		
		// Evaluation
		DataModel model = new FileDataModel (new File(RECOMMENDATION_INPUT));
				RandomUtils.useTestSeed();

			// IR recommender evaluation
			RecommenderIRStatsEvaluator user_eval = new GenericRecommenderIRStatsEvaluator();
		    RecommenderBuilder user_rb = new RecommenderBuilder() 
		    {
		    public Recommender buildRecommender(DataModel mode) throws TasteException 
		        {
		            UserSimilarity user_ll_sim = new LogLikelihoodSimilarity(mode);
		            UserNeighborhood user_ll_nbhd = new NearestNUserNeighborhood(5, user_ll_sim, mode);
		            return new GenericUserBasedRecommender(mode, user_ll_nbhd, user_ll_sim);
		        }
		    };

		    IRStatistics user_ll_stats = user_eval.evaluate(user_rb, null, model, null, 2, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 0.8);
		    System.out.println("Precision: " + user_ll_stats.getPrecision()+" Recall: " +user_ll_stats.getRecall());	    
	}
}
