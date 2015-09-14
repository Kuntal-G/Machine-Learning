package com.nlp.example.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import opennlp.tools.postag.MutableTagDictionary;
import opennlp.tools.postag.POSDictionary;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerFactory;

public class POSDictionaryExample {
	
	
	 private static void usingThePOSDictionary() {
	        try {
	        	InputStream modelIn = new FileInputStream( new File( "en-pos-maxent.bin"));
	            POSModel model = new POSModel(modelIn);
	            POSTaggerFactory posTaggerFactory = model.getFactory();
	            MutableTagDictionary tagDictionary = (MutableTagDictionary) posTaggerFactory.getTagDictionary();

	            String tags[] = tagDictionary.getTags("force");
	            for (String tag : tags) {
	                System.out.print("/" + tag);
	            }
	            System.out.println();

	            System.out.println("Old Tags");
	            // Add old tags first
	            String newTags[] = new String[tags.length + 1];
	            for (int i = 0; i < tags.length; i++) {
	                newTags[i] = tags[i];
	            }
	            newTags[tags.length] = "newTag";
//	            String oldTags[] = tagDictionary.put("force", "newTag");
	            String oldTags[] = tagDictionary.put("force", newTags);
	            // Display old tags
	            for (String tag : oldTags) {
	                System.out.print("/" + tag);
	            }
	            System.out.println();
	            System.out.println("New Tags");
	            // Display new tags
	            tags = tagDictionary.getTags("force");
	            for (String tag : tags) {
	                System.out.print("/" + tag);
	            }
	            System.out.println();

//	            Creating a new Factory with dictionary
	            System.out.println();
	            POSTaggerFactory newFactory = new POSTaggerFactory();
	            newFactory.setTagDictionary(tagDictionary);
	            tags = newFactory.getTagDictionary().getTags("force");
	            for (String tag : tags) {
	                System.out.print("/" + tag);
	            }
	            System.out.println();
	            tags = newFactory.getTagDictionary().getTags("bill");
	            for (String tag : tags) {
	                System.out.print("/" + tag);
	            }
	            System.out.println();
	            createDictionary();
	        	
	        }catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    private static void createDictionary() {
	        try {
	        	InputStream dictionaryIn = new FileInputStream( new File("dictionary.txt")); 
	            POSDictionary dictionary = POSDictionary.create(dictionaryIn);
	            Iterator<String> iterator = dictionary.iterator();
	            while (iterator.hasNext()) {
	                String entry = iterator.next();
	                String tags[] = dictionary.getTags(entry);
	                System.out.print(entry + " ");
	                for (String tag : tags) {
	                    System.out.print("/" + tag);
	                }
	                System.out.println();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


}
