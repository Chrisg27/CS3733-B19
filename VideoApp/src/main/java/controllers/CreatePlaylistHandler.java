package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.PlaylistsDAO;
import http.PlaylistRequest;
import http.PlaylistResponse;
import model.Playlist;

public class CreatePlaylistHandler implements RequestHandler<PlaylistRequest,PlaylistResponse> {
	
	LambdaLogger logger;

	/**
	 * creates a new playlist given a name
	 * @param name the name of the playlist
	 * @return boolean indicating whether playlist was able to be created
	 * @throws Exception
	 */
	boolean createPlaylist(String name) throws Exception {
		if (logger != null) { logger.log("in createPlaylist");}
		PlaylistsDAO dao = new PlaylistsDAO();
		
		Playlist exist = dao.getPlaylist(name);
		Playlist playlist = new Playlist(name);
		if (exist == null) {
			return dao.addEmptyPlaylist(playlist);
		}
		else {
			return false;
		}
	}
	
    @Override
    public PlaylistResponse handleRequest(PlaylistRequest req, Context context) {
    	logger = context.getLogger();
    	logger.log("Loading Java handler to create a playlist");
    	
    	PlaylistResponse response;
    	logger.log(req.toString());
    	
    	try {
    		if (createPlaylist(req.name)) {
    			response = new PlaylistResponse(req.name, 200);
    		}
    		else {
    			response = new PlaylistResponse(req.name, 422);
    		}
    	} catch (Exception e) {
    		response = new PlaylistResponse("Unable to create playlist: " + req.name + "(" + e.getMessage() + ")", 400);
    	}
    	
    	return response;
    }

}
