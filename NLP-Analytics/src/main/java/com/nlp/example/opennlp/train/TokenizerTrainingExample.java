package com.nlp.example.opennlp.train;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import opennlp.tools.tokenize.TokenSample;
import opennlp.tools.tokenize.TokenSampleStream;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;

public class TokenizerTrainingExample {
	
	public static void main(String[] args) {
		TokenizerTrainingExample.trainTokenizer();
	}
	private static void trainTokenizer() {
        createOpenNLPModel();
        try {
          String  paragraph = "A demonstration of how to train a tokenizer.";
            InputStream modelInputStream = new FileInputStream(new File(".", "mymodel.bin"));
            TokenizerModel model = new TokenizerModel(modelInputStream);
            Tokenizer tokenizer = new TokenizerME(model);
            String tokens[] = tokenizer.tokenize(paragraph);
            for (String token : tokens) {
                System.out.println(token);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void createOpenNLPModel() {
        try {
            ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream("training-data.train"), "UTF-8");
            ObjectStream<TokenSample> sampleStream  = new TokenSampleStream(lineStream);
            TokenizerModel model = TokenizerME.train("en", sampleStream, true);
            BufferedOutputStream modelOutputStream = new BufferedOutputStream( new FileOutputStream(new File("mymodel.bin")));
            model.serialize(modelOutputStream);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
