package controllers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.PlaylistsDAO;
import http.PlaylistRequest;
import http.PlaylistResponse;
import model.Playlist;

public class DeletePlaylistHandler implements RequestHandler<PlaylistRequest, PlaylistResponse> {
	
	LambdaLogger logger = null;

    @Override
    public PlaylistResponse handleRequest(PlaylistRequest req, Context context) {
    	logger = context.getLogger();
    	logger.log("Loading Java handler to delete playlist");
    	
    	PlaylistResponse response = null;
    	logger.log(req.toString());
        
    	PlaylistsDAO dao = new PlaylistsDAO();
    	
    	Playlist playlist = new Playlist(req.name);
    	
    	try  {
    		if (dao.deletePlaylist(playlist)) {
    			response = new PlaylistResponse(req.name, 200);
    		}
    		else {
    			response = new PlaylistResponse(req.name, 422, "Unable to delete playlist");
    		}
    	} catch (Exception e) {
    		response = new PlaylistResponse(req.name, 403, "Unable to delete playlist: " + req.name + "(" + e.getMessage() + ")");		
    	}
    	
    	return response;
    }

}
