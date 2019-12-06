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

    @Test
    public void testUploadVideo() {
    	UploadVideoRequest req = new UploadVideoRequest("LambdaTestUploadVideo", "", "", "");
    	UploadVideoResponse res = new UploadVideoHandler().handleRequest(req, createContext("upload"));
    	Assert.assertEquals("LambdaTestUploadVideo", res.response);
    }
}
