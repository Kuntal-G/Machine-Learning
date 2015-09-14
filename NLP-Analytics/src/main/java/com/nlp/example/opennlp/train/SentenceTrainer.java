package com.nlp.example.opennlp.train;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.sentdetect.SentenceDetectorEvaluator;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class SentenceTrainer {
	
	
	public static void main(String[] args) {
		
		String  paragraph = "  This sentence starts with spaces and ends with " +
	              "spaces  . This sentence has no spaces between the next " +
	              "one.This is the next one.";
		SentenceTrainer.basicOpenNLPSentenceTrainer(paragraph);
	}
	
	private static void basicOpenNLPSentenceTrainer(String paragraph) {
//      Charset charset = Charset.forName("UTF-8");
      try {
          ObjectStream<String> lineStream  = new PlainTextByLineStream(new FileReader("sentence.train"));
          ObjectStream<SentenceSample> sampleStream  = new SentenceSampleStream(lineStream);
//          SentenceDetectorFactory sdf = new SentenceDetectorFactory();
//              model = SentenceDetectorME.train("en",
//                      sampleStream,
//                      sdf,
//                      TrainingParameters.defaultParams());
          SentenceModel model = SentenceDetectorME.train(
                  "en", sampleStream, true,
                  null, TrainingParameters.defaultParams());
          OutputStream modelStream = new BufferedOutputStream(new FileOutputStream("modelFile")  );
          model.serialize(modelStream);
          lineStream.close();
          modelStream.close();
          sampleStream.close();

          //
          SentenceDetectorME detector = null;
          try {
        	  InputStream is = new FileInputStream(new File("modelFile"));
              model = new SentenceModel(is);
              detector = new SentenceDetectorME(model);
              String sentences[] = detector.sentDetect(paragraph);
              for (String sentence : sentences) {
                  System.out.println(sentence);
              }
          } catch (FileNotFoundException ex) {
              ex.printStackTrace();
              // Handle exception
          } catch (IOException ex) {
              ex.printStackTrace();
              // Handle exception
          }

          // Evaluate the model
          lineStream
                  = new PlainTextByLineStream(new FileReader("evalSample"));
          sampleStream
                  = new SentenceSampleStream(lineStream);
          SentenceDetectorEvaluator sentenceDetectorEvaluator = new SentenceDetectorEvaluator(detector, null);
          sentenceDetectorEvaluator.evaluate(sampleStream);
          System.out.println(sentenceDetectorEvaluator.getFMeasure());
      } catch (FileNotFoundException ex) {
          ex.printStackTrace();
      } catch (IOException ex) {
          ex.printStackTrace();
      }
  }

}
