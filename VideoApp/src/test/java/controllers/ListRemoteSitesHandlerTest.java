package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.ListRemoteSitesResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListRemoteSitesHandlerTest extends LambdaTest {

	 @Test
	    public void testListRemoteSitesHandler() throws IOException {
	        ListRemoteSitesHandler handler = new ListRemoteSitesHandler();
	        
	        ListRemoteSitesResponse resp = handler.handleRequest(null, createContext("list"));
	    
	        Assert.assertEquals(200, resp.statusCode);
	    }
	
}
