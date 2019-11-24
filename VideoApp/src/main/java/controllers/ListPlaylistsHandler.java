package controllers;

import java.util.List;

import com.amazonaws.services.lambda.runtime.*;

import dataBase.PlaylistsDAO;
import http.ListPlaylistsResponse;
import model.Playlist;

public class ListPlaylistsHandler implements RequestHandler<Object, ListPlaylistsResponse> {

	public LambdaLogger logger;
	
	/**
	 * Loads playlists from RDS
	 * @return list of playlists from RDS
	 * @throws Exception
	 */
	List<Playlist> getPlaylists() throws Exception {
		logger.log("in getPlaylists");
		PlaylistsDAO dao = new PlaylistsDAO();
		
		return dao.getAllPlaylists();
	}
	
	public ListPlaylistsResponse handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java handler to list all playlists");
		
		ListPlaylistsResponse response;
		try {
			List<Playlist> list = getPlaylists();
			response = new ListPlaylistsResponse(list, 200);
		} catch (Exception e) {
			response = new ListPlaylistsResponse(403, e.getMessage());
		}
		
		return response;
	}

}
