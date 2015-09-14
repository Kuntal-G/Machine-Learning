package com.arc.sentiment.service;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.arc.sentiment.model.SentimentRequest;
import com.arc.sentiment.model.SentimentResponse;
import com.arc.sentiment.model.SentimentWrapper;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SentimentService {
	  
	  @POST	  
	  @Path("/sentimentservice")
	  public SentimentResponse getSentiment(SentimentRequest request) throws IOException {
		    
		    SentimentResponse sentimentResponse = new SentimentResponse();
		    sentimentResponse.setClassifier(request.getClassifier());
		    sentimentResponse.setText(request.getText());
		    
		    SentimentWrapper sentimentWrapper = new SentimentWrapper(request.getText(), request.getClassifier());
		   
		    System.out.println(request.getText());
		    System.out.println(request.getClassifier());
		    
		    String sentimentLabel = sentimentWrapper.getSentimentLabel();
		    sentimentResponse.setSentimentLabel(sentimentLabel);
	        return sentimentResponse ;
	  }
	  
	  @GET
	  @Path("/hello")
	  public String hello() throws IOException {
		  return "hello";
	  }
}
