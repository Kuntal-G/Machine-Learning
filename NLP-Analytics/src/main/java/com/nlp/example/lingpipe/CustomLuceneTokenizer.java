package com.nlp.example.lingpipe;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.io.Serializable;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;

import com.aliasi.tokenizer.Tokenizer;
import com.aliasi.tokenizer.TokenizerFactory;
import com.aliasi.util.AbstractExternalizable;

public class CustomLuceneTokenizer implements TokenizerFactory, Serializable {

	private static final long serialVersionUID = -8376017491713196935L;
	private Analyzer analyzer;
	private String field;

	public CustomLuceneTokenizer(Analyzer analyzer, String field) {
		this.analyzer = analyzer;
		this.field = field;
	}

	private Object writeReplace() {
		return new Serializer(this);
	}

	public Tokenizer tokenizer(char[] charSeq, int start, int length) {
		Reader reader = new CharArrayReader(charSeq, start, length);
		TokenStream tokenStream = null;
		try {
			tokenStream = analyzer.tokenStream(field, reader);
			tokenStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new LuceneTokenStreamTokenizer(tokenStream);
	}

	private static class Serializer extends AbstractExternalizable {
		private CustomLuceneTokenizer latf;

		public Serializer(CustomLuceneTokenizer luceneAnalyzerTokenizerFactory) {
			this.latf = luceneAnalyzerTokenizerFactory;
		}

		@Override
		protected Object read(ObjectInput in) throws ClassNotFoundException,
				IOException {
			Analyzer analyzer = (Analyzer) in.readObject();
			String field = in.readUTF();
			return new CustomLuceneTokenizer(analyzer, field);
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			out.writeObject(latf.analyzer);
			out.writeUTF(latf.field);
		}

	}

	static class LuceneTokenStreamTokenizer extends Tokenizer {

		private TokenStream tokenStream;
		private CharTermAttribute termAttribute;
		private OffsetAttribute offsetAttribute;

		private int lastTokenStartPosition = -1;
		private int lastTokenEndPosition = -1;

		public LuceneTokenStreamTokenizer(TokenStream ts) {
			tokenStream = ts;
			termAttribute = tokenStream.addAttribute(CharTermAttribute.class);
			offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
		}

		@Override
		public String nextWhitespace() {
			return "default";
		}

		@Override
		public String nextToken() {
			try {
				if (tokenStream.incrementToken()) {
					lastTokenStartPosition = offsetAttribute.startOffset();
					lastTokenEndPosition = offsetAttribute.endOffset();
					return termAttribute.toString();
				} else {
					endAndClose();
					return null;
				}
			} catch (IOException e) {
				endAndClose();
				return null;
			}
		}

		@Override
		public int lastTokenStartPosition() {
			return lastTokenStartPosition;
		}

		@Override
		public int lastTokenEndPosition() {
			return lastTokenEndPosition;
		}

		private void endAndClose() {

			try {
				tokenStream.end();
			} catch (IOException e) {
				// Cant do anything with this.
			}
			try {
				tokenStream.close();
			} catch (IOException e) {
				// Cant do anything with this.
			}
		}
	}

	public static void main(String[] args) {
		String text = "Hi how are you? Are the numbers 1 2 3 4.5 all integers?";
		Analyzer analyzer = new StandardAnalyzer();
		TokenizerFactory tokFactory = new CustomLuceneTokenizer(analyzer,"DEFAULT");
		Tokenizer tokenizer = tokFactory.tokenizer(text.toCharArray(), 0,text.length());
		/*
		 * for (String token: tokenizer.tokenize()){
		 * System.out.println("Token: " + token); }
		 */
		String token = null;
		while ((token = tokenizer.nextToken()) != null) {
			String ws = tokenizer.nextWhitespace();
			System.out.println("Token:'" + token + "'");
			System.out.println("WhiteSpace:'" + ws + "'");
		}

	}

}
