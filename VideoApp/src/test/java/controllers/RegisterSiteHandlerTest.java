package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.RegisterSiteRequest;
import http.RegisterSiteResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class RegisterSiteHandlerTest extends LambdaTest {

    @Test
    public void testRegisterAndUnregisterSite() {
    	//register a new site
    	RegisterSiteRequest rsr = new RegisterSiteRequest("lambda_test_url");
    	RegisterSiteResponse response = new RegisterSiteHandler().handleRequest(rsr, createContext("create"));
    	Assert.assertEquals("lambda_test_url", response.response);
    	
    	//try to register site again and fail
    	response = new RegisterSiteHandler().handleRequest(rsr, createContext("create"));
    	Assert.assertEquals(422, response.httpCode);
    	
    	//unregister site
    	response = new UnregisterSiteHandler().handleRequest(rsr, createContext("delete"));
    	Assert.assertEquals("lambda_test_url", response.response);
    	
    	//try to unregister again and fail
    	response = new UnregisterSiteHandler().handleRequest(rsr, createContext("delete"));
    	Assert.assertEquals(422, response.httpCode);
    }
}
