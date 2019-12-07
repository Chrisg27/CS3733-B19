package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.PlaylistsDAO;
import dataBase.VideosDAO;
import http.PlaylistVideoRequest;
import http.PlaylistVideoResponse;
import model.Playlist;
import model.VideoClip;

public class AppendPlaylistVideoHandler implements RequestHandler<PlaylistVideoRequest, PlaylistVideoResponse> {

	LambdaLogger logger;
	
	boolean appendVideo(String playlistName, String videoUrl) throws Exception {
		if (logger != null) { logger.log("in appendVideo"); }
		PlaylistsDAO daoPlaylist = new PlaylistsDAO();
		VideosDAO daoVideo = new VideosDAO();
		
		Playlist existPlaylist = daoPlaylist.getPlaylist(playlistName);
		VideoClip existVideo = daoVideo.getVideoClip(videoUrl);
		
		if (existPlaylist != null && existVideo != null) {
			return daoPlaylist.addVideoClipToPlaylist(existPlaylist, existVideo);
		}
		else {
			return false;
		}
	}
	
	@Override
    public PlaylistVideoResponse handleRequest(PlaylistVideoRequest req, Context context) {
    	logger = context.getLogger();
    	logger.log("Loading Java handler to append video to playlist");
    	
    	PlaylistVideoResponse response;
    	logger.log(req.toString());
    	
    	try {
    		if(appendVideo(req.playlist, req.videoUrl)) {
    			response = new PlaylistVideoResponse(req.playlist, 200);
    		}
    		else {
    			response = new PlaylistVideoResponse(req.playlist, 422);
    		}
    	} catch (Exception e) {
    		response = new PlaylistVideoResponse("Unable to append video to playlist: " + req.playlist + "(" + e.getMessage() + ")", 400);
    	}
    	
    	return response;
    }

}
