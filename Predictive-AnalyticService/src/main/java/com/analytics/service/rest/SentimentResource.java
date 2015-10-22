package com.analytics.service.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.analytics.nlp.sentiment.model.SentimentRequest;
import com.analytics.nlp.sentiment.model.SentimentResponse;
import com.analytics.nlp.sentiment.model.SentimentWrapper;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class SentimentResource {

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
