package controllers;

import java.util.List;

import com.amazonaws.services.lambda.runtime.*;

import dataBase.VideosDAO;
import http.ListVideosResponse;
import model.VideoClip;

public class ListVideosHandler implements RequestHandler<Object, ListVideosResponse> {

	public LambdaLogger logger;
	
	/**
	 * Loads local videos from RDS
	 * @return list of videos from RDS
	 * @throws Exception
	 */
	List<VideoClip> getVideos() throws Exception {
		logger.log("in getVideos");
		VideosDAO dao = new VideosDAO();
		
		return dao.getAllVideos();
	}
	
	public ListVideosResponse handleRequest(Object input, Context context) {
		logger = context.getLogger();
		logger.log("Loading Java handler to list all local videos");
		
		ListVideosResponse response;
		try {
			List<VideoClip> list = getVideos();
			response = new ListVideosResponse(list, 200);
		} catch (Exception e) {
			response = new ListVideosResponse(403, e.getMessage());
		}
		
		return response;
	}

}
