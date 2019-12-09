package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import dataBase.VideosDAO;
import http.MarkVideoRequest;
import http.MarkVideoResponse;
import model.VideoClip;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class MarkVideoHandlerTest extends LambdaTest {

    @Test
    public void testMarkVideoHandler() throws Exception {
        //add a new video to the database for testing
    	VideosDAO dao = new VideosDAO();
    	dao.addVideoClip(new VideoClip("lambda-test-url1", " ", " ", true));
    	dao.addVideoClip(new VideoClip("lambda-test-url2", " ", " ", false));
    	
    	//mark a video as true that is already true
    	MarkVideoRequest req1 = new MarkVideoRequest("lambda-test-url1", true);
    	MarkVideoResponse response =  new MarkVideoHandler().handleRequest(req1, createContext("mark"));
    	Assert.assertEquals("lambda-test-url1 true", response.response);
    	//mark a video as false that is true
    	MarkVideoRequest req2 = new MarkVideoRequest("lambda-test-url1", false);
    	response =  new MarkVideoHandler().handleRequest(req2, createContext("mark"));
    	Assert.assertEquals("lambda-test-url1 false", response.response);
    	//mark a video as false that is already false
    	MarkVideoRequest req3 = new MarkVideoRequest("lambda-test-url2", false);
    	response =  new MarkVideoHandler().handleRequest(req3, createContext("mark"));
    	Assert.assertEquals("lambda-test-url2 false", response.response);
    	//mark video as true that is false
    	MarkVideoRequest req4 = new MarkVideoRequest("lambda-test-url2", true);
    	response =  new MarkVideoHandler().handleRequest(req4, createContext("mark"));
    	Assert.assertEquals("lambda-test-url2 true", response.response);
    	//mark a video that does not exist
    	MarkVideoRequest req5 = new MarkVideoRequest("video-not-exist", true);
    	response = new MarkVideoHandler().handleRequest(req5, createContext("mark"));
    	Assert.assertEquals(422, response.httpCode);
    	
    	//remove test videos from database
    	dao.deleteVideoClip(new VideoClip("lambda-test-url1", "", "", false));
    	dao.deleteVideoClip(new VideoClip("lambda-test-url2", "", "", true));
    	
    	
    }
}
