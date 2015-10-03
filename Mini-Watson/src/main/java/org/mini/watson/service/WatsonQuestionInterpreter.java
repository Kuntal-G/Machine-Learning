package org.mini.watson.service;

import java.util.HashMap;

public class WatsonQuestionInterpreter {
	
	private String course;
	private String verb;
	private String answer;
	private String noAnswer ="Sorry, I can not find the answer";
	private WatsonCsvMap input;
	
	private HashMap<String, String> keys;
	
	public void printInfo(){
		System.out.println("course: "+course);
		System.out.println("verb:" +verb);
	}
	public String findTheAnswer(){
		printInfo();
		if (input.getCourseMap().containsKey(course)) {
			keys = input.getCourseMap().get(course);
			
			if (keys.containsKey(verb)) {
				return keys.get(verb)+" " +keys.get(answer);
			}
		}
		return noAnswer;
	}
	public void setInput(WatsonCsvMap input) {
		this.input = input;
	}
	public WatsonQuestionInterpreter(){
	}
	public WatsonQuestionInterpreter(String noAnswer){
		this.noAnswer = noAnswer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswer() {
		return answer;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getVerb() {
		return verb;
	}
	public void setVerb(String verb) {
		this.verb = verb;
	}
}