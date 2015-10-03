package com.nlp.sentiment.classifiers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * A class that finds sentiments in text by using the Sentiment140 api
 * @author kuntal
 */

public class Sentiment140Classifier implements SentimentClassifier {

	
	String url = "http://www.sentiment140.com/api/bulkClassifyJson";
	
	
	/**
	 * Creates a Json object of a tweet
	 * @param tweet
	 * @return {@link JsonObject}
	 */
	JsonObject createJsonObject(String tweet) {
		JsonObject value = Json
				.createObjectBuilder()
				.add("language", "auto")
				.add("data",
						Json.createArrayBuilder().add(
								Json.createObjectBuilder().add("text", tweet))
				     )

				.build();
		return value;

	}
	
	/**
	 * Returns sentiment of a text using Sentiment140 api
	 * @param tweet
	 * @return {@link String}
	 */
	
	//@Override
	public String getSentiment(String text) {
		JsonObject json= createJsonObject(text);
		return postJson(url, json);
	}
	
	/**
	 * The main method that interacts with Sentimentapi api using json object over http connection
	 * @param url
	 * @param json
	 * @return {@link String}
	 * @throws IOException
	 */

	 String postJson(String url, JsonObject json)  {
		 
		String sentiment="NA";
		try{
		URL object = new URL(url);
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Accept", "application/json");
		con.setRequestMethod("POST");

		OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		wr.write(json.toString());
		wr.flush();

		StringBuilder sb = new StringBuilder();

		int HttpResult = con.getResponseCode();

		if (HttpResult == HttpURLConnection.HTTP_OK) {

			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "utf-8"));

			String line = null;

			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

			br.close();

			//System.out.println("" + sb.toString());

		} else {
			System.out.println(con.getResponseMessage());
		}

		String response = sb.toString();
		String regex = "\"polarity\":(.)";
		String polarityResponse = "";
		Pattern patt = Pattern.compile(regex);
		Matcher match = patt.matcher(response);
		while (match.find()) {
			polarityResponse = match.group(1);
		}
		
		sentiment = polarityResponse.equals("0")?"Negative":(polarityResponse.equals("2")?"Neutral":"Positive");
		}catch(Exception e){
			e.printStackTrace();
		}
		return sentiment;

	}

	public static void main(String[] args) throws IOException {
		String url = "http://www.sentiment140.com/api/bulkClassifyJson";
		String tweet = "";
		Sentiment140Classifier classifier = new Sentiment140Classifier();
		classifier.url=url;
		System.out.println(classifier.getSentiment(tweet));

	}

	

}
