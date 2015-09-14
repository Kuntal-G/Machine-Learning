package com.nlp.example.opennlp.train;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class ModelTrainingExample {
	
	
	public static void main(String[] args) {
		ModelTrainingExample.trainingOpenNLDocCatPModel();
	}
	
	 private static void trainingOpenNLDocCatPModel() {
	        DoccatModel model = null;
	        try {
	        	InputStream dataIn = new FileInputStream("en-animal.train");
	        
	                OutputStream dataOut = new FileOutputStream("en-animal.model");
	            ObjectStream<String> lineStream
	                    = new PlainTextByLineStream(dataIn, "UTF-8");
	            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);
	            model = DocumentCategorizerME.train("en", sampleStream);

	            // Save the model
	            OutputStream modelOut = null;
	            modelOut = new BufferedOutputStream(dataOut);
	            model.serialize(modelOut);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


}
