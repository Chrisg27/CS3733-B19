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

      @Test
    public void testDeleteVideoHandler() {
    	DeleteVideoRequest req = new DeleteVideoRequest("LambdaTestUploadVideo", "", "", false);
    	DeleteVideoResponse res = new DeleteVideoHandler().handleRequest(req, createContext("DeleteVideoHandler"));
    	Assert.assertEquals("LambdaTestUploadVideo", res.response);
    }
}
