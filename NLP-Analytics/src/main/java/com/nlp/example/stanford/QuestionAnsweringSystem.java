package com.nlp.example.stanford;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.nlp.example.stanford.model.President;

import opennlp.tools.tokenize.SimpleTokenizer;



import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.GrammaticalStructureFactory;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.stanford.nlp.trees.TypedDependency;

public class QuestionAnsweringSystem {
	
	public static void main(String[] args) {
		QuestionAnsweringSystem.basicQuestionAnswering();
	}
	
	 private static void basicQuestionAnswering() {
	        String question = "Who is the 32rd president of the United States?";
//	        question = "Who was the 32rd president of the United States?";
//	        question = "The 32rd president of the United States was who?";
//	        question = "The 32rd president is who of the United States?";
//	        question = "What was the 3rd President's party?";
//	        question = "When was the 12th president inaugurated";
//	        question = "Where is the 30th president's home town?";

	        String parserModel = "C:/Current Books/NLP and Java/Models/edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
	        LexicalizedParser lexicalizedParser = LexicalizedParser.loadModel(parserModel);

	        TokenizerFactory<CoreLabel> tokenizerFactory =  PTBTokenizer.factory(new CoreLabelTokenFactory(), "");
	        Tokenizer<CoreLabel> tokenizer  = tokenizerFactory.getTokenizer(new StringReader(question));
	        List<CoreLabel> wordList = tokenizer.tokenize();
	        Tree parseTree = lexicalizedParser.apply(wordList);

	        TreebankLanguagePack tlp = lexicalizedParser.treebankLanguagePack();
	        GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
	        GrammaticalStructure gs = gsf.newGrammaticalStructure(parseTree);
	        List<TypedDependency> tdl = gs.typedDependenciesCCprocessed();
	        System.out.println(tdl);
	        for (TypedDependency dependency : tdl) {
	            System.out.println("Governor Word: [" + dependency.gov() + "] Relation: [" + dependency.reln().getLongName()
	                    + "] Dependent Word: [" + dependency.dep() + "]");
	        }

	        System.out.println();
	        System.out.println("You asked: " + question);
	        for (TypedDependency dependency : tdl) {
	            if ("nominal subject".equals(dependency.reln().getLongName())
	                    && "who".equalsIgnoreCase(dependency.gov().originalText())) {
	                System.out.println("Found Who question --- Governor Word: [" + dependency.gov() + "] Relation: [" + dependency.reln().getLongName()
	                        + "] Dependent Word: [" + dependency.dep() + "]");
	                processWhoQuestion(tdl);
	            } else if ("nominal subject".equals(dependency.reln().getLongName())
	                    && "what".equalsIgnoreCase(dependency.gov().originalText())) {
	                System.out.println("Found What question --- Governor Word: [" + dependency.gov() + "] Relation: [" + dependency.reln().getLongName()
	                        + "] Dependent Word: [" + dependency.dep() + "]");
	            } else if ("adverbial modifier".equals(dependency.reln().getLongName())
	                    && "when".equalsIgnoreCase(dependency.dep().originalText())) {
	                System.out.println("Found When question --- Governor Word: [" + dependency.gov() + "] Relation: [" + dependency.reln().getLongName()
	                        + "] Dependent Word: [" + dependency.dep() + "]");
	            } else if ("adverbial modifier".equals(dependency.reln().getLongName())
	                    && "where".equalsIgnoreCase(dependency.dep().originalText())) {
	                System.out.println("Found Where question --- Governor Word: [" + dependency.gov() + "] Relation: [" + dependency.reln().getLongName()
	                        + "] Dependent Word: [" + dependency.dep() + "]");
	            }
	        }
	    }

	    private static void processWhoQuestion(List<TypedDependency> tdl) {
	        System.out.println("Processing Who Question");
	        List<President> list = createPresidentList();
	        for (TypedDependency dependency : tdl) {
	            if ("president".equalsIgnoreCase(dependency.gov().originalText())
	                    && "adjectival modifier".equals(dependency.reln().getLongName())) {
	                String positionText = dependency.dep().originalText();
	                int position = getOrder(positionText) - 1;
	                System.out.println("The president is " + list.get(position).getName());
	            }
	        }
	    }

	    private static int getOrder(String position) {
	        String tmp = "";
	        int i = 0;
	        while (Character.isDigit(position.charAt(i))) {
	            tmp += position.charAt(i++);
	        }
	        return Integer.parseInt(tmp);
	    }

	    private static List<President> createPresidentList() {
	        ArrayList<President> list = new ArrayList<President>();
	        String line = null;
	        try {
	             FileReader reader = new FileReader("PresidentList");
	                BufferedReader br = new BufferedReader(reader);
	            while ((line = br.readLine()) != null) {
	                SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;
	                String tokens[] = simpleTokenizer.tokenize(line);
	                String name = "";
	                String start = "";
	                String end = "";
	                int i = 0;
	                while (!"(".equals(tokens[i])) {
	                    name += tokens[i] + " ";
	                    i++;
	                }
	                start = tokens[i + 1];
	                end = tokens[i + 3];
	                if (end.equalsIgnoreCase("present")) {
	                    end = start;
	                }
	                list.add(new President(name, Integer.parseInt(start),
	                        Integer.parseInt(end)));
	            }
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	        return list;
	    }

}
