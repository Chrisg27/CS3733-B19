package controllers;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import dataBase.VideosDAO;
import http.RemoteSegmentsResponse;
import model.VideoClip;
import model.Segment;

public class ListRemoteSegmentsHandler implements RequestHandler<Object, RemoteSegmentsResponse> {

	LambdaLogger logger;
	
	List<Segment> getVideos() throws Exception {
		logger.log("in getVideos");
		VideosDAO dao = new VideosDAO();
		
		List<VideoClip> clips = dao.getAllVideos();
		List<Segment> remoteClips = new ArrayList<Segment>();
		
		for (int i = 0; i < clips.size(); i++) {
			VideoClip clip = clips.get(i);
			if(!clip.isMarked()) {
				String url = clip.getClipURL();
				String character = clip.getSpeaker();
				String text = clip.getAssociatedText();
				Segment segment = new Segment(url, character, text);
				remoteClips.add(segment);
			}
		}
		
		return remoteClips;
		
	}
	
    @Override
    public RemoteSegmentsResponse handleRequest(Object input, Context context) {
        logger = context.getLogger();
        logger.log("Loading Java handler to list remotely available segments");
        
        RemoteSegmentsResponse response;
        try {
        	List<Segment> list = getVideos();
        	response = new RemoteSegmentsResponse(list, 200);
        } catch (Exception e) {
        	response = new RemoteSegmentsResponse(403, e.getMessage());
        }
        return response;
    }

}