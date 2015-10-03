package com.nlp.sentiment.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Configuration {
  
  private static String RESOURCE_HOME;
  public static String SENTENCE_MODEL;
  public static String LINGPIPE_MODEL;
  private static String CONF_FILE_PATH="./conf/configuration.properties";
  
  public static HashMap<String, String> CONFIGURATION_MAP;

	private static Configuration CONFIGURATION = null;

	private Configuration() {
		loadConfigurationFile();
		setValues();
	}

	public static void create() {
		if (CONFIGURATION == null) {
			System.out
					.println("Creating Sentiment Analysis Configuration for the First Time !!!!");
			CONFIGURATION = new Configuration();
		}
	}

	private void loadConfigurationFile() {
		CONFIGURATION_MAP = new HashMap<String, String>();
		try {
			//InputStream is = Configuration.class.getResourceAsStream("conf/configuration.properties");
			
			BufferedReader br = new BufferedReader(new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/configuration.properties")));
			String line = "";
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(":");
				String ATTR = parts[0].trim();
				String VALUE = parts[1].trim();
				CONFIGURATION_MAP.put(ATTR, VALUE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setValues() {
		RESOURCE_HOME=CONFIGURATION_MAP.get("RESOURCE_HOME");
		SENTENCE_MODEL=RESOURCE_HOME+CONFIGURATION_MAP.get("SENTENCE_MODEL");
		LINGPIPE_MODEL=RESOURCE_HOME+CONFIGURATION_MAP.get("LINGPIPE_MODEL");
		
	}
  
}
