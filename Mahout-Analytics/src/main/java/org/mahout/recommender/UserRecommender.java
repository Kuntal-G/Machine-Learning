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

public class UserRecommender {
	
	 public static void main(String[] args) throws TasteException, IOException {
		// specifying the user id to which the recommendations have to be generated for
		 int userId=6;

		//specifying the number of recommendations to be generated
		 int noOfRecommendations=5;

		//Get the dataset using FileData Model
		DataModel model = new FileDataModel(new File("/home/kuntal/knowledge/IDE/workspace/MahoutTest/data/rating.csv"));

		//Use a pearson similarity algorithm
		UserSimilarity similarity = new PearsonCorrelationSimilarity (model);

		/*NearestNUserNeighborhood is preferred in situations where we need to have control on the exact no of neighbors*/
		UserNeighborhood neighborhood = new NearestNUserNeighborhood (10, similarity, model);

		/*Initalizing the recommender */
		Recommender recommender = new GenericUserBasedRecommender ( model, neighborhood, similarity);

		//calling the recommend method to generate recommendations
		List<RecommendedItem> recommendations = recommender.recommend(userId, noOfRecommendations);

		for (RecommendedItem recommendedItem : recommendations) {
		System.out.println("Recommended Movie Id: "+recommendedItem.getItemID()+"  .Strength of Preference: "+recommendedItem.getValue());
		  }

		  }
		}