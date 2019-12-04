package controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.PlaylistsDAO;
import http.PlaylistRequest;
import http.ListPlaylistVideosResponse;
import http.ListPlaylistsResponse;
import model.Playlist;
import model.VideoClip;

public class ListPlaylistVideosHandler implements RequestHandler<PlaylistRequest, ListPlaylistVideosResponse> {

    LambdaLogger logger;
    
    List<VideoClip> getPlaylistVideos(String name) throws Exception {
    	logger.log("in getPlaylistVideos");
    	PlaylistsDAO dao = new PlaylistsDAO();
    	Iterator<VideoClip> iterator = dao.getPlaylist(name).getVideoIterator();
    	
    	List<VideoClip> clips = new ArrayList<VideoClip>();
    	
    	while (iterator.hasNext()) {
    		clips.add(iterator.next());
    	}
    	
    	return clips;
    }
	
	@Override
    public ListPlaylistVideosResponse handleRequest(PlaylistRequest req, Context context) {
    	logger = context.getLogger();
		logger.log("Loading Java handler to list all videos in playlist");
		
		ListPlaylistVideosResponse response;
		try {
			List<VideoClip> list = getPlaylistVideos(req.name);
			response = new ListPlaylistVideosResponse(list, 200);
		} catch (Exception e) {
			response = new ListPlaylistVideosResponse(403, e.getMessage());
		}
		
		return response;
    }

}
