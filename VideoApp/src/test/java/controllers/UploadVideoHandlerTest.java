package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.DeleteVideoResponse;
import http.UploadVideoRequest;
import http.UploadVideoResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class UploadVideoHandlerTest extends LambdaTest{

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("UploadVideoHandler");

        return ctx;
    }

    @Test
    public void testUploadVideo() {
    	UploadVideoRequest req = new UploadVideoRequest("LambdaTestUploadVideo", "", "", "");
    	UploadVideoResponse res = new UploadVideoHandler().handleRequest(req, createContext("UploadVideoHandler"));
    	Assert.assertEquals("LambdaTestUploadVideo", res.response);
    	
    	//res = new UploadVideoHandler().handleRequest(req, createContext("create"));
		//Assert.assertEquals(422, res.httpCode);
		
		//UploadVideoResponse deleteResponse = new DeleteVideoHandler().handleRequest(req, createContext("delete"));
		//Assert.assertEquals("LambdaTestUploadVideo", deleteResponse.response);
    	
    	
        //UploadVideoHandler handler = new UploadVideoHandler();
        //Context ctx = createContext();

        //String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        //Assert.assertEquals("Hello from Lambda!", output);
    }
}
