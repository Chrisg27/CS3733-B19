 package controllers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import dataBase.VideosDAO;
import http.PlaylistRequest;
import http.PlaylistResponse;
import http.PlaylistVideoRequest;
import http.PlaylistVideoResponse;
import model.VideoClip;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AppendAndRemovePlaylistVideoHandlerTest extends LambdaTest {

	 @Test
	    public void testAppendAndRemovePlaylistVideos() throws Exception {
	    	//create a sample playlist to add videos to
	    	PlaylistRequest pr = new PlaylistRequest("LambdaTestPlaylist");
	    	PlaylistResponse pResponse = new CreatePlaylistHandler().handleRequest(pr, createContext("context"));
	    	Assert.assertEquals("LambdaTestPlaylist", pResponse.response);
	    	
	    	//add a "video" to the database
	    	VideosDAO dao = new VideosDAO();
	    	dao.addVideoClip(new VideoClip("lambda_video_url", "", "", false));
	    	
	    	//try to add video to playlist
	    	PlaylistVideoRequest pvr = new PlaylistVideoRequest("LambdaTestPlaylist", "lambda_video_url");
	    	PlaylistVideoResponse pvResponse = new AppendPlaylistVideoHandler().handleRequest(pvr, createContext("append"));
	    	Assert.assertEquals("LambdaTestPlaylist, lambda_video_url", pvResponse.response);
	    	Assert.assertEquals(200, pvResponse.httpCode);
	    	
	    	//try to add video to playlist again
	    	pvResponse = new AppendPlaylistVideoHandler().handleRequest(pvr, createContext("create"));
	    	Assert.assertEquals("LambdaTestPlaylist, lambda_video_url", pvResponse.response);
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//try to add video to playlist that does not exist
	    	PlaylistVideoRequest pvr2 = new PlaylistVideoRequest("PlaylistNotExist", "lambda_video_url");
	    	pvResponse = new AppendPlaylistVideoHandler().handleRequest(pvr2, createContext("append"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//remove a video from a playlist
	    	pvResponse = new RemovePlaylistVideoHandler().handleRequest(pvr, createContext("delete"));
	    	Assert.assertEquals("LambdaTestPlaylist, lambda_video_url", pvResponse.response);
	    	Assert.assertEquals(pvResponse.httpCode, 200);
	    	
	    	//remove a video from a playlist that does not exist
	    	pvResponse = new RemovePlaylistVideoHandler().handleRequest(pvr2, createContext("delete"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//delete the playlist from database
	    	pResponse = new DeletePlaylistHandler().handleRequest(pr, createContext("delete"));
	    	
	    	dao.deleteVideoClip(new VideoClip("lambda_video_url", "", "", false));
	    }
	
}
