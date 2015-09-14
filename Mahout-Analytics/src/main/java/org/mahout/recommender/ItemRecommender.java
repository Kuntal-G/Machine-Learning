package org.mahout.recommender;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommender {

	 public static void main(String args[])throws TasteException, IOException   {
		  // specifying the user id to which the recommendations have to be generated for
		  int userId=308;

		   //specifying the number of recommendations to be generated
		  int noOfRecommendations=3;

		   // Data model created to accept the input file
		 FileDataModel dataModel = new FileDataModel(new File("/home/kuntal/knowledge/IDE/workspace/MahoutTest/data/rating.csv"));

		   /*Specifies the Similarity algorithm*/
		  ItemSimilarity itemSimilarity = new PearsonCorrelationSimilarity(dataModel);

		   /*Initalizing the recommender */
		  GenericItemBasedRecommender recommender =new GenericItemBasedRecommender(dataModel, itemSimilarity);

		   //calling the recommend method to generate recommendations
		  List<RecommendedItem> recommendations =recommender.recommend(userId, noOfRecommendations);

		   
		  for (RecommendedItem recommendedItem : recommendations)
		   System.out.println("Recommended Movie Id: "+recommendedItem.getItemID()+"  .Strength of Preference: "+recommendedItem.getValue());


		  }
		}

