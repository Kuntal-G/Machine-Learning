package org.mini.watson.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class WatsonQuestionTokenizer {
	
	private String tokenized;
	private int biggestNP=0;
	private String line;
	
	public WatsonQuestionInterpreter info = new WatsonQuestionInterpreter();

	public WatsonQuestionTokenizer(String line){
		this.line = line.toLowerCase();
	}
	public void tokenize() throws FileNotFoundException
	{
		tokenized = new String();
		InputStream modelIn= new FileInputStream("/home/kuntal/git/Machine-Learning/Mini-Watson/src/main/resources/model/en-token.bin");
		try {
			TokenizerModel model = new TokenizerModel(modelIn);
			modelIn.close();
			Tokenizer tokenizer = new TokenizerME(model);
			String tokens[] = tokenizer.tokenize(line);
			for(int i=0; i<tokens.length;i++)
			{
				tokenized = tokenized.concat(tokens[i]+" ");
			}
			System.out.println("Tokeninez string--"+tokenized);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				}
				catch (IOException e) {
				}
			}
		}
	}
	public void getNounPhrases(Parse p) {
		if ((p.getType().equals("NP")||p.getType().equals("P"))&& p.toString().length()>biggestNP)
		{
			biggestNP = p.toString().length();
			info.setCourse(p.toString());
		}
		
		info.setVerb("verb");
		info.setAnswer("answer");
		
		
		for (Parse child : p.getChildren()) {
			getNounPhrases(child);
		}
	}
	public void run() throws Exception {
		biggestNP = 0;
		InputStream is = new FileInputStream("/home/kuntal/git/Machine-Learning/Mini-Watson/src/main/resources/model/en-parser-chunking.bin");
		ParserModel model = new ParserModel(is);
		is.close();
		Parser parser = ParserFactory.create(model);
		tokenize();
		Parse topParses[] = ParserTool.parseLine(tokenized, parser, 1);
		for (Parse p : topParses) {
			//p.show();
			getNounPhrases(p);
		}
	}
	public String findTheAnswer(WatsonCsvMap map)
	{
		info.setInput(map);
		return info.findTheAnswer();
	}
	public void print(WatsonCsvMap map,String question) throws FileNotFoundException,Exception{
		for (Map.Entry<String, HashMap<String, String>> entry :	map.getCourseMap().entrySet()) {
			line = (question +" "+ entry.getKey()+"?").toLowerCase();
			run();
			System.out.println("Print in Tokenizer"+entry.getKey()+" : "+findTheAnswer(map));
		}
	}
}
