package com.nlp.example.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

public class SentenceDetectorExample {
	
	public static void main(String[] args) {
		 String  paragraph = "  This sentence starts with spaces and ends with " +
              "spaces  . This sentence has no spaces between the next " +
              "one.This is the next one.";
		 SentenceDetectorExample.basicOpenNLPSentenceDetector(paragraph);
		 SentenceDetectorExample.basicOpenNLPSentenceDetectorFactory(paragraph);
		 SentenceDetectorExample.basicOpenNLPSentPosDetectMethod(paragraph);
		 
		 
	}

	
	private static void basicOpenNLPSentenceDetector(String paragraph) {
        try {
        	
        	InputStream is = new FileInputStream(new File( "en-sent.bin")) ;
            SentenceModel model = new SentenceModel(is);
            SentenceDetectorME detector = new SentenceDetectorME(model);
            String sentences[] = detector.sentDetect(paragraph);
            for (String sentence : sentences) {
                System.out.println(sentence);
            }
            double probablities[] = detector.getSentenceProbabilities();
            for (double probablity : probablities) {
                System.out.println(probablity);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
	private static void basicOpenNLPSentPosDetectMethod(String paragraph) {
		 try {
	        	
	        	InputStream is = new FileInputStream(new File( "en-sent.bin")) ;
            SentenceModel model = new SentenceModel(is);
            SentenceDetectorME detector = new SentenceDetectorME(model);
            Span spans[] = detector.sentPosDetect(paragraph);
            for (Span span : spans) {
                System.out.println(span);
            }
            for (Span span : spans) {
                System.out.println(span + "["
                        + paragraph.substring(span.getStart(), span.getEnd())
                        + "]");
            }
            for (Span span : spans) {
                System.out.println(span.getType());
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void basicOpenNLPSentenceDetectorFactory(String paragraph) {
           try {
        	
        	InputStream is = new FileInputStream(new File( "en-sent.bin")) ;
            SentenceModel model = new SentenceModel(is);
            SentenceDetectorME detector = new SentenceDetectorME(model);
            String sentences[] = detector.sentDetect(paragraph);
            for (String sentence : sentences) {
                System.out.println(sentence);
            }
            double probablities[] = detector.getSentenceProbabilities();
            for (double probablity : probablities) {
                System.out.println(probablity);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
	
}
