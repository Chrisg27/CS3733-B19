package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.DeleteVideoRequest;
import http.DeleteVideoResponse;
import http.UploadVideoRequest;
import http.UploadVideoResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class DeleteVideoHandlerTest extends LambdaTest{

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("DeleteVideoHandler");

        return ctx;
    }

    @Test
    public void testDeleteVideoHandler() {
    	DeleteVideoRequest req = new DeleteVideoRequest("LambdaTestUploadVideo", "", "", false);
    	DeleteVideoResponse res = new DeleteVideoHandler().handleRequest(req, createContext("DeleteVideoHandler"));
    	Assert.assertEquals("LambdaTestUploadVideo", res.response);

        //String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        //Assert.assertEquals("Hello from Lambda!", output);
    }
}
