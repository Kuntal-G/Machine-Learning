package org.mini.watson.service;

import java.io.FileReader;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;

import java.util.HashMap;

public class WatsonCsvMap {
	
	private String filePath;
	private char separator = ',';
	private HashMap<String, HashMap<String, String>> csvMap ;
	
	public HashMap<String, HashMap<String, String>> getCourseMap() {
		return csvMap;
	}
	
	public WatsonCsvMap(String filePath,char separator)	{
		this.filePath = filePath;
		this.separator = separator;
		csvMap = new HashMap<String, HashMap<String, String>>();
		readCsvFile();
		
	}
	
	
	public void readCsvFile(){
		CSVReader reader = null;
		HashMap<String, String> keys;
		try
		{
			//Get the CSVReader instance with specifying the delimiter to be used
			reader = new CSVReader(new FileReader(filePath),separator);
			String [] nextLine;
			String columnName[];
			if((columnName = reader.readNext()) != null){}//read the colummns name
			//Read one line at a time
			while ((nextLine = reader.readNext()) != null)
			{
				String courseName = nextLine[0].toLowerCase();
				keys = new HashMap<String, String>();
				for(int i=1;i<columnName.length;i++){
					keys.put(columnName[i].toLowerCase(), nextLine[i].toLowerCase());
				}
				//System.out.println("Name: "+courseName+"  Keys:"+keys);
				csvMap.put(courseName, keys);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
