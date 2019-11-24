package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.ListVideosResponse;
import model.VideoClip;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListVideosHandlerTest extends LambdaTest {

    @Test
    public void testListPlaylistsHandler() throws IOException {
        ListVideosHandler handler = new ListVideosHandler();
        
        ListVideosResponse resp = handler.handleRequest(null, createContext("list"));
    
        Assert.assertEquals(200, resp.statusCode);
    }
}
