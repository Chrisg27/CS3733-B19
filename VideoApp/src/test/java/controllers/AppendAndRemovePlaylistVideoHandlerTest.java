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
	    	Assert.assertEquals("LambdaTestPlaylist", pvResponse.response);
	    	
	    	//try to add video to playlist again
	    	pvResponse = new AppendPlaylistVideoHandler().handleRequest(pvr, createContext("create"));
	    	Assert.assertEquals("LambdaTestPlaylist", pvResponse.response);
	    	
	    	//try to add video to playlist that does not exist
	    	PlaylistVideoRequest pvr2 = new PlaylistVideoRequest("PlaylistNotExist", "lambda_video_url");
	    	pvResponse = new AppendPlaylistVideoHandler().handleRequest(pvr2, createContext("append"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//try to add video that does not exist to playlist
	    	PlaylistVideoRequest pvr3 = new PlaylistVideoRequest("LambdaTestPlaylist", "fake_video_url");
	    	pvResponse = new AppendPlaylistVideoHandler().handleRequest(pvr3, createContext("append"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//try to add video that does not exist to playlist that does not exist
	    	PlaylistVideoRequest pvr4 = new PlaylistVideoRequest("PlaylistNoExist", "fake_video_url");
	    	pvResponse = new AppendPlaylistVideoHandler().handleRequest(pvr4, createContext("append"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//remove a video that is not in a playlist from that playlist
	    	pvResponse = new RemovePlaylistVideoHandler().handleRequest(pvr3, createContext("delete"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//remove a video from a playlist
	    	pvResponse = new RemovePlaylistVideoHandler().handleRequest(pvr, createContext("delete"));
	    	Assert.assertEquals("LambdaTestPlaylist", pvResponse.response);
	    	
	    	//remove the video again (should fail)
	    	pvResponse = new RemovePlaylistVideoHandler().handleRequest(pvr, createContext("delete"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//remove a video from a playlist that does not exist
	    	pvResponse = new RemovePlaylistVideoHandler().handleRequest(pvr2, createContext("delete"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//remove a video that does not exist from a playlist that does not exist
	    	pvResponse = new RemovePlaylistVideoHandler().handleRequest(pvr4, createContext("delete"));
	    	Assert.assertEquals(422, pvResponse.httpCode);
	    	
	    	//delete the playlist from database
	    	pResponse = new DeletePlaylistHandler().handleRequest(pr, createContext("delete"));
	    	
	    	dao.deleteVideoClip(new VideoClip("lambda_video_url", "", "", false));
	    }
	
}
