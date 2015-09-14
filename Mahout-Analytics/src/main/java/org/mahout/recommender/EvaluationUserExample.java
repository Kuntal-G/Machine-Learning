package org.mahout.recommender;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli2.OptionException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class EvaluationUserExample {

	public static void main(String[] args) throws IOException, TasteException, OptionException {

		RecommenderBuilder builder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model) throws TasteException{
				UserSimilarity similarity = new PearsonCorrelationSimilarity (model);
				//Splitting of data(.1) done using 90% in training-set & 10% test-set
				UserNeighborhood neighborhood = new ThresholdUserNeighborhood (.1, similarity, model);
				Recommender recommender = new GenericUserBasedRecommender ( model, neighborhood, similarity);
				return new CachingRecommender(recommender);
			}
		};

		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		DataModel model = new FileDataModel(new File("/home/kuntal/knowledge/IDE/workspace/MahoutTest/data/rating.csv"));
		/*0.9 here represents the percentage of each user’s preferences to use to produce recommendations, the rest are compared to estimated preference values to evaluate.
		 1 represent the percentage of users to use in evaluation (so here all users).*/
		double score = evaluator.evaluate(builder,null, model,0.9,1);

		System.out.println("Result: "+score);
	}
}
