package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import http.PlaylistRequest;
import http.PlaylistResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class CreatePlaylistHandlerTest extends LambdaTest {

	@Test
	public void testCreatePlaylist() {
		//create test playlist
		PlaylistRequest pr = new PlaylistRequest("LambdaTestPlaylist");
		PlaylistResponse createResponse = new CreatePlaylistHandler().handleRequest(pr, createContext("create"));
		Assert.assertEquals("LambdaTestPlaylist", createResponse.response);
		
		//try to add playlit again and fail
		createResponse = new CreatePlaylistHandler().handleRequest(pr, createContext("create"));
		Assert.assertEquals(422, createResponse.httpCode);
		
		//delete test playlist
		PlaylistResponse deleteResponse = new DeletePlaylistHandler().handleRequest(pr, createContext("delete"));
		Assert.assertEquals("LambdaTestPlaylist", deleteResponse.response);
		
	}
	
}
