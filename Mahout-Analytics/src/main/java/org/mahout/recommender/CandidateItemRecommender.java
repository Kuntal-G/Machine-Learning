package org.mahout.recommender;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.recommender.SamplingCandidateItemsStrategy;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;

public class CandidateItemRecommender {

	public static void main(String[] args) throws TasteException { 
		
		// Preferences for all users
		FastByIDMap<PreferenceArray> userData=new FastByIDMap<PreferenceArray>();
		
		// Preferences for user 1
		List<Preference> prefsUser1=new ArrayList<Preference>();
		prefsUser1.add(new GenericPreference(1,1,10));
		prefsUser1.add(new GenericPreference(1,4,9));
		
		// Preferences for user 2
		List<Preference> prefsUser2=new ArrayList<Preference>();
		prefsUser2.add(new GenericPreference(2,1,10));
		
		// Preferences for user 3
		List<Preference> prefsUser3=new ArrayList<Preference>();
		prefsUser3.add(new GenericPreference(3,1,10));
		prefsUser3.add(new GenericPreference(3,2,8));
		prefsUser3.add(new GenericPreference(3,3,5));
		
		// Add preferences for all users
		userData.put(1,new GenericUserPreferenceArray(prefsUser1));
		userData.put(2,new GenericUserPreferenceArray(prefsUser2));
		userData.put(3,new GenericUserPreferenceArray(prefsUser3));
		
		DataModel model = new GenericDataModel(userData);
		
		// Get possible unseen preferred items for user 2
		CandidateItemsStrategy strategy=new SamplingCandidateItemsStrategy(1,1);
		  FastIDSet candidateItems=strategy.getCandidateItems(2,new GenericUserPreferenceArray(prefsUser2),model);
			
			for (Long candidateRecommendation : candidateItems) {
			      System.out.println("Candidates: " + candidateRecommendation);
			}
			
			// As result, item 2, 3 and 4 is recommended for user 2, as those are the items which user 2 has not rated but users with similar preferences to user 2 (user 1 and user 2) have rated.
	}

}
