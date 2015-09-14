package org.mahout.recommender;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserBasedRecommender {
	
	private static final String RECOMMENDATION_INPUT = "test/Recommendations/movie.csv";
	
	public static void main(String[] args) throws IOException, TasteException {
		
		DataModel model = new FileDataModel (new File(RECOMMENDATION_INPUT)); 
		
		// User recommender 
		UserSimilarity similarity = new PearsonCorrelationSimilarity (model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood (2, similarity, model);
    		
		Recommender recommender = new GenericUserBasedRecommender (
			    model, neighborhood, similarity);
		
		List<RecommendedItem> userRecommendations =
			    recommender.recommend(3, 1);
		
		for (RecommendedItem itemRecommendation : userRecommendations) {
		      System.out.println("User: " + itemRecommendation);
		}
		 System.out.println();
		
	}

}
