package com.nlp.example.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.util.Span;

public class ChunkerExample {
	
	private static void usingOpenNLPChunker(String[] sentence) {
        try {
                InputStream posModelStream = new FileInputStream(new File("\\en-pos-maxent.bin"));
                InputStream chunkerStream = new FileInputStream(new File("\\en-chunker.binn"));
                      
                    POSModel model = new POSModel(posModelStream);
                    POSTaggerME tagger = new POSTaggerME(model);
                    
                    // Used to create sample data for trainer
//                    for (String sentence : sentences) {
//                        String sen[] = tokenizeSentence(sentence);
//                        String tags[] = tagger.tag(sen);
//                        for (int i = 0; i < tags.length; i++) {
////                    for (String token : sentence) {
//                            System.out.print(sen[i] + "/" + tags[i] + " ");
//                        }
//                        System.out.println();
//                    }
//                    System.out.println();

                    String tags[] =  tagger.tag(sentence);
                    for (int i = 0; i < tags.length; i++) {
//                    for (String token : sentence) {
                        System.out.print(sentence[i] + "/" + tags[i] + " ");
                    }
                    System.out.println();

                    // chunker
                    System.out.println("------------Chunker -----------");
                    ChunkerModel chunkerModel = new ChunkerModel(chunkerStream);
                    ChunkerME chunkerME = new ChunkerME(chunkerModel);
                    String result[] = chunkerME.chunk(sentence, tags);

                    for (int i = 0; i < result.length; i++) {
                        System.out.println("[" + sentence[i] + "] " + result[i]);
                    }

                    System.out.println("------------Chunker Spans -----------");
                    Span[] spans = chunkerME.chunkAsSpans(sentence, tags);
                    for (Span span : spans) {
                        System.out.print("Type: " + span.getType() + " - " + " Begin: "
                                + span.getStart() + " End:" + span.getEnd()
                                + " Length: " + span.length() + "  [");
                        for (int j = span.getStart(); j < span.getEnd(); j++) {
                            System.out.print(sentence[j] + " ");
                        }
                        System.out.println("]");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

    }


}
