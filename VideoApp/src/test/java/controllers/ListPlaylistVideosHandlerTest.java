package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.PlaylistRequest;
import http.PlaylistResponse;
import http.ListPlaylistVideosResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ListPlaylistVideosHandlerTest extends LambdaTest {

   @Test
   public void testListPlaylistVideosHandler() {
	   //create test playlist
	   PlaylistRequest pr = new PlaylistRequest("LambdaTestPlaylist");
	   PlaylistResponse pResponse = new CreatePlaylistHandler().handleRequest(pr, createContext("create"));
	   
	   //test list videos
	   ListPlaylistVideosResponse response = new ListPlaylistVideosHandler() .handleRequest(pr, createContext("list"));
	   Assert.assertEquals(200, response.statusCode);
	   
	   //test with playlist that does not exist
	   PlaylistRequest pr2 = new PlaylistRequest("PlaylistNoExist");
	   response = new ListPlaylistVideosHandler().handleRequest(pr2, createContext("list"));
	   Assert.assertEquals(403, response.statusCode);
   }
	
}
