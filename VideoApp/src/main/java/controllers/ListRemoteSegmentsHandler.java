package controllers;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.VideosDAO;
import http.RemoteSegmentsResponse;
import model.VideoClip;

public class ListRemoteSegmentsHandler implements RequestHandler<Object, RemoteSegmentsResponse> {

	LambdaLogger logger;
	
	List<VideoClip> getVideos() throws Exception {
		logger.log("in getVideos");
		VideosDAO dao = new VideosDAO();
		
		List<VideoClip> clips = dao.getAllVideos();
		List<VideoClip> remoteClips = new ArrayList<VideoClip>();
		
		for (int i = 0; i < clips.size(); i++) {
			VideoClip clip = clips.get(i);
			if(!clip.isMarked()) remoteClips.add(clip);
		}
		
		return remoteClips;
		
	}
	
    @Override
    public RemoteSegmentsResponse handleRequest(Object input, Context context) {
        logger = context.getLogger();
        logger.log("Loading Java handler to list remotely available segments");
        
        RemoteSegmentsResponse response;
        try {
        	List<VideoClip> list = getVideos();
        	response = new RemoteSegmentsResponse(list, 200);
        } catch (Exception e) {
        	response = new RemoteSegmentsResponse(403, e.getMessage());
        }
        return response;
    }

}
