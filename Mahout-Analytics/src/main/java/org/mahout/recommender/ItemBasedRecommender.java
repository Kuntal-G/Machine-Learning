package org.mahout.recommender;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemBasedRecommender {

	private static final String RECOMMENDATION_INPUT = "test/Recommendations/movie.csv";
	
	public static void main(String[] args) throws IOException, TasteException {
	
		// Item recommender 
		DataModel model = new FileDataModel (new File(RECOMMENDATION_INPUT)); 
		
				ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity (model);
				Recommender itemRecommender = new GenericItemBasedRecommender(model,itemSimilarity);
				
				List<RecommendedItem> itemRecommendations =
					    itemRecommender.recommend(3, 2);
				
				for (RecommendedItem itemRecommendation : itemRecommendations) {
				      System.out.println("Item: " + itemRecommendation);
				}
	}

}
